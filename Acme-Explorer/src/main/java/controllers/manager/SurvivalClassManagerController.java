
package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.ActorService;
import services.ManagerService;
import services.SurvivalClassService;
import services.SystemConfigService;
import services.TripService;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Controller
@RequestMapping("/survival-class/manager")
public class SurvivalClassManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private SurvivalClassService	survivalClassService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ActorService			actorService;
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private ManagerService managerService;


	// Constructors -----------------------------------------------------------

	public SurvivalClassManagerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<SurvivalClass> survivalClasses;

		survivalClasses = this.survivalClassService.findByPrincipal();
		result = new ModelAndView("survival-class/list");
		result.addObject("survivalClasses", survivalClasses);
		result.addObject("requestURI", "survival-class/manager/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.create();
		result = this.createEditModelAndView(survivalClass);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int survivalClassId) {
		ModelAndView result;
		SurvivalClass survivalClass;

		survivalClass = this.survivalClassService.findOneToEdit(survivalClassId);
		Assert.notNull(survivalClass);
		result = this.createEditModelAndView(survivalClass);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;
		String[] description = survivalClass.getDescription().toLowerCase().split(" ");
		String[] title = survivalClass.getTitle().toLowerCase().split(" ");

		Manager m = this.managerService.findByPrincipal();


		if (binding.hasErrors())
			result = this.createEditModelAndView(survivalClass);
		else
			try {
				for(String s : this.systemConfigService.findConfig().getSpamWords()){
					for(String d : description){
						if(d.contains(s)){
							m.setSuspicious(true);
							this.managerService.save(m);
						}
					}
				}
				
				for(String s : this.systemConfigService.findConfig().getSpamWords()){
					for(String t : title){
						if(t.contains(s)){
							m.setSuspicious(true);
							this.managerService.save(m);
						}
					}
				}
				this.survivalClassService.save(survivalClass);
				result = new ModelAndView("redirect:list.do");
			} catch (final ObjectOptimisticLockingFailureException e) {
				result = this.createEditModelAndView(survivalClass, "survivalClass.commit.test");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SurvivalClass survivalClass, final BindingResult binding) {
		ModelAndView result;

		try {
			this.survivalClassService.delete(survivalClass);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(survivalClass, "survivalClass.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass) {
		ModelAndView result;

		result = this.createEditModelAndView(survivalClass, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final SurvivalClass survivalClass, final String message) {
		ModelAndView result = null;
		final Manager manager = (Manager) this.actorService.findByPrincipal();
		final Collection<Trip> trips = this.tripService.findByManager(manager);

		if (survivalClass.getId() == 0)
			result = new ModelAndView("survival-class/create");
		else
			result = new ModelAndView("survival-class/edit");
		result.addObject("survivalClass", survivalClass);
		result.addObject("trips", trips);
		if (survivalClass.getTrip() != null)
			result.addObject("tripId", survivalClass.getTrip().getId());
		result.addObject("message", message);

		return result;
	}

}

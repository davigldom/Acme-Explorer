
package controllers.manager;

import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.LegalTextService;
import services.ManagerService;
import services.RangerService;
import services.SystemConfigService;
import services.TripService;
import controllers.AbstractController;
import domain.Category;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Sponsorship;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TripService			tripService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private LegalTextService	legalTextService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------------------

	public TripManagerController() {
		super();
	}

	//Display ---------------------------------------------------------------------
	@RequestMapping(value = "/manager/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;
		boolean isOver = false;
		boolean canApplyDate = false;

		trip = this.tripService.findOne(tripId);
		Assert.notNull(trip);
		result = new ModelAndView("trip/display");
		Sponsorship randomSponsorship;
		final int numberOfSponsorships = trip.getSponsorships().size();
		final Random rand = new Random();

		if (numberOfSponsorships != 0) {
			final int random = rand.nextInt(numberOfSponsorships);
			randomSponsorship = (Sponsorship) trip.getSponsorships().toArray()[random];
		} else
			randomSponsorship = null;

		if (trip.getEndDate().before(new Date(System.currentTimeMillis())))
			isOver = true;

		if (trip.getStartDate().after(new Date(System.currentTimeMillis())))
			canApplyDate = true;

		result.addObject("trip", trip);
		result.addObject("randomSponsorship", randomSponsorship);
		result.addObject("isOver", isOver);
		result.addObject("canApplyDate", canApplyDate);

		return result;
	}

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView result;
		Collection<Trip> trips;

		trips = this.tripService.findAll();
		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/manager/list.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/manager/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.create(this.managerService.findByPrincipal());
		result = this.createEditModelAndView(trip);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId) {
		ModelAndView result;
		Trip trip;

		trip = this.tripService.findOneToEdit(tripId);
		Assert.notNull(trip);
		result = this.createEditModelAndView(trip);

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Trip trip, final BindingResult binding) {
		ModelAndView result;
		final Manager m = this.managerService.findByPrincipal();

		final String[] description = trip.getDescription().toLowerCase().split(" ");
		final String[] title = trip.getTitle().toLowerCase().split(" ");
		final String[] requirements = trip.getRequirements().toLowerCase().split(" ");

		if (binding.hasErrors())
			result = this.createEditModelAndView(trip);
		else
			try {
				this.tripService.save(trip);

				for (final String s : this.systemConfigService.findConfig().getSpamWords())
					for (final String d : description)
						if (d.contains(s)) {
							m.setSuspicious(true);
							this.managerService.save(m);
						}

				for (final String s : this.systemConfigService.findConfig().getSpamWords())
					for (final String t : title)
						if (t.contains(s)) {
							m.setSuspicious(true);
							this.managerService.save(m);
						}

				for (final String s : this.systemConfigService.findConfig().getSpamWords())
					for (final String r : requirements)
						if (r.contains(s)) {
							m.setSuspicious(true);
							this.managerService.save(m);
						}

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Trip trip, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(trip);
		else
			try {
				this.tripService.delete(trip);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(trip, "trip.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(@RequestParam final int tripId, @RequestParam final String cancelationReason) {
		ModelAndView result;

		try {
			final Trip trip = this.tripService.findOneToCancel(tripId);
			trip.setCancelled(true);
			trip.setCancelationReason(cancelationReason);
			this.tripService.save(trip);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.display(tripId);
			//				result.addObject("tripId", tripId);
			result.addObject("message", "trip.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "publish")
	public ModelAndView publish(@RequestParam final int tripId) {
		ModelAndView result;

		try {
			final Trip trip = this.tripService.findOneToEdit(tripId);
			Assert.isTrue(!trip.getStages().isEmpty());
			trip.setPublished(true);
			this.tripService.save(trip);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.display(tripId);
			result.addObject("message", "trip.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Trip trip) {
		ModelAndView result;

		result = this.createEditModelAndView(trip, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Trip trip, final String message) {
		ModelAndView result;

		final Collection<Ranger> rangers = this.rangerService.findAll();
		final Collection<LegalText> legalTexts = this.legalTextService.findAllFinal();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("trip/edit");
		result.addObject("rangers", rangers);
		result.addObject("legalTexts", legalTexts);
		result.addObject("categories", categories);
		result.addObject("trip", trip);
		result.addObject("message", message);

		return result;
	}
}

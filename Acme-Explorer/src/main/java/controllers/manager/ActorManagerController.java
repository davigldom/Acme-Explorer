
package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.Actor;
import domain.Manager;

@Controller
@RequestMapping("/actor")
public class ActorManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------------------

	public ActorManagerController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Manager manager, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(manager);
		else
			try {
				this.managerService.save(manager);
				if (manager.getId() == 0)
					result = new ModelAndView("redirect:/welcome/index.do");
				else
					result = new ModelAndView("redirect:/actor/display-principal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(manager, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/manager/ban", method = RequestMethod.POST, params = "true")
	public ModelAndView bannedManager(@RequestParam final int managerId) {
		ModelAndView result;
		final Manager manager = this.managerService.findOne(managerId);
		Assert.isTrue(!manager.isBanned());

		try {
			manager.setBanned(true);
			final Collection<Manager> managers = this.managerService.findAll();
			result = new ModelAndView("redirect:/actor/administrator/list.do");
			result.addObject("managers", managers);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/actor/administrator/list.do");
			result.addObject("message", "actor.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/manager/ban", method = RequestMethod.POST, params = "false")
	public ModelAndView unbannedManager(@RequestParam final int managerId) {
		ModelAndView result;
		final Manager manager = this.managerService.findOne(managerId);
		Assert.isTrue(manager.isBanned());

		try {
			manager.setBanned(false);
			final Collection<Manager> managers = this.managerService.findAll();
			result = new ModelAndView("redirect:/actor/administrator/list.do");
			result.addObject("managers", managers);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/actor/administrator/list.do");
			result.addObject("message", "actor.commit.error");
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject(actor.getUserAccount().getAuthorities().toArray()[0].toString().toLowerCase(), actor);
		result.addObject("authority", actor.getUserAccount().getAuthorities().toArray()[0].toString().toLowerCase());
		result.addObject("message", message);
		result.addObject("defaultCountry", this.systemConfigService.findConfig().getCountryCode());

		return result;
	}
}

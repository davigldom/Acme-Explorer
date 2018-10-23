
package controllers.ranger;

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

import services.RangerService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.Actor;
import domain.Ranger;

@Controller
@RequestMapping("/actor")
public class ActorRangerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------------------

	public ActorRangerController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Ranger ranger, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(ranger);
		else
			try {
				this.rangerService.save(ranger);
				if (ranger.getId() == 0)
					result = new ModelAndView("redirect:/welcome/index.do");
				else
					result = new ModelAndView("redirect:/actor/display-principal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(ranger, "actor.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "true")
	public ModelAndView bannedRanger(@RequestParam final int rangerId) {
		ModelAndView result;
		final Ranger ranger = this.rangerService.findOne(rangerId);
		Assert.isTrue(!ranger.isBanned());

		try {
			ranger.setBanned(true);
			final Collection<Ranger> rangers = this.rangerService.findAll();
			result = new ModelAndView("redirect:/actor/administrator/list.do");
			result.addObject("rangers", rangers);
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/actor/administrator/list.do");
			result.addObject("message", "actor.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/ranger/edit", method = RequestMethod.POST, params = "false")
	public ModelAndView unbannedRanger(@RequestParam final int rangerId) {
		ModelAndView result;
		final Ranger ranger = this.rangerService.findOne(rangerId);
		Assert.isTrue(ranger.isBanned());

		try {
			ranger.setBanned(false);
			final Collection<Ranger> rangers = this.rangerService.findAll();
			result = new ModelAndView("redirect:/actor/administrator/list.do");
			result.addObject("rangers", rangers);
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

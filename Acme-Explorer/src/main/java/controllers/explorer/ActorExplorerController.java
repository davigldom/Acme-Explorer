
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ExplorerService;
import services.SystemConfigService;
import controllers.AbstractController;
import domain.Actor;
import domain.Explorer;

@Controller
@RequestMapping("/actor")
public class ActorExplorerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------------------

	public ActorExplorerController() {
		super();
	}

	//Display ---------------------------------------------------------------------

	// Creation ---------------------------------------------------------------

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/explorer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Explorer explorer, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(explorer);
		else
			try {
				this.explorerService.save(explorer);
				if (explorer.getId() == 0)
					result = new ModelAndView("redirect:/security/login.do");
				else
					result = new ModelAndView("redirect:/actor/display-principal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(explorer, "actor.commit.error");
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

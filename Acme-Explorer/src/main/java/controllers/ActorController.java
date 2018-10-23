
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ExplorerService;
import services.FinderService;
import services.RangerService;
import services.SystemConfigService;
import domain.Actor;
import domain.Explorer;
import domain.Finder;
import domain.Ranger;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private SystemConfigService	systemConfigService;

	@Autowired
	private FinderService		finderService;


	// Constructors -----------------------------------------------------------

	public ActorController() {
		super();
	}

	//Display ---------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int actorId) {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(actorId);
		Assert.notNull(actor);
		Assert.isTrue(actor.getUserAccount().getAuthorities().toArray()[0].toString().equals("RANGER"));
		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("isPrincipal", false);

		return result;
	}

	@RequestMapping(value = "/display-principal", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(this.actorService.findByPrincipal().getId());
		Assert.notNull(actor);
		result = new ModelAndView("actor/display");
		result.addObject("actor", actor);
		result.addObject("isPrincipal", true);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create-explorer", method = RequestMethod.GET)
	public ModelAndView createExplorer() {
		ModelAndView result;
		Explorer explorer;

		Finder finder = this.finderService.create();
		finder = this.finderService.save(finder);
		explorer = this.explorerService.create(finder);
		result = this.createEditModelAndView(explorer);

		return result;
	}

	@RequestMapping(value = "/create-ranger", method = RequestMethod.GET)
	public ModelAndView createRanger() {
		ModelAndView result;
		Ranger ranger;

		ranger = this.rangerService.create();
		result = this.createEditModelAndView(ranger);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor actor;
		final Actor principal = this.actorService.findByPrincipal();
		actor = this.actorService.findOne(principal.getId());
		Assert.isTrue(actor.equals(principal));
		Assert.notNull(actor);
		result = this.createEditModelAndView(actor);

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

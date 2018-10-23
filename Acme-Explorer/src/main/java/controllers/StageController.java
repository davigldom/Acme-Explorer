
package controllers;

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

import services.StageService;
import services.TripService;
import domain.Stage;
import domain.Trip;

@Controller
@RequestMapping("/stage")
public class StageController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private StageService	stageService;

	@Autowired
	private TripService		tripService;


	// Constructors -----------------------------------------------------------

	public StageController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<Stage> stages;

		stages = this.stageService.findByTrip(tripId);
		result = new ModelAndView("stage/list");
		result.addObject("stages", stages);
		result.addObject("tripId", tripId);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Stage stage;

		stage = this.stageService.create();

		result = this.createEditModelAndView(stage, tripId);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tripId, @RequestParam final int stageId) {
		ModelAndView result;
		Stage stage;

		stage = this.stageService.findOneToEdit(stageId, tripId);
		Assert.notNull(stage);
		result = this.createEditModelAndView(stage, tripId);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Stage stage, final BindingResult binding, @RequestParam final int tripId) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(stage, tripId);
		else
			try {
				final Trip trip = this.tripService.findOne(tripId);
				this.stageService.save(stage, trip);
				result = new ModelAndView("redirect:list.do?tripId=" + String.valueOf(tripId));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(stage, tripId, "socialIdentity.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Stage stage, final int tripId, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(stage, tripId);
		else
			try {
				final Trip trip = this.tripService.findOne(tripId);
				this.stageService.delete(stage, trip);
				result = new ModelAndView("redirect:list.do?tripId=" + String.valueOf(tripId));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(stage, tripId, "socialIdentity.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Stage stage, final int tripId) {
		ModelAndView result;

		result = this.createEditModelAndView(stage, tripId, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Stage stage, final int tripId, final String message) {
		ModelAndView result;

		result = new ModelAndView("stage/edit");
		result.addObject("stage", stage);
		result.addObject("tripId", tripId);
		result.addObject("message", message);

		return result;
	}
}

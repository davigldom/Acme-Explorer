
package controllers.explorer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.StoryService;
import services.TripService;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Controller
@RequestMapping("/story/explorer")
public class StoryExplorerController extends AbstractController {

	@Autowired
	private TripService		tripService;

	@Autowired
	private StoryService	storyService;


	public StoryExplorerController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		Story story;
		final Trip trip = this.tripService.findOne(tripId);

		story = this.storyService.create(trip);
		result = this.createEditModelAndView(story);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Story story, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(story);
		else
			try {
				this.storyService.save(story);
				result = new ModelAndView("redirect:/trip/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(story, "story.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Story story) {
		ModelAndView result;

		result = this.createEditModelAndView(story, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Story story, final String messageCode) {
		ModelAndView result;
		final Trip t = story.getTrip();
		final Explorer e = story.getExplorer();

		result = new ModelAndView("story/explorer/edit");
		result.addObject("story", story);
		result.addObject("trip", t);
		result.addObject("explorer", e);

		result.addObject("message", messageCode);

		return result;
	}

}

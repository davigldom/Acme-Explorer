
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

import services.TagService;
import services.TagValueService;
import services.TripService;
import controllers.AbstractController;
import domain.Tag;
import domain.TagValue;
import domain.Trip;

@Controller
@RequestMapping("/tagValue/manager")
public class TagValueManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private TagValueService	tagValueService;

	@Autowired
	private TripService		tripService;

	@Autowired
	private TagService		tagService;


	// Constructors -----------------------------------------------------------

	public TagValueManagerController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		ModelAndView result;
		TagValue tagValue;
		Trip trip;

		tagValue = this.tagValueService.create();
		trip = this.tripService.findOne(tripId);
		tagValue.setTrip(trip);

		result = this.createEditModelAndView(tagValue);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tagValueId) {
		ModelAndView result;
		TagValue tagValue;

		tagValue = this.tagValueService.findOne(tagValueId);
		Assert.notNull(tagValue);
		result = this.createEditModelAndView(tagValue);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final TagValue tagValue, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tagValue);
		else
			try {
				this.tagValueService.save(tagValue);
				result = new ModelAndView("redirect:/trip/display.do?tripId=" + String.valueOf(tagValue.getTrip().getId()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tagValue, "tagValue.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final TagValue tagValue, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(tagValue);
		else
			try {
				this.tagValueService.delete(tagValue);
				result = new ModelAndView("redirect:/trip/display.do?tripId=" + String.valueOf(tagValue.getTrip().getId()));
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tagValue, "tagValue.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final TagValue tagValue) {
		ModelAndView result;

		result = this.createEditModelAndView(tagValue, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final TagValue tagValue, final String message) {
		ModelAndView result;

		final Collection<Tag> tags = this.tagService.findAll();

		result = new ModelAndView("tagValue/edit");
		result.addObject("tagValue", tagValue);
		result.addObject("message", message);
		result.addObject("tags", tags);

		return result;
	}
}

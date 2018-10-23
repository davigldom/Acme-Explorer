
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import domain.Audition;
import domain.Auditor;
import domain.Trip;

@Controller
@RequestMapping("/audition")
public class AuditionController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private TripService	tripService;


	//List of auditions per trip
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listPerTrip(@RequestParam final int tripId) {
		final ModelAndView result;
		final Collection<Audition> auditions;
		final Trip trip = this.tripService.findOne(tripId);

		auditions = trip.getAuditions();

		result = new ModelAndView("audition/list");
		result.addObject("auditions", auditions);
		result.addObject("tripId", trip.getId());
		result.addObject("requestURI", "audition/list.do");

		return result;
	}

	// Ancillary methods -------------------------------------------

	protected ModelAndView createEditModelAndView(final Audition audition) {
		ModelAndView result;

		result = this.createEditModelAndView(audition, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Audition audition, final String messageCode) {
		final ModelAndView result;
		final Date moment = audition.getMoment();
		final String title = audition.getTitle();
		final String description = audition.getDescription();
		final Collection<String> attachments = audition.getAttachments();
		final boolean draft = audition.isDraft();

		final Auditor auditor = audition.getAuditor();
		final Trip trip = audition.getTrip();

		result = new ModelAndView("audition/edit");

		result.addObject("audition", audition);
		result.addObject("moment", moment);
		result.addObject("title", title);
		result.addObject("description", description);
		result.addObject("attachments", attachments);
		result.addObject("draft", draft);
		result.addObject("auditor", auditor);
		result.addObject("trip", trip);
		result.addObject("message", messageCode);

		return result;
	}

}

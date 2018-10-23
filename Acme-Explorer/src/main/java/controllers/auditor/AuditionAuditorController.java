
package controllers.auditor;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AuditionService;
import services.AuditorService;
import services.TripService;
import controllers.AbstractController;
import domain.Audition;
import domain.Auditor;
import domain.Trip;

@Controller
@RequestMapping("/audition")
public class AuditionAuditorController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private AuditionService	auditionService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private TripService		tripService;


	// Display ----------------------------------------------------

	@RequestMapping(value = "/auditor/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int auditionId) {
		ModelAndView result;
		Audition audition;

		audition = this.auditionService.findOne(auditionId);
		Assert.notNull(audition);
		result = new ModelAndView("audition/display");

		result.addObject("audition", audition);

		return result;
	}

	// Listing ----------------------------------------------------

	//List of auditions per auditor
	@RequestMapping(value = "/auditor/list", method = RequestMethod.GET)
	public ModelAndView listPerAuditor() {
		final ModelAndView result;
		final Collection<Audition> auditions;
		final Auditor auditor = this.auditorService.findByPrincipal();
		final int principalId = this.auditorService.findByPrincipal().getId();
		boolean isAutor = false;

		auditions = auditor.getAuditions();

		result = new ModelAndView("audition/list");
		result.addObject("auditions", auditions);

		if (principalId == auditor.getId())
			isAutor = true;

		result.addObject("isAutor", isAutor);
		result.addObject("tripId", 0);
		result.addObject("requestURI", "audition/auditor/list.do");

		return result;
	}

	// Creation ---------------------------------------------------

	@RequestMapping(value = "/auditor/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		final ModelAndView result;
		final Audition audition;
		final Auditor auditor = this.auditorService.findByPrincipal();
		final Trip trip = this.tripService.findOne(tripId);
		final Collection<Audition> auditorAuditions = auditor.getAuditions();
		final Collection<Audition> tripAuditions = trip.getAuditions();
		Boolean res = false;

		//		String url;
		//		url = "redirect:list.do";

		for (final Audition aud : tripAuditions)
			if (auditorAuditions.contains(aud)) {
				res = true;
				break;
			}

		if (res) {
			result = this.listPerAuditor();
			result.addObject("message", "audition.create.error");
		} else {
			audition = this.auditionService.create(trip, auditor);
			result = this.createEditModelAndView(audition);
		}

		return result;
	}
	// Edition ----------------------------------------------------

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int auditionId) {
		ModelAndView result;
		final Audition audition;
		audition = this.auditionService.findOneToEdit(auditionId);
		Assert.notNull(audition);
		try {
			Assert.isTrue(audition.isDraft());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audition, "audition.commit.error");
		}

		result = this.createEditModelAndView(audition);

		return result;
	}

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Audition audition, final BindingResult binding) {
		ModelAndView result;

		if (audition.getId() != 0) {
			final Collection<String> attachments = audition.getAttachments();
			for (final String attachment : attachments)
				if (attachment == null || attachment.equals(""))
					attachments.remove(attachment);

			audition.setAttachments(attachments);
		} else {
			final Collection<String> attachments;
			attachments = new HashSet<String>();
			audition.setAttachments(attachments);
		}

		if (binding.hasErrors())
			result = this.createEditModelAndView(audition);
		else
			try {
				this.auditionService.save(audition);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(audition, "audition.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/auditor/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Audition audition, final BindingResult binding) {

		ModelAndView result;

		try {
			this.auditionService.delete(audition);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(audition, "audition.commit.error");
		}

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
		final boolean draft = audition.isDraft();
		Collection<String> attachments = null;

		if (!audition.getAttachments().isEmpty())
			attachments = audition.getAttachments();

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

	@RequestMapping(value = "/auditor/addAttachment", method = RequestMethod.GET)
	public ModelAndView addAttachment(@RequestParam final String attachment, @RequestParam final int auditionId) {
		final ModelAndView result;

		Audition audition;
		audition = this.auditionService.findOne(auditionId);

		if (!attachment.isEmpty() || attachment == null) {
			audition.getAttachments().add(attachment);
			this.auditionService.save(audition);
		}

		String url;
		url = "redirect:display.do?auditionId=" + audition.getId();

		result = new ModelAndView(url);
		result.addObject("audition", audition);

		return result;
	}

}


package controllers.manager;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ManagerService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Status;
import domain.Trip;

@Controller
@RequestMapping("/application")
public class ApplicationManagerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private ApplicationService	applicationService;


	// Constructors -----------------------------------------------

	public ApplicationManagerController() {
		super();
	}

	// Listing ----------------------------------------------------

	@RequestMapping(value = "/manager/list", method = RequestMethod.GET)
	public ModelAndView listManager() {
		final ModelAndView result;
		final Collection<Application> applications;
		final Manager manager = this.managerService.findByPrincipal();

		applications = this.applicationService.findByManagerId(manager.getId());

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/manager/list.do");

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.GET)
	public ModelAndView editManager(@RequestParam final int applicationId) {
		ModelAndView result;
		final Application application = this.applicationService.findOne(applicationId);

		result = this.createEditModelAndViewReject(application, null);

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "due")
	public ModelAndView due(@RequestParam final int applicationId) {
		ModelAndView result;
		final Application application = this.applicationService.findOne(applicationId);
		Assert.isTrue(application.getStatus().equals(Status.PENDING));

		application.setStatus(Status.DUE);

		try {
			this.applicationService.save(application);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "application.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/manager/edit", method = RequestMethod.POST, params = "reject")
	public ModelAndView reject(@RequestParam final int applicationId, @RequestParam final String rejectedReason) {
		ModelAndView result;
		final Application application = this.applicationService.findOne(applicationId);
		Assert.notNull(application);
		Assert.isTrue(application.getStatus().equals(Status.PENDING));

		if (rejectedReason == null || rejectedReason == "")
			result = this.createEditModelAndViewReject(application, "application.reject.reason");
		else {
			application.setStatus(Status.REJECTED);
			application.setRejectedReason(rejectedReason);

			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndViewReject(application, "application.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods -------------------------------------------
	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String messageCode) {
		final ModelAndView result;
		final Date moment = application.getMoment();
		final Status status = application.getStatus();
		final Trip trip = application.getTrip();
		final String rejectedReason = application.getRejectedReason();
		final String comments = application.getComments();
		final CreditCard creditCard = application.getCreditCard();
		final Explorer explorer = application.getExplorer();

		if (messageCode != null && messageCode.equals("application.credit-card.expired"))
			result = new ModelAndView("redirect:edit.do?applicationId=" + application.getId());
		else {
			result = new ModelAndView("application/edit");
			result.addObject("application", application);
			result.addObject("moment", moment);
			result.addObject("status", status);
			result.addObject("trip", trip);
			result.addObject("explorer", explorer);
			result.addObject("rejectedReason", rejectedReason);
			result.addObject("comments", comments);
			result.addObject("creditCard", creditCard);
		}
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndViewReject(final Application application, final String messageCode) {
		final ModelAndView result;
		final Date moment = application.getMoment();
		final Status status = application.getStatus();
		final Trip trip = application.getTrip();
		final String comments = application.getComments();
		final CreditCard creditCard = application.getCreditCard();
		final Explorer explorer = application.getExplorer();

		result = new ModelAndView("application/reject");

		result.addObject("application", application);
		result.addObject("moment", moment);
		result.addObject("status", status);
		result.addObject("trip", trip);
		result.addObject("explorer", explorer);
		result.addObject("comments", comments);
		result.addObject("creditCard", creditCard);
		result.addObject("message", messageCode);

		return result;
	}

}

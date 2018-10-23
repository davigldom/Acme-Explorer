
package controllers.explorer;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CreditCardService;
import services.ExplorerService;
import services.TripService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Status;
import domain.Trip;

@Controller
@RequestMapping("/application")
public class ApplicationExplorerController extends AbstractController {

	// Services ---------------------------------------------------

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructors -----------------------------------------------

	public ApplicationExplorerController() {
		super();
	}

	// Listing ----------------------------------------------------

	@RequestMapping(value = "/explorer/list", method = RequestMethod.GET)
	public ModelAndView listExplorer(@RequestParam(required = false) final String message) {
		final ModelAndView result;
		final Collection<Application> applications;
		final Explorer explorer = this.explorerService.findByPrincipal();

		applications = explorer.getApplications();

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/explorer/list.do");
		result.addObject("message", message);

		return result;
	}

	// Creation ---------------------------------------------------

	@RequestMapping(value = "/explorer/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tripId) {
		final ModelAndView result;
		Application application;
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip trip = this.tripService.findOne(tripId);

		application = this.applicationService.create(trip, explorer);

		result = this.createEditModelAndView(application);

		return result;
	}

	// Edition ----------------------------------------------------

	@RequestMapping(value = "/explorer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId, @RequestParam(required = false) final String message) {
		ModelAndView result;
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Collection<CreditCard> creditCardOptions = this.creditCardService.findByExplorerId(explorer.getId());
		final Application application = this.applicationService.findOne(applicationId);
		Assert.notNull(application); //Not needed as you can't delete an application.

		result = this.createEditModelAndView(application);
		result.addObject("creditCardOptions", creditCardOptions);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/explorer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		final LocalDate now = LocalDate.now();
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Collection<Application> apps = application.getTrip().getApplications();
		final Set<Explorer> explorers = new HashSet<>();
		Assert.notNull(now);
		Assert.notNull(application);

		for (final Application a : apps)
			explorers.add(a.getExplorer());

		if (explorers.contains(explorer) && application.getStatus() != Status.DUE) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "application.alreadyApplied");
		} else if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else if (application.getCreditCard() != null && 2000 + application.getCreditCard().getExpirationYear() == now.getYear() && application.getCreditCard().getExpirationMonth() < now.getMonthOfYear())
			result = this.createEditModelAndView(application, "application.credit-card.expired");
		else if (application.getCreditCard() != null && 2000 + application.getCreditCard().getExpirationYear() < now.getYear())
			result = this.createEditModelAndView(application, "application.credit-card.expired");
		else
			try {
				this.applicationService.save(application);
				result = new ModelAndView("redirect:list.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(application, "application.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/explorer/edit", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(@RequestParam final int applicationId) {
		ModelAndView result;
		final Application application = this.applicationService.findOne(applicationId);
		Assert.isTrue(application.getStatus().equals(Status.ACCEPTED));
		Assert.isTrue(application.getTrip().getStartDate().after(new Date()));

		application.setStatus(Status.CANCELLED);

		try {
			this.applicationService.save(application);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "application.commit.error");
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

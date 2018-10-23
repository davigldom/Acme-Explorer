
package controllers.admin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.ApplicationService;
import services.AuditionService;
import services.LegalTextService;
import services.ManagerService;
import services.NoteService;
import services.RangerService;
import services.SystemConfigService;
import services.TripService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Manager;
import domain.Ranger;
import domain.Trip;

@Controller
@RequestMapping("/actor")
public class ActorAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private AuditionService			auditionService;

	@Autowired
	private NoteService				noteService;

	@Autowired
	private LegalTextService		legalTextService;

	@Autowired
	private TripService				tripService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private RangerService			rangerService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private SystemConfigService		systemConfigService;


	// Constructors -----------------------------------------------------------

	public ActorAdministratorController() {
		super();
	}

	//Display ---------------------------------------------------------------------

	@RequestMapping(value = "/administrator/create-manager", method = RequestMethod.GET)
	public ModelAndView createManager() {
		ModelAndView result;
		Manager manager;

		manager = this.managerService.create();
		result = this.createEditModelAndView(manager);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(administrator);
		else
			try {
				this.administratorService.save(administrator);
				result = new ModelAndView("redirect:/actor/display-principal.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(administrator, "actor.commit.error");
			}

		return result;
	}

	// Show dashboard -----------------------------------------------

	@RequestMapping("/administrator/dashboard")
	public ModelAndView dashboard() {
		final ModelAndView result;

		result = new ModelAndView("administrator/dashboard");
		result.addObject("averageApplicationsPerTrip", this.applicationService.averageApplicationsPerTrip());
		result.addObject("minimumApplicationsPerTrip", this.applicationService.minimumApplicationsPerTrip());
		result.addObject("maximumApplicationsPerTrip", this.applicationService.maximumApplicationsPerTrip());
		result.addObject("standardDeviationApplicationsPerTrip", this.applicationService.standardDerivationApplicationsPerTrip());

		result.addObject("averageManagersPerTrip", this.tripService.findAveragePerManager());
		result.addObject("minimumManagersPerTrip", this.tripService.findMinPerManager());
		result.addObject("maximumManagersPerTrip", this.tripService.findMaxPerManager());
		result.addObject("standardDeviationManagersPerTrip", this.tripService.findStandardDeviationPerManager());
		result.addObject("ratioSuspiciousManagers", this.managerService.ratioManagersSuspicious());

		result.addObject("averagePriceOfTrip", this.tripService.findPriceAverage());
		result.addObject("minimumPriceOfTrip", this.tripService.findPriceMin());
		result.addObject("maximumPriceOfTrip", this.tripService.findPriceMax());
		result.addObject("standardDeviationPriceOfTrip", this.tripService.findPriceStandardDeviation());

		result.addObject("averageRangersPerTrip", this.tripService.findAveragePerRanger());
		result.addObject("minimumRangersPerTrip", this.tripService.findMinPerRanger());
		result.addObject("maximumRangersPerTrip", this.tripService.findMaxPerRanger());
		result.addObject("standardDeviationRangersPerTrip", this.tripService.findStandardDeviationPerRanger());
		result.addObject("ratioRangersCurriculaRegistered", this.rangerService.getRatioRangersWithCurriculaRegistered());
		result.addObject("ratioRangersCurriculumEndorsed", this.rangerService.getRatioRangersWithCurriculumEndorsed());
		result.addObject("ratioSuspiciousRangers", this.rangerService.getRatioRangersSuspicious());

		result.addObject("ratioApplicationsRejected", this.applicationService.ratioApplicationsRejected());
		result.addObject("ratioApplicationsPending", this.applicationService.ratioApplicationsPending());
		result.addObject("ratioApplicationsDue", this.applicationService.ratioApplicationsDue());
		result.addObject("ratioApplicationsAccepted", this.applicationService.ratioApplicationsAccepted());
		result.addObject("ratioApplicationsCancelled", this.applicationService.ratioApplicationsCancelled());

		result.addObject("tripsOrganised", this.tripService.findAll().size());
		result.addObject("tripsCancelled", this.tripService.findRatioCancelled()); //Los trips se están creando con cadena vacía en vez de null

		final Collection<Trip> tenPercent = this.tripService.findTenPercentApplications();
		result.addObject("tenPercent", tenPercent);

		result.addObject("legalTexts", this.legalTextService.findAll());

		result.addObject("averageNotesPerTrip", this.noteService.findAverage());
		result.addObject("minimumNotesPerTrip", this.noteService.findMin());
		result.addObject("maximumNotesPerTrip", this.noteService.findMax());
		result.addObject("standardDeviationNotesPerTrip", this.noteService.findStandardDeviation());

		result.addObject("averageAuditRecordsPerTrip", this.auditionService.averageAuditsPerTrip());
		result.addObject("minimumAuditRecordsPerTrip", this.auditionService.minimumAuditsPerTrip());
		result.addObject("maximumAuditRecordsPerTrip", this.auditionService.maximumAuditsPerTrip());
		result.addObject("standardDeviationAuditRecordsPerTrip", this.auditionService.standardDerivationAuditsPerTrip());
		result.addObject("tripsWithAnAuditRecord", this.tripService.findRatioWithAudit());

		return result;

	}

	@RequestMapping(value = "/administrator/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Actor> actors;
		Collection<Manager> managers;
		Collection<Ranger> rangers;

		actors = this.actorService.findAll();
		managers = this.managerService.findAll();
		rangers = this.rangerService.findAll();

		final List<Manager> suspiciousManagers = new ArrayList<>();
		for (final Manager m : managers)
			if (m.isSuspicious())
				suspiciousManagers.add(m);
		final List<Ranger> suspiciousRangers = new ArrayList<>();
		for (final Ranger r : rangers)
			if (r.isSuspicious())
				suspiciousRangers.add(r);
		result = new ModelAndView("actor/list");
		result.addObject("actors", actors);
		result.addObject("managers", suspiciousManagers);
		result.addObject("requestURI", "actor/administrator/list.do");
		result.addObject("rangers", suspiciousRangers);

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

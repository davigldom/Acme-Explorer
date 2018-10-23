
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Actor;
import domain.Administrator;
import domain.Application;
import domain.Explorer;
import domain.Manager;
import domain.Message;
import domain.PriorityLevel;
import domain.Status;
import domain.Trip;

@Service
@Transactional
public class ApplicationService {

	//Managed Repository-------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting services----------------------------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ExplorerService			explorerService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private MessageService			messageService;


	//	@Autowired
	//	private CreditCardService		creditCardService;

	// Constructors------------------------------------------------

	// Simple CRUD methods-----------------------------------------
	public Application create(final Trip trip, final Explorer explorer) {
		Assert.notNull(trip);
		Assert.notNull(explorer);
		//		Assert.notNull(creditCard);

		final Application result = new Application();

		result.setMoment(new Date(System.currentTimeMillis() - 1000));
		result.setStatus(Status.PENDING);

		result.setExplorer(explorer);
		result.setTrip(trip);
		//		result.setCreditCard(creditCard);

		explorer.getApplications().add(result);
		trip.getApplications().add(result);

		return result;
	}

	public Collection<Application> findAll() {
		Collection<Application> result;

		result = this.applicationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Application findOne(final int applicationId) {
		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(this.checkPrincipalExplorer(application) || this.checkPrincipalManager(application));
		Assert.isTrue(application.getTrip().getStartDate().after(new Date(System.currentTimeMillis())));
		//Check if we are saving a new application or editing one
		if (application.getId() != 0) {
			final Status oldStatus = this.applicationRepository.findOne(application.getId()).getStatus();

			//If a credit card is provided, the application is accepted as long as its status was due.
			if (application.getCreditCard() != null && application.getStatus().equals(Status.DUE))
				application.setStatus(Status.ACCEPTED);

			//Check if the status has changed
			if (!oldStatus.equals(application.getStatus())) {
				if (application.getStatus().equals(Status.REJECTED))
					Assert.isTrue(this.checkPrincipalManager(application) && application.getRejectedReason() != null);
				else if (application.getStatus().equals(Status.DUE))
					Assert.isTrue(this.checkPrincipalManager(application) && application.getCreditCard() == null);
				else if (application.getStatus().equals(Status.ACCEPTED))
					Assert.notNull(application.getCreditCard());
				else if (application.getStatus().equals(Status.CANCELLED))
					Assert.isTrue(this.checkPrincipalExplorer(application));

				//We create the subject, body and priority level of the notification.
				final String subject = "[AUTO] Application status change";
				final String body = "Application to trip \"" + application.getTrip().getTitle() + "\" has changed to " + application.getStatus();
				final PriorityLevel priorityLevel = PriorityLevel.HIGH;
				//We use [AUTO] to let the recipient know that it has not been actually written by the sender. 

				//Now we create two messages depending on the user logged.							
				final Message notification = this.messageService.create();

				notification.setSubject(subject);
				notification.setBody(body);
				notification.setPriorityLevel(priorityLevel);

				if (this.checkPrincipalExplorer(application)) {
					final List<Actor> actors = new ArrayList<Actor>();
					actors.add(this.explorerService.findByPrincipal());
					actors.add(application.getTrip().getManager());
					notification.setActors(actors);
				} else {
					final List<Actor> actors = new ArrayList<Actor>();
					actors.add(this.managerService.findByPrincipal());
					actors.add(application.getExplorer());
					notification.setActors(actors);
				}

				this.messageService.save(notification, true, false);
			}
		}

		application.setMoment(new Date(System.currentTimeMillis() - 1000));
		Application result = null;

		result = this.applicationRepository.save(application);

		return result;
	}

	public void delete(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);

		application.getExplorer().getApplications().remove(application);
		application.getTrip().getApplications().remove(application);

		this.applicationRepository.delete(application);
	}

	// Other business methods--------------------------------------

	public Application findOneToEdit(final int applicationId) {
		Application result;
		result = this.findOne(applicationId);
		Assert.notNull(result);
		Assert.isTrue(this.checkPrincipalExplorer(result) || this.checkPrincipalManager(result));
		return result;
	}

	public boolean checkPrincipalExplorer(final Application application) {
		Assert.notNull(application);
		final Explorer principal = this.explorerService.findByPrincipal();
		return principal == application.getExplorer();
	}

	public boolean checkPrincipalManager(final Application application) {
		Assert.notNull(application);
		final Manager principal = this.managerService.findByPrincipal();
		return principal == application.getTrip().getManager();
	}

	public Collection<Application> findByManagerId(final int managerId) {
		Collection<Application> result;
		result = this.applicationRepository.findByManagerId(managerId);
		return result;
	}

	public Collection<Application> findByExplorerId(final int explorerId) {
		Collection<Application> result;
		result = this.applicationRepository.findByExplorerId(explorerId);
		return result;
	}

	public Collection<Application> findByPrincipal() {
		Collection<Application> result;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		result = explorer.getApplications();

		return result;
	}

	public Double averageApplicationsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.averageApplicationsPerTrip();
		return result;
	}

	public Double minimumApplicationsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.minimumApplicationsPerTrip();
		return result;
	}

	public Double maximumApplicationsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.maximumApplicationsPerTrip();
		return result;
	}
	public Double standardDerivationApplicationsPerTrip() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.standardDerivationApplicationsPerTrip();
		return result;
	}
	public Double ratioApplicationsRejected() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.ratioApplicationsRejected();
		return result;
	}
	
	public Double ratioApplicationsPending() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.ratioApplicationsPending();
		return result;
	}

	public Double ratioApplicationsDue() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.ratioApplicationsDue();
		return result;
	}

	public Double ratioApplicationsAccepted() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.ratioApplicationsAccepted();
		return result;
	}

	public Double ratioApplicationsCancelled() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Double result;
		result = this.applicationRepository.ratioApplicationsCancelled();
		return result;
	}
}

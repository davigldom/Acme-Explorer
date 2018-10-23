
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.CreditCard;
import domain.Explorer;
import domain.Manager;
import domain.Message;
import domain.Status;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ExplorerService		explorerService;

	@Autowired
	private TripService			tripService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private CreditCardService	creditCardService;

	@Autowired
	private FolderService		folderService;


	@Test
	public void testCreateApplication() {
		super.authenticate("explorer1");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		//		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Application application = this.applicationService.create(trip, explorer);

		Assert.isTrue(application.getExplorer().equals(explorer));
		Assert.notNull(application.getTrip());
		Assert.notNull(application.getMoment());
		Assert.isTrue(application.getStatus().equals(Status.PENDING));
		Assert.isNull(application.getComments());
		Assert.isNull(application.getRejectedReason());

		super.authenticate(null);
	}

	@Test
	public void testSaveApplication() {
		super.authenticate("explorer2");

		final String comments;
		comments = "Very good";
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		//		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Application application = this.applicationService.create(trip, explorer);

		application.setComments(comments);

		final Application savedApplication = this.applicationService.save(application);

		final Collection<Application> applications = this.applicationService.findByExplorerId(explorer.getId());
		Assert.isTrue(applications.contains(savedApplication));
		super.authenticate(null);

	}

	@Test
	public void testPayApplication() {
		super.authenticate("explorer2");

		//We create a credit card.
		final Explorer principal = this.explorerService.findByPrincipal();
		final CreditCard newCreditCard = this.creditCardService.create();
		newCreditCard.setHolderName("Holder name to test the save");
		newCreditCard.setBrandName("Brand name to test the save");
		newCreditCard.setNumber("4024007169299525");
		newCreditCard.setExpirationMonth(12);
		newCreditCard.setExpirationYear(24);
		newCreditCard.setCVV(123);
		newCreditCard.setExplorer(principal);
		final CreditCard creditCard = this.creditCardService.save(newCreditCard);

		final String comments;
		comments = "Very good";
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		//		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Application application = this.applicationService.create(trip, explorer);

		application.setComments(comments);

		final Application savedApplication = this.applicationService.save(application);

		final Collection<Application> applications = this.applicationService.findByExplorerId(explorer.getId());
		Assert.isTrue(applications.contains(savedApplication));

		savedApplication.setStatus(Status.DUE);
		savedApplication.setCreditCard(creditCard);
		final Application savedApplication1 = this.applicationService.save(savedApplication);

		Assert.isTrue(savedApplication1.getStatus().equals(Status.ACCEPTED));

		final Message notification = (Message) this.folderService.getNotificationBox(principal).getMessages().toArray()[0];
		Assert.isTrue(notification.getSubject().equals("[AUTO] Application status change"));

		super.authenticate(null);

	}

	@Test
	public void testCancelApplication() {
		super.authenticate("explorer2");

		//We create a credit card.
		final Explorer principal = this.explorerService.findByPrincipal();
		final CreditCard newCreditCard = this.creditCardService.create();
		newCreditCard.setHolderName("Holder name to test the save");
		newCreditCard.setBrandName("Brand name to test the save");
		newCreditCard.setNumber("4024007169299525");
		newCreditCard.setExpirationMonth(12);
		newCreditCard.setExpirationYear(24);
		newCreditCard.setCVV(123);
		newCreditCard.setExplorer(principal);

		final String comments;
		comments = "Very good";
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		//		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Application application = this.applicationService.create(trip, explorer);

		application.setComments(comments);

		final Application savedApplication = this.applicationService.save(application);

		final Collection<Application> applications = this.applicationService.findByExplorerId(explorer.getId());
		Assert.isTrue(applications.contains(savedApplication));

		savedApplication.setStatus(Status.CANCELLED);
		final Application savedApplication1 = this.applicationService.save(savedApplication);

		Assert.isTrue(savedApplication1.getStatus().equals(Status.CANCELLED));

		super.authenticate(null);

	}

	@Test
	public void testRejectApplication() {
		super.authenticate("explorer2");

		//We create a credit card.
		final Explorer principal = this.explorerService.findByPrincipal();
		final CreditCard newCreditCard = this.creditCardService.create();
		newCreditCard.setHolderName("Holder name to test the save");
		newCreditCard.setBrandName("Brand name to test the save");
		newCreditCard.setNumber("4024007169299525");
		newCreditCard.setExpirationMonth(12);
		newCreditCard.setExpirationYear(24);
		newCreditCard.setCVV(123);
		newCreditCard.setExplorer(principal);

		final String comments;
		comments = "Very good";
		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		//		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Application application = this.applicationService.create(trip, explorer);

		application.setComments(comments);

		final Application savedApplication = this.applicationService.save(application);

		final Collection<Application> applications = this.applicationService.findByExplorerId(explorer.getId());
		Assert.isTrue(applications.contains(savedApplication));

		super.authenticate(null);

		super.authenticate(trip.getManager().getUserAccount().getUsername());

		savedApplication.setStatus(Status.REJECTED);

		Application savedApplication1 = null;
		try {
			savedApplication1 = this.applicationService.save(savedApplication);
		} catch (final Exception e) {
			savedApplication.setRejectedReason("This is for a test");
			savedApplication1 = this.applicationService.save(savedApplication);
		}

		Assert.isTrue(savedApplication1.getStatus().equals(Status.REJECTED));

		super.authenticate(null);

	}

	@Test
	public void testDeleteApplication() {
		super.authenticate("explorer2");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		//		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Application application = this.applicationService.create(trip, explorer);

		application.setStatus(Status.ACCEPTED);

		final Application savedApplication = this.applicationService.save(application);
		this.applicationService.delete(savedApplication);

		final Collection<Application> applications = this.applicationService.findByPrincipal();
		Assert.isTrue(!applications.contains(this.applicationService));

		super.authenticate(null);
	}

	@Test
	public void testFindOneToEdit() {
		super.authenticate("explorer2");

		Application application;
		application = (Application) this.explorerService.findByPrincipal().getApplications().toArray()[0];

		this.applicationService.findOneToEdit(application.getId());
		Assert.isTrue(this.applicationService.checkPrincipalExplorer(application) || this.applicationService.checkPrincipalManager(application));

		super.authenticate(null);
	}

	@Test
	public void testFindByManagerId() {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();
		final Trip trip = (Trip) manager.getTrips().toArray()[0];

		Application application;
		application = (Application) trip.getApplications().toArray()[0];

		final Collection<Application> apps = this.applicationService.findByManagerId(manager.getId());

		Assert.isTrue(apps.contains(application));

		super.authenticate(null);
	}
	@Test
	public void testFindByExplorerId() {
		super.authenticate("explorer2");

		final Explorer explorer = this.explorerService.findByPrincipal();

		Application application;
		application = (Application) this.explorerService.findByPrincipal().getApplications().toArray()[0];

		final Collection<Application> apps = this.applicationService.findByExplorerId(explorer.getId());
		Assert.isTrue(apps.contains(application));

		super.authenticate(null);
	}
}

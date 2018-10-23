
package services;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;
import domain.LegalText;
import domain.Manager;
import domain.Ranger;
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TripServiceTest extends AbstractTest {

	@Autowired
	private TripService			tripService;
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private RangerService		rangerService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private LegalTextService	legalTextService;
	@Autowired
	private StageService		stageService;


	@Test
	public void testCreate() {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();

		//		final Ranger ranger = (Ranger) this.rangerService.findAll().toArray()[0];

		//		final Category category = this.categoryService.findDefaultCategory();

		final LegalText legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		if (legalText.isDefinitive())
			legalText.setDefinitive(true);

		final Trip trip = this.tripService.create(manager);

		Assert.isNull(trip.getTitle());
		Assert.isNull(trip.getDescription());
		Assert.isNull(trip.getPublication());
		Assert.notNull(trip.getTicker());
		Assert.isNull(trip.getCancelationReason());
		Assert.isTrue(trip.getPrice() == 0.0);
		Assert.isNull(trip.getStartDate());
		Assert.isNull(trip.getEndDate());
		Assert.isTrue(!trip.isCancelled());
		Assert.isTrue(!trip.isPublished());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();

		final Ranger ranger = (Ranger) this.rangerService.findAll().toArray()[0];

		final Category category = this.categoryService.findDefaultCategory();

		final LegalText legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		if (!legalText.isDefinitive())
			legalText.setDefinitive(true);

		final Trip trip = this.tripService.create(manager);
		trip.setTitle("Test trip");
		trip.setDescription("Test description");
		trip.setStartDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
		trip.setEndDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		trip.setRequirements("Requirements");
		trip.setRanger(ranger);
		trip.setCategory(category);
		trip.setLegalText(legalText);

		final Trip savedTrip = this.tripService.save(trip);

		Assert.isTrue(this.tripService.findAll().contains(savedTrip));
		Assert.isTrue(this.tripService.findByCategory(category).contains(savedTrip));
		Assert.isTrue(this.tripService.findByManager(manager).contains(savedTrip));
		Assert.isTrue(this.tripService.findByLegalText(legalText).contains(savedTrip));
		Assert.isTrue(this.tripService.findByRanger(ranger).contains(savedTrip));

		super.authenticate(null);
	}

	@Test
	public void testDelete() throws ParseException {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();

		final Ranger ranger = (Ranger) this.rangerService.findAll().toArray()[0];

		final Category category = this.categoryService.findDefaultCategory();

		final LegalText legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		if (!legalText.isDefinitive())
			legalText.setDefinitive(true);

		final Trip trip = this.tripService.create(manager);
		trip.setTitle("Test trip");
		trip.setDescription("Test description");
		trip.setStartDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
		trip.setEndDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		trip.setRequirements("Requirements");
		trip.setRanger(ranger);
		trip.setCategory(category);
		trip.setLegalText(legalText);

		final Trip savedTrip = this.tripService.save(trip);

		this.tripService.delete(savedTrip);

		Assert.isTrue(!this.tripService.findAll().contains(savedTrip));

		super.authenticate(null);
	}

	@Test
	public void testCancelWithoutReason() {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();

		final Ranger ranger = (Ranger) this.rangerService.findAll().toArray()[0];

		final Category category = this.categoryService.findDefaultCategory();

		final LegalText legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		if (!legalText.isDefinitive())
			legalText.setDefinitive(true);

		final Trip trip = this.tripService.create(manager);
		trip.setTitle("Test trip");
		trip.setDescription("Test description");
		trip.setStartDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
		trip.setEndDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		trip.setRequirements("Requirements");
		trip.setRanger(ranger);
		trip.setCategory(category);
		trip.setLegalText(legalText);

		final Trip savedTrip = this.tripService.save(trip);

		final Trip editedTrip = this.tripService.findOne(savedTrip.getId());
		editedTrip.setCancelled(true);
		editedTrip.setPublished(true);

		try {
			this.tripService.save(editedTrip);
			Assert.isTrue(false);
		} catch (final Exception e) {
		}

	}

	@Test
	public void testPublish() {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();

		final Ranger ranger = (Ranger) this.rangerService.findAll().toArray()[0];

		final Category category = this.categoryService.findDefaultCategory();

		final LegalText legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		if (!legalText.isDefinitive())
			legalText.setDefinitive(true);

		final Trip trip = this.tripService.create(manager);
		trip.setTitle("Test trip");
		trip.setDescription("Test description");
		trip.setStartDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
		trip.setEndDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		trip.setRequirements("Requirements");
		trip.setRanger(ranger);
		trip.setCategory(category);
		trip.setLegalText(legalText);

		Trip savedTrip = this.tripService.save(trip);

		final Trip editedTrip = this.tripService.findOne(savedTrip.getId());
		editedTrip.setPublished(true);
		Assert.isTrue(editedTrip.getPublication() == null);
		savedTrip = this.tripService.save(editedTrip);
		Assert.isTrue(savedTrip.getPublication() != null);

	}

	@Test
	public void testGetPrice() {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();

		final Ranger ranger = (Ranger) this.rangerService.findAll().toArray()[0];

		final Category category = this.categoryService.findDefaultCategory();

		final LegalText legalText = (LegalText) this.legalTextService.findAll().toArray()[0];
		if (!legalText.isDefinitive())
			legalText.setDefinitive(true);

		final Trip trip = this.tripService.create(manager);
		trip.setTitle("Test trip");
		trip.setDescription("Test description");
		trip.setStartDate(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000));
		trip.setEndDate(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000));
		trip.setRequirements("Requirements");
		trip.setRanger(ranger);
		trip.setCategory(category);
		trip.setLegalText(legalText);

		final Trip savedTrip = this.tripService.save(trip);

		final Stage stage1 = this.stageService.create();
		stage1.setTitle("Stage 1");
		stage1.setDescription("This test is starting to get boring");
		stage1.setPrice(25.0);
		final Stage stage1Saved = this.stageService.save(stage1, savedTrip);
		final Stage stage2 = this.stageService.create();
		stage2.setTitle("Stage 2");
		stage2.setDescription("This test is starting to get really boring");
		stage2.setPrice(25.0);
		final Stage stage2Saved = this.stageService.save(stage2, savedTrip);
		final Set<Stage> stages = new HashSet<>();
		stages.add(stage1Saved);
		stages.add(stage2Saved);

		Assert.isTrue(savedTrip.getPrice() == 60.5, String.valueOf(savedTrip.getPrice()));

	}

	@Test
	public void testGetTrips() {
		super.authenticate("manager1");
		final Collection<Trip> trips1 = this.tripService.finderResults("trip", 0.0, 100.0, null, null);

		Assert.isTrue(!trips1.isEmpty());
		super.authenticate(null);
	}
}

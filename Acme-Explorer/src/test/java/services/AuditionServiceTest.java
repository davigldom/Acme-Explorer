
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Audition;
import domain.Auditor;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditionServiceTest extends AbstractTest {

	@Autowired
	AuditionService			auditionService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private TripService		tripService;


	@Test
	public void testCreateAudition() {
		super.authenticate("auditor1");

		final Auditor principal = this.auditorService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Audition newAudition = this.auditionService.create(trip, principal);

		Assert.notNull(newAudition.getMoment());
		Assert.isNull(newAudition.getTitle());
		Assert.isNull(newAudition.getDescription());
		Assert.isTrue(newAudition.getAttachments().isEmpty());
		Assert.notNull(newAudition.getAuditor());
		Assert.notNull(newAudition.getTrip());

		super.authenticate(null);
	}

	@Test
	public void testSaveAudition() {
		super.authenticate("auditor1");

		final Auditor principal = this.auditorService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Audition newAudition = this.auditionService.create(trip, principal);

		newAudition.setTitle("Test title");
		newAudition.setDescription("Test description");
		newAudition.setDraft(true);
		final Collection<String> attachments = new ArrayList<String>();
		newAudition.setAttachments(attachments);

		final Audition savedAudition = this.auditionService.save(newAudition);

		final Collection<Audition> auditionsByAuditor = this.auditionService.findByAuditorId(principal.getId());
		Assert.isTrue(auditionsByAuditor.contains(savedAudition));

		final Collection<Audition> auditionsByTrip = this.tripService.findOne(trip.getId()).getAuditions();
		Assert.isTrue(auditionsByTrip.contains(savedAudition));

		super.authenticate(null);
	}

	@Test
	public void testDeleteAudition() {
		super.authenticate("auditor1");

		final Auditor principal = this.auditorService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Audition newAudition = this.auditionService.create(trip, principal);

		newAudition.setTitle("Test title");
		newAudition.setDescription("Test description");
		newAudition.setDraft(true);
		final Collection<String> attachments = new ArrayList<String>();
		newAudition.setAttachments(attachments);

		final Audition savedAudition = this.auditionService.save(newAudition);

		this.auditionService.delete(savedAudition);

		final Collection<Audition> auditions = this.auditionService.findByAuditorId(principal.getId());

		Assert.isTrue(!auditions.contains(savedAudition));

		super.authenticate(null);
	}

	@Test
	public void testFindOneToEdit() {
		super.authenticate("auditor1");

		Audition audition;
		audition = (Audition) this.auditorService.findByPrincipal().getAuditions().toArray()[0];

		this.auditionService.findOneToEdit(audition.getId());
		this.auditionService.checkPrincipal(audition);

		super.authenticate(null);
	}

	@Test
	public void testFindByAuditorId() {
		super.authenticate("auditor1");

		final Auditor auditor = this.auditorService.findByPrincipal();

		Audition audition;
		audition = (Audition) this.auditorService.findByPrincipal().getAuditions().toArray()[0];

		final Collection<Audition> auditions = this.auditionService.findByAuditorId(auditor.getId());
		Assert.isTrue(auditions.contains(audition));

		super.authenticate(null);
	}

	@Test
	public void testFindByTripId() {
		super.authenticate("auditor1");

		final Auditor auditor = this.auditorService.findByPrincipal();

		Audition audition;
		audition = (Audition) auditor.getAuditions().toArray()[0];

		Trip trip;
		trip = this.auditionService.findOne(audition.getId()).getTrip();

		final Collection<Audition> auditions = this.auditionService.findByTripId(trip.getId());
		Assert.isTrue(auditions.contains(audition));

		super.authenticate(null);
	}

}

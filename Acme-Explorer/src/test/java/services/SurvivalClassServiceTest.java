
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Location;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SurvivalClassServiceTest extends AbstractTest {

	@Autowired
	private SurvivalClassService	survivalClassService;
	@Autowired
	private ManagerService			managerService;


	@Test
	public void testCreate() {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();
		final SurvivalClass s = this.survivalClassService.create();

		Assert.isTrue(s.getManager().equals(manager));
		Assert.isNull(s.getTitle());
		Assert.isNull(s.getDescription());
		Assert.isNull(s.getMoment());
		Assert.isNull(s.getLocation());

		super.authenticate(null);
	}

	@Test
	public void testSave() throws ParseException {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();
		final Trip trip = (Trip) manager.getTrips().toArray()[0];
		final SurvivalClass s = this.survivalClassService.create();

		s.setTitle("Title 1");
		s.setDescription("Description 1");
		trip.getLegalText().setDefinitive(true);
		s.setTrip(trip);

		final Date moment = new SimpleDateFormat("yyyy/mm/dd").parse("2018/11/13");
		s.setMoment(moment);

		final Location location = new Location();
		location.setName("Location 1");
		location.setLongitude(15.0);
		location.setLatitude(11.0);
		s.setLocation(location);

		final SurvivalClass savedSc = this.survivalClassService.save(s);

		Assert.isTrue(this.survivalClassService.findByPrincipal().contains(savedSc));

		super.authenticate(null);
	}

	@Test
	public void testDelete() throws ParseException {
		super.authenticate("manager1");

		final Manager manager = this.managerService.findByPrincipal();
		final Trip trip = (Trip) manager.getTrips().toArray()[0];
		final SurvivalClass s = this.survivalClassService.create();

		s.setTitle("Title 2");
		s.setDescription("Description 2");
		trip.getLegalText().setDefinitive(true);

		s.setTrip(trip);

		final Date moment = new SimpleDateFormat("yyyy/mm/dd").parse("2018/11/14");
		s.setMoment(moment);

		final Location location = new Location();
		location.setName("Location 2");
		location.setLongitude(16.0);
		location.setLatitude(12.0);
		s.setLocation(location);

		final SurvivalClass savedSc = this.survivalClassService.save(s);
		this.survivalClassService.delete(savedSc);

		final Collection<SurvivalClass> scs = this.survivalClassService.findAll();
		Assert.isTrue(!scs.contains(savedSc));

		super.authenticate(null);
	}

	@Test
	public void testFindAvailableToEnrol() {
		super.authenticate("explorer1");

		final Collection<SurvivalClass> scAvailables = this.survivalClassService.findAvailableToEnrol();
		Assert.notNull(scAvailables);

		super.authenticate(null);
	}

	@Test
	public void testFindOneToEdit() {
		super.authenticate("manager1");

		final Manager m = this.managerService.findByPrincipal();
		final SurvivalClass aux = (SurvivalClass) m.getSurvivalClasses().toArray()[0];
		final SurvivalClass sc = this.survivalClassService.findOneToEdit(aux.getId());

		sc.setDescription("Edited description");
		sc.getTrip().getLegalText().setDefinitive(true);

		final SurvivalClass editedSc = this.survivalClassService.save(sc);

		Assert.isTrue(this.survivalClassService.findAll().contains(editedSc));
		Assert.isTrue(aux.getDescription().equals(editedSc.getDescription()));

		super.authenticate(null);
	}
}


package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RangerServiceTest extends AbstractTest {

	@Autowired
	private RangerService	rangerService;


	@Test
	public void testCreate() {
		super.authenticate("administrator");

		final Ranger ranger = this.rangerService.create();
		Assert.isNull(ranger.getAddress());
		Assert.isNull(ranger.getCurriculum());
		Assert.isNull(ranger.getEmail());
		Assert.isNull(ranger.getName());
		Assert.isNull(ranger.getPhone());
		Assert.isNull(ranger.getSurname());
		Assert.notNull(ranger.getTrips());
		Assert.isTrue(!ranger.isBanned());
		Assert.isTrue(!ranger.isSuspicious());

		super.unauthenticate();
	}

	@Test
	public void testRegister() {

		final Ranger ranger = this.rangerService.create();
		ranger.setAddress("Address");
		ranger.setSuspicious(true);
		ranger.setEmail("anewmail@mail.com");
		ranger.setName("Nuevo");
		ranger.setPhone("0011");
		ranger.setSurname("Nuevo");
		ranger.getUserAccount().setUsername("user123e");
		ranger.getUserAccount().setPassword("passwordadada");
		final Ranger saved = this.rangerService.save(ranger);
		Assert.isTrue(this.rangerService.findAll().contains(saved));
	}

	@Test
	public void testSave() {
		super.authenticate("ranger1");

		final Ranger ranger = this.rangerService.findByPrincipal();
		ranger.setName("nuevoNombre1");
		final Ranger rangerModified = this.rangerService.save(ranger);
		Assert.isTrue(ranger.getName().equals(rangerModified.getName()));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("administrator");

		final Ranger ranger = this.rangerService.create();
		ranger.setAddress("Address");
		ranger.setSuspicious(true);
		ranger.setEmail("anewmail@mail.com");
		ranger.setName("Nuevo");
		ranger.setPhone("0011");
		ranger.setSurname("Nuevo");
		ranger.getUserAccount().setUsername("user123e");
		ranger.getUserAccount().setPassword("passwordadada");
		final Ranger saved = this.rangerService.save(ranger);
		this.rangerService.delete(saved);
		Assert.isTrue(!this.rangerService.findAll().contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testGetRatioRangersWithCurriculaRegistered() {
		super.authenticate("administrator");

		final Double res = this.rangerService.getRatioRangersWithCurriculaRegistered();
		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void testGetRatioRangersWithCurriculumEndorsed() {
		super.authenticate("administrator");

		final Double res = this.rangerService.getRatioRangersWithCurriculumEndorsed();
		Assert.notNull(res);

		super.unauthenticate();
	}

	@Test
	public void testGetRatioRangersSuspicious() {
		super.authenticate("administrator");

		final Double res = this.rangerService.getRatioRangersSuspicious();
		Assert.notNull(res);

		super.unauthenticate();
	}

}

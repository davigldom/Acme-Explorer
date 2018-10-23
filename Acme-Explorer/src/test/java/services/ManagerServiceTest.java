
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Manager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ManagerServiceTest extends AbstractTest {

	@Autowired
	private ManagerService	managerService;


	@Test
	public void testCreate() {
		super.authenticate("administrator");

		final Manager manager = this.managerService.create();
		Assert.isNull(manager.getAddress());
		Assert.isNull(manager.getEmail());
		Assert.isTrue(!manager.isBanned());
		Assert.isTrue(!manager.isSuspicious());
		Assert.isNull(manager.getName());
		Assert.isNull(manager.getPhone());
		Assert.isNull(manager.getSurname());
		Assert.isTrue(manager.getSocialIdentities().size() == 0);
		Assert.isTrue(manager.getSurvivalClasses().isEmpty());
		Assert.isTrue(manager.getTrips().isEmpty());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("administrator");

		final Manager m = this.managerService.create();

		m.setName("Name 1");
		m.setSurname("Surname 1");
		m.setEmail("email1@gmail.com");
		m.setPhone("645978312");
		m.setAddress("Calle Falsa, 123");
		m.getUserAccount().setUsername("abcdef");
		m.getUserAccount().setPassword("password");

		final Manager managerSaved = this.managerService.save(m);
		Assert.isTrue(this.managerService.findAll().contains(managerSaved));

		super.authenticate(null);
	}

}

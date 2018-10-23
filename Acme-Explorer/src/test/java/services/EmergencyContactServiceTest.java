
package services;

import java.text.ParseException;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EmergencyContact;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EmergencyContactServiceTest extends AbstractTest {

	@Autowired
	private EmergencyContactService	emergencyContactService;

	//Supporting services

	@Autowired
	private ExplorerService			explorerService;


	//Tests

	@Test
	public void testCreateEmergencyContact() {
		super.authenticate("explorer1");

		final EmergencyContact e = this.emergencyContactService.create();
		Assert.notNull(e);
		Assert.isNull(e.getName());
		Assert.isNull(e.getPhone());
		Assert.isNull(e.getEmail());

		super.authenticate(null);
	}

	@Test
	public void testSaveEmergencyContact() {
		super.authenticate("explorer1");

		final EmergencyContact e = this.emergencyContactService.create();

		e.setEmail("example@gmail.com");
		e.setName("John Doe");
		e.setPhone("678546387");

		final EmergencyContact eSaved = this.emergencyContactService.save(e);
		Assert.notNull(e);
		Assert.notNull(e.getEmail());
		Assert.notNull(e.getName());
		Assert.notNull(e.getPhone());

		Assert.isTrue(this.emergencyContactService.findAll().contains(eSaved));
		Assert.isTrue(this.explorerService.findByPrincipal().getEmergencyContacts().contains(eSaved));

		super.authenticate(null);
	}

	@Test
	public void testDeleteEducationRecord() throws ParseException {
		super.authenticate("Explorer1");

		final EmergencyContact e = this.emergencyContactService.create();

		e.setEmail("example@gmail.com");
		e.setName("John Doe");
		e.setPhone("678546387");

		final EmergencyContact eSaved = this.emergencyContactService.save(e);
		Assert.notNull(e);
		Assert.notNull(e.getEmail());
		Assert.notNull(e.getName());
		Assert.notNull(e.getPhone());

		Assert.isTrue(this.emergencyContactService.findAll().contains(eSaved));
		Assert.isTrue(this.explorerService.findByPrincipal().getEmergencyContacts().contains(eSaved));
		this.emergencyContactService.delete(eSaved);
		Assert.isTrue(!this.emergencyContactService.findAll().contains(eSaved));
		Assert.isTrue(!this.explorerService.findByPrincipal().getEmergencyContacts().contains(eSaved));

		super.authenticate(null);
	}
}

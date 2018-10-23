
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Auditor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AuditorServiceTest extends AbstractTest {

	@Autowired
	private AuditorService	auditorService;


	@Test
	public void testCreate() {
		super.authenticate("auditor1");

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Auditor auditor = this.auditorService.create();
		Assert.isNull(auditor.getAddress());
		Assert.isNull(auditor.getEmail());
		Assert.isNull(auditor.getName());
		Assert.isNull(auditor.getPhone());
		Assert.isNull(auditor.getSurname());

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("auditor1");

		final Auditor auditor = this.auditorService.findByPrincipal();

		auditor.setAddress("Address");
		final Auditor saved = this.auditorService.save(auditor);
		Assert.isTrue(auditor.getAddress().equals(saved.getAddress()));

		super.unauthenticate();
	}

}

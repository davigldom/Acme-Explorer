
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	@Autowired
	public SponsorService	sponsorService;


	@Test
	public void testCreate() {
		super.authenticate("administrator");

		final Sponsor sponsor = this.sponsorService.create();
		Assert.isNull(sponsor.getAddress());
		Assert.isNull(sponsor.getEmail());
		Assert.isNull(sponsor.getName());
		Assert.isNull(sponsor.getPhone());
		Assert.isNull(sponsor.getSurname());
		Assert.isNull(sponsor.getUserAccount());

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("sponsor1");

		final Sponsor sponsor = this.sponsorService.findByPrincipal();
		sponsor.setAddress("Address");
		final Sponsor saved = this.sponsorService.save(sponsor);
		Assert.isTrue(sponsor.getAddress().equals(saved.getAddress()));

		super.unauthenticate();
	}
}

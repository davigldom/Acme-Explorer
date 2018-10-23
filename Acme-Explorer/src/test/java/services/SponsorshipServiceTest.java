
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
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	@Autowired
	public SponsorshipService	sponsorshipService;
	@Autowired
	public TripService			tripService;
	@Autowired
	public SponsorService		sponsorService;
	@Autowired
	public CreditCardService	creditCardService;


	@Test
	public void testCreate() {
		super.authenticate("sponsor1");
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Sponsorship sponsorship = this.sponsorshipService.create(this.sponsorService.findByPrincipal(), creditCard, trip);
		Assert.isNull(sponsorship.getBanner());
		Assert.isNull(sponsorship.getLink());

		super.unauthenticate();
	}

	@Test
	public void testSave() {
		super.authenticate("sponsor1");

		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];
		final Sponsor principal = this.sponsorService.findByPrincipal();

		final Sponsorship sponsorship = this.sponsorshipService.create(principal, creditCard, trip);
		sponsorship.setBanner("http://url.com/");
		sponsorship.setLink("http://link.com/");
		final Sponsorship saved = this.sponsorshipService.save(sponsorship);

		final Collection<Sponsorship> sponsorships = this.sponsorshipService.findBySponsorPrincipal();
		Assert.isTrue(sponsorships.contains(saved));

		super.unauthenticate();
	}
	@Test
	public void testDelete() {
		super.authenticate("sponsor1");

		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final CreditCard creditCard = (CreditCard) this.creditCardService.findAll().toArray()[0];

		final Sponsorship sponsorship = this.sponsorshipService.create(this.sponsorService.findByPrincipal(), creditCard, trip);

		sponsorship.setBanner("http://url.com/");
		sponsorship.setCreditCard(creditCard);
		sponsorship.setLink("http://link.com/");
		final Sponsorship saved = this.sponsorshipService.save(sponsorship);

		this.sponsorshipService.delete(saved);
		Assert.isTrue(!this.sponsorshipService.findAll().contains(sponsorship));

		super.unauthenticate();
	}
}

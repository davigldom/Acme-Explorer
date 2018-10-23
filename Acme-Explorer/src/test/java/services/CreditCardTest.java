
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Explorer;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardTest extends AbstractTest {

	@Autowired
	CreditCardService	creditCardService;

	@Autowired
	ExplorerService		explorerService;

	@Autowired
	SponsorService		sponsorService;


	@Test
	public void testCreateCreditCard() {
		super.authenticate("explorer1");
		final CreditCard newCreditCard = this.creditCardService.create();
		Assert.isNull(newCreditCard.getHolderName());
		Assert.isNull(newCreditCard.getBrandName());
		Assert.isNull(newCreditCard.getNumber());
		Assert.isNull(newCreditCard.getExplorer());
		Assert.isNull(newCreditCard.getSponsor());
		Assert.notNull(newCreditCard.getExpirationMonth());
		Assert.notNull(newCreditCard.getExpirationYear());
		Assert.notNull(newCreditCard.getCVV());
		super.authenticate(null);
	}

	@Test
	public void testSaveCreditCardExplorer() {
		super.authenticate("explorer1");
		final Explorer principal = this.explorerService.findByPrincipal();
		final CreditCard newCreditCard = this.creditCardService.create();
		newCreditCard.setHolderName("Holder name to test the save");
		newCreditCard.setBrandName("Brand name to test the save");
		newCreditCard.setNumber("123456789");
		newCreditCard.setExpirationMonth(12);
		newCreditCard.setExpirationYear(24);
		newCreditCard.setCVV(123);
		newCreditCard.setExplorer(principal);
		final CreditCard savedCreditCard = this.creditCardService.save(newCreditCard);
		final CreditCard creditCard = this.creditCardService.findOne(savedCreditCard.getId());
		Assert.isTrue(creditCard.equals(savedCreditCard));
		super.authenticate(null);
	}

	@Test
	public void testSaveCreditCardAuditor() {
		super.authenticate("sponsor1");
		final Sponsor principal = this.sponsorService.findByPrincipal();
		final CreditCard newCreditCard = this.creditCardService.create();
		newCreditCard.setHolderName("Holder name to test the save");
		newCreditCard.setBrandName("Brand name to test the save");
		newCreditCard.setNumber("123456789");
		newCreditCard.setExpirationMonth(12);
		newCreditCard.setExpirationYear(24);
		newCreditCard.setCVV(123);
		newCreditCard.setSponsor(principal);
		final CreditCard savedCreditCard = this.creditCardService.save(newCreditCard);
		final CreditCard creditCard = this.creditCardService.findOne(savedCreditCard.getId());
		Assert.isTrue(creditCard.equals(savedCreditCard));
		super.authenticate(null);
	}
}

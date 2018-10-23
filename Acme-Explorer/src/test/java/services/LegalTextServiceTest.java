
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
import domain.LegalText;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class LegalTextServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private LegalTextService		legalTextService;


	@Test
	public void testCreate() {
		super.authenticate("administrator");

		final LegalText legalText = this.legalTextService.create();

		Assert.notNull(this.administratorService.findByPrincipal());
		Assert.isNull(legalText.getTitle());
		Assert.isNull(legalText.getBody());
		Assert.isNull(legalText.getApplicableLaws());
		Assert.isTrue(!legalText.isDefinitive());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("administrator");

		final LegalText legalText = this.legalTextService.create();

		Assert.notNull(this.administratorService.findByPrincipal());

		legalText.setTitle("Text's title 1");
		legalText.setBody("Text's body 1");
		final String law = "Applicable law 1";
		legalText.setApplicableLaws(law);
		final Collection<Trip> trips = new ArrayList<Trip>();
		legalText.setTrips(trips);

		final LegalText legalTextSaved = this.legalTextService.save(legalText);

		Assert.isTrue(this.legalTextService.findAll().contains(legalTextSaved));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("administrator");

		final LegalText legalText = this.legalTextService.create();

		Assert.notNull(this.administratorService.findByPrincipal());

		legalText.setTitle("Text's title 2");
		legalText.setBody("Text's body 2");
		final String law = "Applicable law 2";
		legalText.setApplicableLaws(law);
		final Collection<Trip> trips = new ArrayList<Trip>();
		legalText.setTrips(trips);

		final LegalText legalTextSaved = this.legalTextService.save(legalText);
		this.legalTextService.delete(legalTextSaved);

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();
		Assert.isTrue(!legalTexts.contains(legalTextSaved));

		super.authenticate(null);
	}

	@Test
	public void testEdit() {
		super.authenticate("administrator");

		final LegalText legalText = this.legalTextService.create();

		legalText.setTitle("Title 1");
		legalText.setBody("Text's body");
		final String law = "Applicable law";
		legalText.setApplicableLaws(law);
		final Collection<Trip> trips = new ArrayList<Trip>();
		legalText.setTrips(trips);

		final LegalText legalTextSaved = this.legalTextService.save(legalText);

		final LegalText legalTextToEdit = this.legalTextService.findOneToEdit(legalTextSaved.getId());

		legalTextToEdit.setTitle("Title 2");

		final LegalText legalTextEdited = this.legalTextService.save(legalTextToEdit);

		final Collection<LegalText> legalTexts = this.legalTextService.findAll();
		Assert.isTrue(legalTexts.contains(legalTextEdited));
		Assert.isTrue(legalTextEdited.getTitle().equals("Title 2"));

		super.authenticate(null);
	}

}

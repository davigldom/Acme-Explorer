
package services;

import java.sql.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.Period;
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	@Autowired
	public ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService rangerService;

	@Test
	public void testCreate() {
		super.authenticate("ranger1");

		final ProfessionalRecord pr = this.professionalRecordService.create();
		Assert.isNull(pr.getAttachment());
		Assert.isNull(pr.getComments());
		Assert.isNull(pr.getCompanyName());
		Assert.isNull(pr.getProfessionalPeriod());
		Assert.isNull(pr.getRole());

		super.unauthenticate();
	}

	@Test
	public void testFindAll() {
		Assert.notNull(this.professionalRecordService.findAll());
	}

	@Test
	public void testSave() {
		super.authenticate("ranger2");

		final ProfessionalRecord pr = this.professionalRecordService.create();
		final Period professionalPeriod = new Period();
		professionalPeriod.setStart(new Date(2000 / 01 / 01));
		professionalPeriod.setEnd(new Date(2001 / 01 / 01));
		Curriculum curriculum = this.curriculumService.findByRangerId(this.rangerService.findByPrincipal().getId());
		pr.setAttachment("http://www.attachment1.com/");
		pr.setCompanyName("A company name");
		pr.setProfessionalPeriod(professionalPeriod);
		pr.setRole("Role prueba");
		final ProfessionalRecord saved = this.professionalRecordService.save(pr, curriculum);
		Assert.isTrue(this.professionalRecordService.findAll().contains(saved));

		super.unauthenticate();
	}

	@Test
	public void testDelete() {
		super.authenticate("ranger2");
		Curriculum curriculum = this.curriculumService.findByRangerId(this.rangerService.findByPrincipal().getId());
		final ProfessionalRecord pr = this.professionalRecordService.create();
		final Period professionalPeriod = new Period();
		professionalPeriod.setStart(new Date(2000 / 01 / 01));
		professionalPeriod.setEnd(new Date(2001 / 01 / 01));
		pr.setAttachment("http://www.attachment1.com/");
		pr.setCompanyName("A company name");
		pr.setProfessionalPeriod(professionalPeriod);
		pr.setRole("Role prueba");
		final ProfessionalRecord saved = this.professionalRecordService.save(pr, curriculum);
		this.professionalRecordService.delete(saved, curriculum);
		Assert.isTrue(!this.professionalRecordService.findAll().contains(saved));

		super.unauthenticate();
	}
}

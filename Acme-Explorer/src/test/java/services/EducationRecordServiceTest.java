
package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.EducationRecord;
import domain.Period;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService	rangerService;
	
	@Autowired
	private PersonalRecordService	personalRecordService;
	
	//Tests

	@Test
	public void testCreateEducationRecord() {
		super.authenticate("ranger1");

		final EducationRecord educationRecord = this.educationRecordService.create();
		Assert.notNull(educationRecord);
		Assert.isNull(educationRecord.getAttachment());
		Assert.isNull(educationRecord.getComments());
		Assert.isNull(educationRecord.getTitleOfDiploma());
		Assert.isNull(educationRecord.getInstitution());
		Assert.isNull(educationRecord.getEducationPeriod());

		super.authenticate(null);
	}

	@Test
	public void testSaveEducationRecord() throws ParseException {
		super.authenticate("ranger1");
		final PersonalRecord newRecord = this.personalRecordService.create();
		final List<String> comments = new ArrayList<>();
		comments.add("Very good");

		newRecord.setName("Test name");
		newRecord.setPhoto("https://www.googleimages.com");
		newRecord.setEmail("test@acme.co");
		newRecord.setPhone("664052167");
		newRecord.setLinkedIn("https://www.linkedin.com");

		final PersonalRecord savedRecord = this.personalRecordService.save(newRecord);


		final Curriculum newCurriculum = this.curriculumService.create(savedRecord);

		final Curriculum savedCurriculum = this.curriculumService.save(newCurriculum);
		
		final EducationRecord e = this.educationRecordService.create();

		final Period period = new Period();
		final Date startDate = new SimpleDateFormat("yyyy/mm/dd").parse("2010/11/14");
		final Date endDate = new SimpleDateFormat("yyyy/mm/dd").parse("2014/11/14");
		period.setEnd(startDate);
		period.setStart(endDate);

		e.setTitleOfDiploma("Computer software engineering");
		e.setAttachment("http://www.us.es/");
		e.setInstitution("University of Seville");
		e.setEducationPeriod(period);

		final EducationRecord eSaved = this.educationRecordService.save(e, savedCurriculum);
		Assert.notNull(eSaved);
		Assert.notNull(eSaved.getAttachment());
		Assert.isNull(eSaved.getComments());
		Assert.notNull(eSaved.getTitleOfDiploma());
		Assert.notNull(eSaved.getInstitution());
		Assert.notNull(eSaved.getEducationPeriod());
		Assert.isTrue(this.educationRecordService.findAll().contains(eSaved));

		super.authenticate(null);
	}

	@Test
	public void testDeleteEducationRecord() throws ParseException {
		super.authenticate("ranger2");
		Curriculum curriculum = this.curriculumService.findByRangerId(this.rangerService.findByPrincipal().getId());
		final EducationRecord e = this.educationRecordService.create();

		final Period period = new Period();
		final Date startDate = new SimpleDateFormat("yyyy/mm/dd").parse("2010/11/14");
		final Date endDate = new SimpleDateFormat("yyyy/mm/dd").parse("2014/11/14");
		period.setEnd(startDate);
		period.setStart(endDate);

		e.setTitleOfDiploma("Computer software engineering");
		e.setAttachment("http://www.us.es/");
		e.setInstitution("University of Seville");
		e.setEducationPeriod(period);

		final EducationRecord eSaved = this.educationRecordService.save(e, curriculum);

		this.educationRecordService.delete(eSaved, curriculum);
		Assert.isTrue(!this.educationRecordService.findAll().contains(eSaved));

		super.authenticate(null);
	}
}

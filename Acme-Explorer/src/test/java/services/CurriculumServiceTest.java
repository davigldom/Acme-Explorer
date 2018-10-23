
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.PersonalRecord;
import domain.Ranger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService		rangerService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Test
	public void testCreateCurriculum() {
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
		Assert.notNull(newCurriculum.getPersonalRecord());
		Assert.isTrue(newCurriculum.getEducationRecords().isEmpty());
		Assert.isTrue(newCurriculum.getProfessionalRecords().isEmpty());
		Assert.isTrue(newCurriculum.getEndorserRecords().isEmpty());
		Assert.isTrue(newCurriculum.getMiscellaneousRecords().isEmpty());

		super.authenticate(null);
	}

	@Test
	public void testSaveCurriculum() {
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
		final Collection<Curriculum> curriculums = this.curriculumService.findAll();
		Assert.isTrue(curriculums.contains(savedCurriculum));

		super.authenticate(null);
	}

	@Test
	public void testDeleteCurriculum() {
		super.authenticate("ranger2");

		final Ranger principal = this.rangerService.findByPrincipal();
		final Curriculum curriculum = this.curriculumService.findByRangerId(principal.getId());

		final Curriculum savedCurriculum = this.curriculumService.save(curriculum);

		this.curriculumService.delete(savedCurriculum);
		Assert.isTrue(!this.curriculumService.findAll().contains(savedCurriculum));

		super.authenticate(null);
	}
}

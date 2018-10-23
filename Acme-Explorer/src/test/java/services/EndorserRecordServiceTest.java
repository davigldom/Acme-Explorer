
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Curriculum;
import domain.EndorserRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService rangerService;
	
	//Tests

	@Test
	public void testCreateEndorserRecord() {
		super.authenticate("ranger1");

		final EndorserRecord endorserRecord = this.endorserRecordService.create();
		Assert.notNull(endorserRecord);
		Assert.isNull(endorserRecord.getEmail());
		Assert.isNull(endorserRecord.getComments());
		Assert.isNull(endorserRecord.getLinkedIn());
		Assert.isNull(endorserRecord.getPhone());
		Assert.isNull(endorserRecord.getName());

		super.authenticate(null);
	}

	@Test
	public void testSaveEndorserRecord() {
		super.authenticate("ranger2");

		final EndorserRecord e = this.endorserRecordService.create();
		Curriculum curriculum = this.curriculumService.findByRangerId(this.rangerService.findByPrincipal().getId());
		e.setComments("comments");
		e.setEmail("example@gmail.com");
		e.setLinkedIn("http://www.us.es/");
		e.setName("John Lock");
		e.setPhone("678658470");

		final EndorserRecord eSaved = this.endorserRecordService.save(e, curriculum);
		Assert.notNull(e);
		Assert.notNull(e.getEmail());
		Assert.notNull(e.getComments());
		Assert.notNull(e.getLinkedIn());
		Assert.notNull(e.getName());
		Assert.notNull(e.getPhone());
		Assert.isTrue(this.endorserRecordService.findAll().contains(eSaved));

		super.authenticate(null);
	}

	@Test
	public void testDeleteEndorserRecord() {
		super.authenticate("ranger2");

		final EndorserRecord e = this.endorserRecordService.create();
		Curriculum curriculum = this.curriculumService.findByRangerId(this.rangerService.findByPrincipal().getId());
		e.setComments("comments");
		e.setEmail("example@gmail.com");
		e.setLinkedIn("http://www.us.es/");
		e.setName("John Lock");
		e.setPhone("678658470");

		final EndorserRecord eSaved = this.endorserRecordService.save(e, curriculum);
		Assert.notNull(e);
		Assert.notNull(e.getEmail());
		Assert.notNull(e.getComments());
		Assert.notNull(e.getLinkedIn());
		Assert.notNull(e.getName());
		Assert.notNull(e.getPhone());
		Assert.isTrue(this.endorserRecordService.findAll().contains(eSaved));

		this.endorserRecordService.delete(eSaved, curriculum);
		Assert.isTrue(!this.endorserRecordService.findAll().contains(eSaved));

		super.authenticate(null);
	}
}

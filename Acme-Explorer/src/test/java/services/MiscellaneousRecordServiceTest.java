
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
import domain.Curriculum;
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private RangerService rangerService;

	@Test
	public void testCreate() {
		super.authenticate("ranger1");
		final MiscellaneousRecord result = this.miscellaneousRecordService.create();
		Assert.isNull(result.getAttachment());
		Assert.isNull(result.getTitle());
		Assert.isNull(result.getComments());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("ranger2");
		final MiscellaneousRecord newRecord = this.miscellaneousRecordService.create();
		Curriculum curriculum = this.curriculumService.findByRangerId(this.rangerService.findByPrincipal().getId());
		newRecord.setTitle("Test title");
		newRecord.setAttachment("https://www.google.com");
		newRecord.setComments("comments");

		final MiscellaneousRecord savedRecord = this.miscellaneousRecordService.save(newRecord, curriculum);

		final Collection<MiscellaneousRecord> records = this.miscellaneousRecordService.findAll();
		Assert.isTrue(records.contains(savedRecord));

		super.authenticate(null);
	}
	@Test
	public void testDelete() {
		super.authenticate("ranger2");
		final MiscellaneousRecord newRecord = this.miscellaneousRecordService.create();
		Curriculum curriculum = this.curriculumService.findByRangerId(this.rangerService.findByPrincipal().getId());
		newRecord.setTitle("Test title");
		newRecord.setAttachment("https://www.google.com");
		newRecord.setComments("comments");

		final MiscellaneousRecord savedRecord = this.miscellaneousRecordService.save(newRecord, curriculum);
		this.miscellaneousRecordService.delete(savedRecord, curriculum);

		final Collection<MiscellaneousRecord> records = this.miscellaneousRecordService.findAll();
		Assert.isTrue(!records.contains(savedRecord));

		super.authenticate(null);
	}
}

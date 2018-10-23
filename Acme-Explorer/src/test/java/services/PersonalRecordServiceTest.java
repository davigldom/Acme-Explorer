
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	@Autowired
	private PersonalRecordService	personalRecordService;


	@Test
	public void testCreate() {
		super.authenticate("ranger1");
		final PersonalRecord result = this.personalRecordService.create();
		Assert.isNull(result.getName());
		Assert.isNull(result.getPhoto());
		Assert.isNull(result.getEmail());
		Assert.isNull(result.getPhone());
		Assert.isNull(result.getLinkedIn());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
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

		final Collection<PersonalRecord> records = this.personalRecordService.findAll();
		Assert.isTrue(records.contains(savedRecord));

		super.authenticate(null);
	}

}

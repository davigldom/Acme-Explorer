
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Tag;
import domain.TagValue;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagValueServiceTest extends AbstractTest {

	@Autowired
	private TagValueService	tagValueService;
	@Autowired
	private TripService		tripService;
	@Autowired
	private TagService		tagService;


	@Test
	public void testCreate() {
		super.authenticate("administrator");

		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Tag tag = (Tag) this.tagService.findAll().toArray()[0];
		Assert.notNull(trip);
		Assert.notNull(tag);

		final TagValue tv = this.tagValueService.create();
		Assert.isNull(tv.getValue());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("manager1");

		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Tag tag = (Tag) this.tagService.findAll().toArray()[0];
		Assert.notNull(trip);
		Assert.notNull(tag);

		final TagValue tv = this.tagValueService.create();
		tv.setValue("EASY");
		tv.setTrip(trip);
		tv.setTag(tag);

		final TagValue tvSaved = this.tagValueService.save(tv);
		Assert.isTrue(this.tagValueService.findAll().contains(tvSaved));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("manager1");

		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Tag tag = (Tag) this.tagService.findAll().toArray()[0];
		Assert.notNull(trip);
		Assert.notNull(tag);

		final TagValue tv = this.tagValueService.create();
		tv.setValue("EASY");
		tv.setTrip(trip);
		tv.setTag(tag);

		final TagValue tvSaved = this.tagValueService.save(tv);
		this.tagValueService.delete(tvSaved);

		Assert.isTrue(!this.tagValueService.findAll().contains(tvSaved));

		super.authenticate(null);
	}
}

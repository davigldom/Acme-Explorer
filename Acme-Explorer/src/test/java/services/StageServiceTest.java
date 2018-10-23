
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
import domain.Stage;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StageServiceTest extends AbstractTest {

	@Autowired
	public StageService	stageService;

	@Autowired
	public TripService	tripService;

	@Test
	public void testCreate() {
		super.authenticate("manager1");
		final Stage stage = this.stageService.create();
		Assert.isNull(stage.getDescription());
		Assert.notNull(stage.getPrice());
		Assert.isNull(stage.getTitle());
		super.authenticate(null);

	}


	@Test
	public void testFindAll() {
		super.authenticate("manager1");
		final Collection<Stage> stages = this.stageService.findAll();
		Assert.notNull(stages);
		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("manager1");
		Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Stage stage = this.stageService.create();
		stage.setDescription("Description");
		stage.setPrice(50.0);
		stage.setTitle("A title");
		final Stage saved = this.stageService.save(stage, trip);
		Assert.isTrue(this.stageService.findAll().contains(saved));
		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("manager1");
		Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Stage stage = this.stageService.create();
		stage.setDescription("Description");
		stage.setPrice(50.0);
		stage.setTitle("A title");
		final Stage saved = this.stageService.save(stage, trip);
		this.stageService.delete(saved, trip);
		Assert.isTrue(!this.stageService.findAll().contains(saved));
		super.authenticate(null);
	}
}

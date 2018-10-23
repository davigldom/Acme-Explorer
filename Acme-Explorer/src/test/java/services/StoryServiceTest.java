
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class StoryServiceTest extends AbstractTest {

	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private TripService		tripService;
	@Autowired
	private StoryService	storyService;


	@Test
	public void testCreate() {
		super.authenticate("explorer2");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip t = (Trip) this.tripService.findAcceptedApplicationsOfExplorer(explorer.getId()).toArray()[0];
		final Story s = this.storyService.create(t);

		Assert.isTrue(s.getExplorer().equals(explorer));
		Assert.notNull(s.getTrip());
		Assert.isNull(s.getTitle());
		Assert.isNull(s.getText());
		Assert.isNull(s.getAttachments());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("explorer2");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip t = (Trip) this.tripService.findAcceptedApplicationsOfExplorer(explorer.getId()).toArray()[0];
		final Story s = this.storyService.create(t);

		s.setTitle("Title 1");
		s.setText("Text 1");
		final Set<String> attachments = new HashSet<String>();
		final String e = "http://www.acme-explorer.com/stories/story1";
		attachments.add(e);
		s.setAttachments(attachments);

		final Story storySaved = this.storyService.save(s);

		Assert.isTrue(this.storyService.findByPrincipal().contains(storySaved));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("explorer2");

		final Explorer explorer = this.explorerService.findByPrincipal();
		final Trip t = (Trip) this.tripService.findAcceptedApplicationsOfExplorer(explorer.getId()).toArray()[0];
		final Story s = this.storyService.create(t);

		s.setTitle("Title 2");
		s.setText("Text 2");
		final Set<String> attachments = new HashSet<String>();
		final String e = "http://www.acme-explorer.com/stories/story2";
		attachments.add(e);
		s.setAttachments(attachments);

		final Story storySaved = this.storyService.save(s);
		this.storyService.delete(storySaved);

		final Collection<Story> stories = this.storyService.findByPrincipal();
		Assert.isTrue(!stories.contains(storySaved));

		super.authenticate(null);
	}
}


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
import domain.Tag;
import domain.TagValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TagServiceTest extends AbstractTest {

	@Autowired
	private TagService	tagService;


	@Test
	public void testCreate() {
		super.authenticate("administrator");

		Tag t;
		t = this.tagService.create();

		Assert.isNull(t.getName());
		Assert.isNull(t.getTagValues());

		super.authenticate(null);
	}

	@Test
	public void testSave() {
		super.authenticate("administrator");

		Tag tag;

		tag = this.tagService.create();

		final Set<TagValue> tagValues = new HashSet<TagValue>();
		tag.setName("DIFFICULTY");
		tag.setTagValues(tagValues);

		final Tag savedT = this.tagService.save(tag);

		Assert.isTrue(this.tagService.findAll().contains(savedT));

		super.authenticate(null);
	}

	@Test
	public void testDelete() {
		super.authenticate("administrator");

		Tag tag;

		tag = this.tagService.create();

		final Set<TagValue> tagValues = new HashSet<TagValue>();
		tag.setName("DANGEROUS");
		tag.setTagValues(tagValues);

		final Tag savedT = this.tagService.save(tag);

		this.tagService.delete(savedT);
		Assert.isTrue(!this.tagService.findAll().contains(savedT));

		super.authenticate(null);
	}

	@Test
	public void testFindNotUsed() {
		final Collection<Tag> tagsNotUsed = this.tagService.findNotUsed();
		Assert.notNull(tagsNotUsed);
	}

	@Test
	public void testFindOneToEdit() {
		super.authenticate("administrator");

		Tag tag;

		tag = this.tagService.create();

		final Set<TagValue> tagValues = new HashSet<TagValue>();
		tag.setName("DIFFICULTY");
		tag.setTagValues(tagValues);

		final Tag savedT = this.tagService.save(tag);
		Assert.isTrue(this.tagService.findAll().contains(savedT));

		final Collection<Tag> tagsNotUsed = this.tagService.findNotUsed();
		final Tag notUsed = (Tag) tagsNotUsed.toArray()[0];

		notUsed.setName("Edited name");
		this.tagService.save(notUsed);

		super.authenticate(null);
	}
}

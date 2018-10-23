
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TagValueRepository;
import domain.Tag;
import domain.TagValue;
import domain.Trip;

@Service
@Transactional
public class TagValueService {

	@Autowired
	private TagValueRepository	tagValueRepository;

	@Autowired
	private ManagerService		managerService;


	// CRUD METHODS
	public TagValue create() {

		final TagValue t = new TagValue();

		return t;
	}

	public Collection<TagValue> findAll() {
		return this.tagValueRepository.findAll();
	}

	public TagValue findOne(final int id) {
		return this.tagValueRepository.findOne(id);
	}

	public TagValue save(final TagValue t) {
		final Trip trip = t.getTrip();
		final Tag tag = t.getTag();
		Assert.notNull(t);
		Assert.isTrue(trip.getManager().equals(this.managerService.findByPrincipal()));
		for (TagValue tagValue : trip.getTagValues()) {
			//A trip cannot have two different values for one tag
			if(tagValue.getId()!=t.getId())Assert.isTrue(!tagValue.getTag().getName().equals(tag.getName()));
		}

		final TagValue result = this.tagValueRepository.save(t);

		if (t.getId() == 0)
			trip.getTagValues().add(result);
		if (t.getId() == 0)
			tag.getTagValues().add(result);

		return result;
	}

	public void delete(final TagValue t) {
		final Trip trip = t.getTrip();
		final Tag tag = t.getTag();
		Assert.notNull(t);

		trip.getTagValues().remove(t);
		tag.getTagValues().remove(t);

		this.tagValueRepository.delete(t);

	}

	// OTHER METHODS
}

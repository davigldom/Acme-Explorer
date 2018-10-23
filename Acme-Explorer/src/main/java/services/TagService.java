
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TagRepository;
import domain.Administrator;
import domain.Tag;

@Service
@Transactional
public class TagService {

	@Autowired
	private TagRepository			tagRepository;

	@Autowired
	private AdministratorService	administratorService;


	//CRUD METHODS
	public Tag create() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Tag s = new Tag();
		return s;
	}

	public Collection<Tag> findAll() {
		return this.tagRepository.findAll();
	}

	public Tag findOne(final int id) {
		return this.tagRepository.findOne(id);
	}

	public Tag save(final Tag t) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		
		Assert.notNull(t);
		Assert.isTrue(t.getTagValues().isEmpty());
		Assert.isNull(this.tagRepository.findTagName(t.getName()));
		
		return this.tagRepository.save(t);
	}

	public void delete(final Tag t) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.notNull(t);
		Assert.isTrue(t.getId() != 0);

		this.tagRepository.delete(t);
	}

	//OTHER METHODS

	public Collection<Tag> findNotUsed() {
		Collection<Tag> result;

		result = this.tagRepository.findNotUsed();

		return result;
	}

	public Tag findOneToEdit(final int id) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		final Tag t;
		t = this.findOne(id);
		Assert.notNull(t);

		final Collection<Tag> tagsNotUsed = this.findNotUsed();
		Assert.isTrue(tagsNotUsed.contains(t));

		return t;
	}
}

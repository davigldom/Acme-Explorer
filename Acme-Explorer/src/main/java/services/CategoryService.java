
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Administrator;
import domain.Category;
import domain.Trip;

@Service
@Transactional
public class CategoryService {

	// Managed repository--------------------------------------
	@Autowired
	private CategoryRepository		categoryRepository;

	//Supporting services---------------------------------------

	@Autowired
	private AdministratorService	administratorService;


	// Simple CRUD methods-------------------------------------

	public Category create(final Category parent) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.notNull(parent);

		Category result = null;
		result = new Category();
		result.setParent(parent);
		result.setChildren(new ArrayList<Category>());

		return result;
	}

	public Category findOne(final int categoryId) {
		Category result = null;
		result = this.categoryRepository.findOne(categoryId);
		return result;
	}

	public Collection<Category> findAll() {
		Collection<Category> result = null;
		result = this.categoryRepository.findAll();
		return result;
	}

	public Category save(final Category category) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);
		Assert.notNull(category);

		Category result = null;
		//Check that the name of the category is unique within the context of the same parent category
		final Collection<String> categoryNames;
		categoryNames = this.categoryRepository.findChildrenCategoryNames(category.getParent().getId());

		//First case: Editing a category without changing parent
		if (category.getParent().getChildren().contains(category)) {
			final Category oldCategory = this.findOne(category.getId());
			if (!oldCategory.getName().equals(category.getName()))
				Assert.isTrue(!categoryNames.contains(category.getName()));

			//Second case: saving a category with different parent
		} else
			Assert.isTrue(!categoryNames.contains(category.getName()));

		//Now that we checked if the name already exists, we save the category and add it to its parent
		result = this.categoryRepository.save(category);

		result.getParent().getChildren().add(result);

		return result;
	}
	public void delete(final Category category) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);

		category.getParent().getChildren().remove(category);
		final java.util.Iterator<Category> iter = category.getChildren().iterator();
		while (iter.hasNext()) {
			final Category child = iter.next();
			iter.remove();
			this.delete(child);

		}

		for (final Trip trip : category.getTrips()) {
			trip.setCategory(this.findDefaultCategory());
			this.findDefaultCategory().getTrips().add(trip);
		}
		category.getTrips().clear();
		this.categoryRepository.delete(category);
	}

	// Other business methods
	public Category findOneToEdit(final int categoryId) {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		Category result;
		result = this.findOne(categoryId);
		Assert.notNull(result);
		return result;
	}

	public Collection<String> findChildrenCategoryNames(final int categoryId) {

		Collection<String> result;
		result = this.categoryRepository.findChildrenCategoryNames(categoryId);
		return result;
	}

	public Category findDefaultCategory() {

		Category result;
		result = this.categoryRepository.findDefaultCategory();
		return result;
	}

}

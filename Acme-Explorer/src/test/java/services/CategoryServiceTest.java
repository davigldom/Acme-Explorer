
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
import domain.Category;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	@Autowired
	CategoryService			categoryService;

	@Autowired
	AdministratorService	administratorService;


	//Create method test
	@Test
	public void testCreateCategory() {
		super.authenticate("administrator");

		final Category parent = this.categoryService.findDefaultCategory();
		final Category newCategory = this.categoryService.create(parent);

		Assert.isTrue(newCategory.getParent().equals(parent));

		//		final Collection<String> categoryNames;
		//		categoryNames = this.categoryService.findChildrenCategoryNames(newCategory.getParent().getId());
		//		Assert.isTrue(categoryNames.contains(newCategory.getName()));

		super.authenticate(null);
	}

	@Test
	public void testSaveCategory() {
		super.authenticate("administrator");

		final Category parent = this.categoryService.findDefaultCategory();
		final Category newCategory = this.categoryService.create(parent);
		final List<Trip> trips = new ArrayList<>();
		newCategory.setName("TestCategoryName");
		newCategory.setTrips(trips);
		final Category savedCategory = this.categoryService.save(newCategory);

		final Collection<Category> categories = this.categoryService.findAll();
		Assert.isTrue(categories.contains(savedCategory));

		final Collection<String> categoryNames;
		categoryNames = this.categoryService.findChildrenCategoryNames(newCategory.getParent().getId());
		Assert.isTrue(categoryNames.contains(savedCategory.getName()));

		super.authenticate(null);
	}

	@Test
	public void testDeleteCategory() {
		super.authenticate("administrator");

		final Category parent = this.categoryService.findDefaultCategory();
		final Category newCategory = this.categoryService.create(parent);
		final List<Trip> trips = new ArrayList<>();
		newCategory.setName("TestCategoryName");
		newCategory.setTrips(trips);
		final Category savedCategory = this.categoryService.save(newCategory);
		this.categoryService.delete(savedCategory);

		final Collection<Category> categories;
		categories = this.categoryService.findAll();
		Assert.isTrue(!categories.contains(savedCategory));

		super.authenticate(null);
	}

	@Test
	public void testFindChildrenCategoryNames() {
		super.authenticate("administrator");

		final Category parent = this.categoryService.findDefaultCategory();
		final Category newCategory = this.categoryService.create(parent);
		final List<Trip> trips = new ArrayList<>();
		newCategory.setName("TestCategoryName");
		newCategory.setTrips(trips);
		final Category savedCategory = this.categoryService.save(newCategory);

		final Collection<String> categoryNames;
		categoryNames = this.categoryService.findChildrenCategoryNames(savedCategory.getParent().getId());
		Assert.isTrue(categoryNames.contains(savedCategory.getName()));

		super.authenticate(null);
	}

	@Test
	public void testFindDefaultCategory() {
		super.authenticate("administrator");

		final Category parent = this.categoryService.findDefaultCategory();
		//		final Category savedCategory = this.categoryService.save(parent);
		//
		//		Assert.isTrue(this.categoryService.findAll().contains(savedCategory));
		Assert.isTrue(parent.getName().contentEquals("CATEGORY"));

		super.authenticate(null);
	}

}

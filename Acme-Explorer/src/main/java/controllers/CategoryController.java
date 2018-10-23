
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------

	public CategoryController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer categoryId) {
		final ModelAndView result;
		final Collection<Category> categories;
		final Category category;

		if (categoryId == null)
			category = this.categoryService.findDefaultCategory();
		else
			category = this.categoryService.findOne(categoryId);

		categories = category.getChildren();

		result = new ModelAndView("category/list");

		result.addObject("categories", categories);
		result.addObject("category", category);
		result.addObject("requestURI", "category/list.do");

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;

		result = new ModelAndView("category/administrator/edit");
		result.addObject("category", category);

		if (category.getId() != 0) {

			result.addObject("trips", category.getTrips());
			result.addObject("parent", category.getParent());
			result.addObject("children", category.getChildren());
		}

		result.addObject("message", message);

		return result;
	}
}


package controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryAdministratorController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private CategoryService	categoryService;


	// Constructors -----------------------------------------------------------

	public CategoryAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int categoryParentId) {
		ModelAndView result;
		final Category categoryParent = this.categoryService.findOne(categoryParentId);

		final Category newCategory = this.categoryService.create(categoryParent);
		result = this.createEditModelAndView(newCategory);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = this.categoryService.findOne(categoryId);
		Assert.notNull(category);
		result = this.createEditModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:/category/list.do?categoryId=" + category.getParent().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "category.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/administrator/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Category category, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.delete(category);
				result = new ModelAndView("redirect:/category/list.do?categoryId=" + category.getParent().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(category, "category.commit.error");
			}

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

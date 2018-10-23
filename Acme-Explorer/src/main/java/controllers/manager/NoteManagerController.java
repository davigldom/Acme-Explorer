
package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;

import services.ManagerService;
import services.NoteService;
import services.SystemConfigService;
import domain.Manager;
import domain.Note;

@Controller
@RequestMapping("/note/manager")
public class NoteManagerController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private NoteService			noteService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private SystemConfigService	systemConfigService;


	// Constructors -----------------------------------------------------------

	public NoteManagerController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tripId) {
		ModelAndView result;
		Collection<Note> notes;

		notes = this.noteService.findByTripId(tripId);
		result = new ModelAndView("note/list");
		result.addObject("notes", notes);
		result.addObject("requestURI", "note/manager/list.do");

		return result;
	}

	// Reply ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView reply(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOneToReply(noteId);
		Assert.notNull(note);
		result = this.createEditModelAndView(note);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {
		ModelAndView result;
		final Manager m = this.managerService.findByPrincipal();
		final String[] reply = note.getReply().toLowerCase().split(" ");

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);

				for (final String s : this.systemConfigService.findConfig().getSpamWords())
					for (final String r : reply)
						if (r.contains(s)) {
							m.setSuspicious(true);
							this.managerService.save(m);
						}

				final int tripId = note.getTrip().getId();

				result = new ModelAndView("redirect:list.do?tripId=" + tripId);
			} catch (final ObjectOptimisticLockingFailureException e) {
				result = this.createEditModelAndView(note, "note.commit.test");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");
			}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String message) {
		ModelAndView result;

		final int tripId = note.getTrip().getId();

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("requestURI", "note/manager/edit.do");
		result.addObject("tripId", tripId);
		result.addObject("message", message);

		return result;
	}

}

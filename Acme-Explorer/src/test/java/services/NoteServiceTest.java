/*
 * NoteServiceTest.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Auditor;
import domain.Manager;
import domain.Note;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	//Service under test --------------------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private AuditorService	auditorService;

	@Autowired
	private TripService		tripService;


	//Tests------------------------------------------------------------------------

	@Test
	public void testCreate() {
		super.authenticate("auditor1");
		//		final Auditor principal = this.auditorService.findByPrincipal();
		//		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];

		final Note newNote = this.noteService.create();
		Assert.notNull(newNote.getCreationMoment());
		Assert.isNull(newNote.getRemark());
		Assert.isNull(newNote.getReply());
		Assert.isNull(newNote.getReplyMoment());

		super.authenticate(null);
	}
	@Test
	public void testSave() {
		super.authenticate("auditor1");
		final Auditor principal = this.auditorService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];

		final Note newNote = this.noteService.create();
		newNote.setRemark("Test remark");
		newNote.setTrip(trip);
		newNote.setAuditor(principal);
		final Note savedNote = this.noteService.save(newNote);

		final Collection<Note> notesAuditor = this.noteService.findByAuditorPrincipal();
		Assert.isTrue(notesAuditor.contains(savedNote));

		final Collection<Note> notesTrip = this.tripService.findOne(trip.getId()).getNotes();
		Assert.isTrue(notesTrip.contains(savedNote));

		super.authenticate(null);
	}

	@Test
	public void testReply() {
		super.authenticate("auditor1");
		final Auditor principal = this.auditorService.findByPrincipal();
		final Trip trip = (Trip) this.tripService.findAll().toArray()[0];
		final Manager manager = trip.getManager();

		final Note newNote = this.noteService.create();
		newNote.setRemark("Test remark");
		newNote.setTrip(trip);
		newNote.setAuditor(principal);
		final Note savedNote = this.noteService.save(newNote);
		super.authenticate(null);

		super.authenticate(manager.getUserAccount().getUsername());
		final Note replyNote = this.noteService.findOneToReply(savedNote.getId());
		replyNote.setRemark("Very good test!");
		super.authenticate(null);
	}

}

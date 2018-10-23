/*
 * NoteService.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Administrator;
import domain.Auditor;
import domain.Manager;
import domain.Note;
import domain.Trip;

@Service
@Transactional
public class NoteService {

	// Managed
	// repository------------------------------------------------------------
	@Autowired
	private NoteRepository			noteRepository;

	//Supporting services------------------------------------------------------

	@Autowired
	private AuditorService			auditorService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TripService				tripService;


	// Constructor -----------------------------------
	public NoteService() {
		super();
	}

	// CRUD methods ---------------------------------------------------
	public Note create() { // (final Auditor auditor, final Trip trip)
		// Assert.notNull(auditor);
		// Assert.notNull(trip);
		final Note result = new Note();
		final Auditor principal = this.auditorService.findByPrincipal();
		result.setAuditor(principal);
		// result.setTrip(trip);
		result.setCreationMoment(new Date(System.currentTimeMillis()));
		principal.getNotes().add(result);
		// trip.getNotes().add(result);
		return result;
	}

	public Collection<Note> findAll() {
		Collection<Note> result;

		result = this.noteRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Note findOne(final int noteId) {
		Note result;

		result = this.noteRepository.findOne(noteId);
		Assert.notNull(result);

		return result;
	}

	public Note save(final Note note) {
		Assert.notNull(note);
		this.checkPrincipal(note);

		if (note.getId() == 0)
			note.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		final Trip trip = note.getTrip();
		trip.getNotes().add(note);

		//If the note is being saved and it was saved before, it can only mean that a reply has been written
		if (note.getId() != 0)
			Assert.isTrue(note.getReply() != "" && note.getReply() != null);

		//Since a note can't be edited, if reply is not null, then this operation is saving a reply.
		if (note.getReply() != null)
			note.setReplyMoment(new Date(System.currentTimeMillis() - 1000));
		Note result = null;

		result = this.noteRepository.save(note);

		return result;
	}

	// Other business methods
	// ---------------------------------------------------------

	public Collection<Note> findByManagerPrincipal(final int idAuditor) {
		Collection<Note> notes = null;

		final Manager principal = this.managerService.findByPrincipal();
		notes = this.noteRepository.findByAuditorIdAndManagerId(idAuditor, principal.getId());

		return notes;
	}

	public Note findOneToReply(final int noteId) {
		Note result;
		result = this.findOne(noteId);
		Assert.notNull(result);
		Assert.isTrue(this.managerService.findByPrincipal() == result.getTrip().getManager());
		Assert.isNull(result.getReply());
		return result;
	}

	public Collection<Note> findByAuditorPrincipal() {
		Collection<Note> result;
		final Auditor principal = this.auditorService.findByPrincipal();
		result = this.noteRepository.findByAuditorId(principal.getId());
		return result;
	}

	public Collection<Note> findByTripId(final int tripId) {
		final Manager principal = this.managerService.findByPrincipal();
		final Trip trip = this.tripService.findOne(tripId);
		Assert.isTrue(principal.equals(trip.getManager()));
		Collection<Note> result;
		result = this.noteRepository.findByTripId(tripId);
		return result;
	}
	public double findAverage() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.noteRepository.findAverage();
		return result;
	}

	public double findMax() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.noteRepository.findMax();
		return result;
	}

	public double findMin() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.noteRepository.findMin();
		return result;
	}

	public double findStandardDeviation() {
		Administrator admin;

		admin = this.administratorService.findByPrincipal();
		Assert.notNull(admin);

		double result;
		result = this.noteRepository.findStandardDeviation();
		return result;
	}

	void checkPrincipal(final Note note) {
		Assert.notNull(note);
		//		final Auditor auditorPrincipal = this.auditorService.findByPrincipal();
		//		final Manager managerPrincipal = this.managerService.findByPrincipal();
		//		Assert.isTrue(this.auditorService.findByPrincipal().equals(note.getAuditor()) || this.managerService.findByPrincipal().equals(note.getTrip().getManager()));
		if (this.auditorService.findByPrincipal() != null)
			Assert.isTrue(this.auditorService.findByPrincipal().equals(note.getAuditor()));
		else
			Assert.isTrue(this.managerService.findByPrincipal().equals(note.getTrip().getManager()));

	}

}

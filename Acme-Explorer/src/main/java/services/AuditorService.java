
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AuditorRepository;
import security.LoginService;
import security.UserAccount;
import domain.Audition;
import domain.Auditor;
import domain.Note;

@Service
@Transactional
public class AuditorService {

	// Managed repository------------------------------------------------------------

	@Autowired
	private AuditorRepository	auditorRepository;


	// Supporting services-----------------------------------------

	//	@Autowired
	//	private AuditionService		auditionService;

	//	@Autowired
	//	private NoteService			noteService;

	// CRUD methods ---------------------------------------------------
	public Auditor create() {
		Auditor result;

		result = new Auditor();

		final List<Audition> auditions = new ArrayList<>();
		result.setAuditions(auditions);

		final List<Note> notes = new ArrayList<>();
		result.setNotes(notes);

		return result;
	}

	public Collection<Auditor> findAll() {
		Collection<Auditor> result;

		result = this.auditorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Auditor findOne(final int auditorId) {
		Auditor result;

		result = this.auditorRepository.findOne(auditorId);
		Assert.notNull(result);

		return result;
	}

	public Auditor save(final Auditor auditor) {
		Assert.notNull(auditor);

		Assert.isTrue(this.findByPrincipal().equals(auditor));
		Auditor result;
		result = this.auditorRepository.save(auditor);

		return result;
	}

	public void delete(final Auditor auditor) {
		Assert.notNull(auditor);
		Assert.isTrue(auditor.getId() != 0);

		auditor.getAuditions().clear();
		auditor.getNotes().clear();

		this.auditorRepository.delete(auditor);
	}

	// Other business methods---------------------------------------------------------
	public Auditor findByPrincipal() {
		Auditor result;
		final UserAccount userAccount = LoginService.getPrincipal();
		//		Assert.notNull(userAccount);
		result = this.findByUserAccount(userAccount);
		//		Assert.notNull(result);

		return result;
	}

	public Auditor findByUserAccount(final UserAccount userAccount) {
		//		Assert.notNull(userAccount);

		Auditor result = null;

		if (userAccount != null)
			result = this.auditorRepository.findByUserAccountId(userAccount.getId());
		//		Assert.notNull(result);

		return result;
	}

	public Auditor findByUserAccountId(final int auditorId) {
		Auditor result;
		result = this.auditorRepository.findByUserAccountId(auditorId);
		return result;
	}

}

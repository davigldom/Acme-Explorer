
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.EndorserRecord;

@Service
@Transactional
public class EndorserRecordService {

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	@Autowired
	private RangerService				rangerService;


	// Simple CRUD methods

	public EndorserRecord create() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		EndorserRecord result;
		result = new EndorserRecord();
		return result;
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		Assert.isTrue(endorserRecordId != 0);
		EndorserRecord result;
		result = this.endorserRecordRepository.findOne(endorserRecordId);
		return result;
	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;
		result = this.endorserRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord, final Curriculum curriculum) {
		Assert.notNull(endorserRecord);
		EndorserRecord result;
		result = this.endorserRecordRepository.save(endorserRecord);
		if (endorserRecord.getId() == 0)
			curriculum.getEndorserRecords().add(result);
		return result;
	}

	public void delete(final EndorserRecord endorserRecord, final Curriculum curriculum) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);
		curriculum.getEndorserRecords().remove(endorserRecord);
		this.endorserRecordRepository.delete(endorserRecord);
	}

	public EndorserRecord findOneToEdit(final int endorserRecordId) {
		Assert.isTrue(endorserRecordId != 0);
		EndorserRecord result;
		result = this.endorserRecordRepository.findOne(endorserRecordId);
		final Curriculum curriculumPrincipal = this.rangerService.findByPrincipal().getCurriculum();
		if (curriculumPrincipal != null)
			Assert.isTrue(curriculumPrincipal.getEndorserRecords().contains(result));
		else
			Assert.isTrue(false);
		return result;
	}
}

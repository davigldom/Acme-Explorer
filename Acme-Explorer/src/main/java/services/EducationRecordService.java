
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import security.LoginService;
import security.UserAccount;
import domain.Curriculum;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	@Autowired
	private RangerService rangerService;

	// Simple CRUD methods

	public EducationRecord create() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		EducationRecord result;
		result = new EducationRecord();
		return result;
	}

	public EducationRecord findOne(final int educationRecordId) {
		Assert.isTrue(educationRecordId != 0);
		EducationRecord result;
		result = this.educationRecordRepository.findOne(educationRecordId);
		return result;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;
		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord, Curriculum curriculum) {
		Assert.notNull(educationRecord);
		EducationRecord result;
		result = this.educationRecordRepository.save(educationRecord);
		if(educationRecord.getId()==0)curriculum.getEducationRecords().add(result);
		return result;
	}

	public void delete(final EducationRecord educationRecord, Curriculum curriculum) {
		Assert.notNull(educationRecord);
		Assert.notNull(this.educationRecordRepository.findOne(educationRecord.getId()));
		curriculum.getEducationRecords().remove(educationRecord);
		this.educationRecordRepository.delete(educationRecord);
	}
	
	public EducationRecord findOneToEdit(final int educationRecordId) {
		Assert.isTrue(educationRecordId != 0);
		EducationRecord result;
		result = this.educationRecordRepository.findOne(educationRecordId);
		Curriculum curriculumPrincipal = this.rangerService.findByPrincipal()
				.getCurriculum();
		if(curriculumPrincipal!=null)Assert.isTrue(curriculumPrincipal.getEducationRecords().contains(result));
		else Assert.isTrue(false);
		return result;
	}
}

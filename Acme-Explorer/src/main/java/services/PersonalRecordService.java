package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Curriculum;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	// Managed repository

	@Autowired
	private PersonalRecordRepository personalRecordRepository;

	// Supporting services
	@Autowired
	private RangerService rangerService;

	// Constructors

	public PersonalRecordService() {
		super();
	}

	// Simple CRUD methods

	public PersonalRecord create() {
		PersonalRecord result = null;
		result = new PersonalRecord();
		return result;
	}

	public PersonalRecord findOne(final int personalRecordId) {
		PersonalRecord result = null;
		result = this.personalRecordRepository.findOne(personalRecordId);
		return result;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> result = null;
		result = this.personalRecordRepository.findAll();
		return result;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);
		PersonalRecord result = null;
		result = this.personalRecordRepository.save(personalRecord);
		return result;
	}
	
	public PersonalRecord findOneToEdit(final int personalRecordId) {
		PersonalRecord result = null;
		result = this.personalRecordRepository.findOne(personalRecordId);
		Curriculum curriculumPrincipal = this.rangerService.findByPrincipal()
				.getCurriculum();
		if(curriculumPrincipal!=null)Assert.isTrue(curriculumPrincipal.getPersonalRecord().equals(result));
		else Assert.isTrue(false);
		return result;
	}

}

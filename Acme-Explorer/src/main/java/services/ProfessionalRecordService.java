package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Managed repository

	@Autowired
	private ProfessionalRecordRepository professionalRecordRepository;

	// Supporting services
	@Autowired
	private RangerService rangerService;

	// Constructors

	public ProfessionalRecordService() {
		super();
	}

	// Simple CRUD methods

	public ProfessionalRecord create() {
		ProfessionalRecord result = null;
		result = new ProfessionalRecord();
		return result;
	}

	public ProfessionalRecord findOne(final int professionalRecordId) {
		ProfessionalRecord result = null;
		result = this.professionalRecordRepository
				.findOne(professionalRecordId);
		return result;
	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result = null;
		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord,
			Curriculum curriculum) {
		Assert.notNull(professionalRecord);
		ProfessionalRecord result = null;
		result = this.professionalRecordRepository.save(professionalRecord);
		if (professionalRecord.getId() == 0)
			curriculum.getProfessionalRecords().add(result);
		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord,
			Curriculum curriculum) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);
		curriculum.getProfessionalRecords().remove(professionalRecord);
		this.professionalRecordRepository.delete(professionalRecord);
	}

	public ProfessionalRecord findOneToEdit(final int professionalRecordId) {
		ProfessionalRecord result = null;
		result = this.professionalRecordRepository.findOne(professionalRecordId);
		Curriculum curriculumPrincipal= this.rangerService.findByPrincipal().getCurriculum();
		if(curriculumPrincipal!=null)Assert.isTrue(curriculumPrincipal.getProfessionalRecords().contains(result));
		else Assert.isTrue(false);
		return result;
	}
}

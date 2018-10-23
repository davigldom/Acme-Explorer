
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;


	// Supporting services
	@Autowired
	private RangerService rangerService;

	// Constructors

	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods

	public MiscellaneousRecord create() {
		MiscellaneousRecord result = null;
		result = new MiscellaneousRecord();
		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result = null;
		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		return result;
	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result = null;
		result = this.miscellaneousRecordRepository.findAll();
		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord, Curriculum curriculum) {
		Assert.notNull(miscellaneousRecord);
		MiscellaneousRecord result = null;
		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		if(miscellaneousRecord.getId()==0)curriculum.getMiscellaneousRecords().add(result);
		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord, Curriculum curriculum) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId()!=0);
		curriculum.getMiscellaneousRecords().remove(miscellaneousRecord);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}
	
	public MiscellaneousRecord findOneToEdit(final int miscellaneousRecordId) {
		MiscellaneousRecord result = null;
		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
		Curriculum curriculumPrincipal = this.rangerService.findByPrincipal()
				.getCurriculum();
		if(curriculumPrincipal!=null)Assert.isTrue(curriculumPrincipal.getMiscellaneousRecords().contains(result));
		else Assert.isTrue(false);
		return result;
	}
}

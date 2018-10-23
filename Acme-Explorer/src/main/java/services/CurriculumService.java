
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ranger;

@Service
@Transactional
public class CurriculumService {

	// Managed repository------------------------------------------------------------
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services-----------------------------------------

	@Autowired
	private RangerService			rangerService;


	// CRUD methods ---------------------------------------------------
	public Curriculum create(final PersonalRecord personalRecord) {
		Curriculum result;
		result = new Curriculum();

		final Ranger ranger = this.rangerService.findByPrincipal();
		result.setRanger(ranger);
		result.setPersonalRecord(personalRecord);
		result.setTicker(this.generateTicker());
		result.setEducationRecords(new ArrayList<EducationRecord>());
		result.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		result.setEndorserRecords(new ArrayList<EndorserRecord>());
		result.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());
		

		
		//		this.rangerService.save(ranger);

		return result;
	}
	public Collection<Curriculum> findAll() {
		Collection<Curriculum> result;

		result = this.curriculumRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum result;

		result = this.curriculumRepository.findOne(curriculumId);
		Assert.notNull(result);

		return result;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		this.checkPrincipal(curriculum);

		//		Ranger ranger;
		//		ranger = this.rangerService.findByPrincipal();
		//		ranger.setCurriculum(curriculum);

		//		curriculum.setRanger(ranger);

		Curriculum result;
		result = this.curriculumRepository.save(curriculum);

		Ranger ranger;
		ranger = result.getRanger();
		ranger.setCurriculum(result);
//
//		this.rangerService.save(ranger);

		return result;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Assert.isTrue(curriculum.getId() != 0);
		this.checkPrincipal(curriculum);

		curriculum.getRanger().setCurriculum(null);

		this.curriculumRepository.delete(curriculum);
	}

	// Other business methods----------------------------------

	public Curriculum findByRangerId(final int rangerId) {
		Curriculum result;
		result = this.curriculumRepository.findByRangerId(rangerId);
		return result;
	}

	public Curriculum findByPersonalRecordId(final int personalRecordId) {
		Curriculum result;
		result = this.curriculumRepository.findByPersonalRecordId(personalRecordId);
		return result;
	}
	
	private void checkPrincipal(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		final Ranger principal = this.rangerService.findByPrincipal();
		Assert.isTrue(principal == curriculum.getRanger());
	}

	public Curriculum findOneToEdit(final int curriculumId) {
		Curriculum result;
		result = this.findOne(curriculumId);
		Assert.notNull(result);
		this.checkPrincipal(result);
		return result;
	}

	String generateTicker() {
		String ticker = "";

		final Calendar c = Calendar.getInstance();
		String day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
		if (day.length() == 1)
			day = "0" + day;

		String month = Integer.toString(c.get(Calendar.MONTH)+1);
		if (month.length() == 1)
			month = "0" + month;

		String year = Integer.toString(c.get(Calendar.YEAR));
		year = year.substring(2);

		ticker = year + month + day;

		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final Random random = new Random();
		final int index1 = random.nextInt(alphabet.length());
		final int index2 = random.nextInt(alphabet.length());
		final int index3 = random.nextInt(alphabet.length());
		final int index4 = random.nextInt(alphabet.length());
		ticker = ticker + "-" + alphabet.charAt(index1) + alphabet.charAt(index2) + alphabet.charAt(index3) + alphabet.charAt(index4);

		Collection<String> tickers = this.curriculumRepository.findTickersLike(ticker);
		if(!tickers.isEmpty()){
			ticker = this.generateTicker();
		}
		
		return ticker;

	}

}

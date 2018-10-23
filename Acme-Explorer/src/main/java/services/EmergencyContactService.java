
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EmergencyContactRepository;
import domain.EmergencyContact;

@Service
@Transactional
public class EmergencyContactService {

	@Autowired
	private EmergencyContactRepository	emergencyContactRepository;


	//Suportting services

	@Autowired
	private ExplorerService explorerService;
	
	// Simple CRUD methods

	public EmergencyContact create() {
		EmergencyContact result;
		result = new EmergencyContact();
		return result;
	}

	public EmergencyContact findOne(final int emergencyContactId) {
		Assert.isTrue(emergencyContactId != 0);
		EmergencyContact result;
		result = this.emergencyContactRepository.findOne(emergencyContactId);
		return result;
	}

	public Collection<EmergencyContact> findAll() {
		Collection<EmergencyContact> result;
		result = this.emergencyContactRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public EmergencyContact save(final EmergencyContact emergencyContact) {
		Assert.notNull(emergencyContact);
		if(emergencyContact.getId()!=0)Assert.isTrue(this.explorerService.findByPrincipal().getEmergencyContacts().contains(emergencyContact));
		
		if(emergencyContact.getPhone().isEmpty())Assert.isTrue(!emergencyContact.getEmail().isEmpty());
		if(emergencyContact.getEmail().isEmpty())Assert.isTrue(!emergencyContact.getPhone().isEmpty());
		
		EmergencyContact result;
		result = this.emergencyContactRepository.save(emergencyContact);
		if(emergencyContact.getId()==0)this.explorerService.findByPrincipal().getEmergencyContacts().add(result);
		return result;
	}

	public void delete(final EmergencyContact emergencyContact) {
		Assert.notNull(emergencyContact);
		Assert.isTrue(this.explorerService.findByPrincipal().getEmergencyContacts().contains(emergencyContact));
		this.explorerService.findByPrincipal().getEmergencyContacts().remove(emergencyContact);
		this.emergencyContactRepository.delete(emergencyContact);
	}
	
	
	//OTHER METHODS
	
	public Collection<EmergencyContact> findByExplorerId(int explorerId){
		Collection<EmergencyContact> result;
		
		result = this.emergencyContactRepository.findByExplorerId(explorerId);
		return result;
	}

}

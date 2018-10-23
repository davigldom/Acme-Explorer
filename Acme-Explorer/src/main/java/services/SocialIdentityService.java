package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialIdentityRepository;
import domain.Actor;
import domain.SocialIdentity;
@Service
@Transactional
public class SocialIdentityService {
	// Managed repository
	
	@Autowired
	private SocialIdentityRepository socialIdentityRepository;
	
	// Supporting Services
	
	@Autowired
	private ActorService actorService;
	
	// Constructors
	
	public SocialIdentityService() {
		super();
	}

	// Simple CRUD methods
	
		public SocialIdentity create() {
			SocialIdentity result;
			result = new SocialIdentity();
			return result;
		}

		public SocialIdentity findOne(final int socialIdentityId) {
			Assert.isTrue(socialIdentityId!=0);
			Assert.isTrue(this.socialIdentityRepository.exists(socialIdentityId));
			SocialIdentity result;
			result = this.socialIdentityRepository.findOne(socialIdentityId);
			return result;
		}

		public Collection<SocialIdentity> findAll() {
			Collection<SocialIdentity> result;
			result = this.socialIdentityRepository.findAll();
			Assert.notNull(result); 
			return result;
		}

		public SocialIdentity save(final SocialIdentity socialIdentity) {
			Assert.notNull(socialIdentity);
			if(socialIdentity.getId()!=0)Assert.isTrue(this.actorService.findByPrincipal().getSocialIdentities().contains(socialIdentity));
			SocialIdentity result;
			result = this.socialIdentityRepository.save(socialIdentity);
			if(socialIdentity.getId()==0)this.actorService.findByPrincipal().getSocialIdentities().add(result);
			return result;
		}

		public void delete(final SocialIdentity socialIdentity) {
			Assert.notNull(socialIdentity);
			Assert.isTrue(this.actorService.findByPrincipal().getSocialIdentities().contains(socialIdentity));
			Assert.isTrue(this.socialIdentityRepository.exists(socialIdentity.getId()));
			this.actorService.findByPrincipal().getSocialIdentities().remove(socialIdentity);
			this.socialIdentityRepository.delete(socialIdentity);
		}
		
		//Other methods
		
		public Collection<SocialIdentity> findByPrincipal(){
			Collection<SocialIdentity> result;
			final Actor principal = this.actorService.findByPrincipal();
			result = this.findByActorId(principal.getId());
			return result;
		}
		
		public Collection<SocialIdentity> findByActorId(int actorId){
			Collection<SocialIdentity> result;
			result = this.socialIdentityRepository.findByActorId(actorId);
			return result;
		}
}

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.SocialIdentity;

import utilities.AbstractTest;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})

@Transactional
public class SocialIdentityServiceTest extends AbstractTest{

	@Autowired
	public SocialIdentityService socialIdentityService;
	
	@Test
	public void createTest(){
		super.authenticate("administrator");
		
		SocialIdentity socialIdentity = this.socialIdentityService.create();
		Assert.isNull(socialIdentity.getLink());
		Assert.isNull(socialIdentity.getNameOfSocialNetwork());
		Assert.isNull(socialIdentity.getPhoto());
		Assert.isNull(socialIdentity.getNick());
		
		super.unauthenticate();
	}
	

	
	@Test
	public void testSave(){
		super.authenticate("administrator");
		
		final SocialIdentity socialIdentity = this.socialIdentityService.create();
		socialIdentity.setLink("http://link.com/");
		socialIdentity.setNameOfSocialNetwork("Twitter");
		socialIdentity.setNick("Nuevonick");
		socialIdentity.setPhoto("http://flicker.com/1/");
		final SocialIdentity saved = this.socialIdentityService.save(socialIdentity);
		Assert.isTrue(this.socialIdentityService.findAll().contains(saved));
		
		super.unauthenticate();
	}
	
	@Test
	public void testDelete(){
		super.authenticate("administrator");
		
		final SocialIdentity socialIdentity = this.socialIdentityService.create();
		socialIdentity.setLink("http://link.com/");
		socialIdentity.setNameOfSocialNetwork("Twitter");
		socialIdentity.setNick("Nuevonick");
		socialIdentity.setPhoto("http://flicker.com/1/");
		final SocialIdentity saved = this.socialIdentityService.save(socialIdentity);
		this.socialIdentityService.delete(saved);
		Assert.isTrue(!this.socialIdentityService.findAll().contains(saved));
		
		super.unauthenticate();
		
	}
}

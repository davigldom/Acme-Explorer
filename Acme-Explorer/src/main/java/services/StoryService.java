
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.StoryRepository;
import domain.Explorer;
import domain.Story;
import domain.Trip;

@Service
@Transactional
public class StoryService {

	@Autowired
	private StoryRepository	storyRepository;

	@Autowired
	private ExplorerService	explorerService;
	@Autowired
	private TripService		tripService;


	//CRUD METHODS
	public Story create(final Trip trip) {
		Assert.notNull(trip);
		this.tripService.checkTripIsOver(trip);

		final Story result = new Story();
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();

		//Assert.isTrue(this.tripService.findAcceptedApplicationsOfExplorer(explorer.getId()).contains(trip));

		result.setExplorer(explorer);
		result.setTrip(trip);

		explorer.getStories().add(result);

		return result;
	}

	public Collection<Story> findAll() {
		return this.storyRepository.findAll();
	}

	public Story findOne(final int id) {
		return this.storyRepository.findOne(id);
	}

	public Story save(final Story story) {
		Assert.notNull(story);
		this.tripService.checkTripIsOver(story.getTrip());

		Explorer explorer;
		explorer = this.explorerService.findByPrincipal();

		Assert.isTrue(this.tripService.findAcceptedApplicationsOfExplorer(explorer.getId()).contains(story.getTrip()));

		Assert.isTrue(story.getExplorer().equals(explorer));

		final Story saved = this.storyRepository.save(story);

		explorer.getStories().add(saved);

		this.explorerService.save(explorer);

		return saved;

	}

	public void delete(final Story story) {
		Assert.notNull(story);
		Assert.isTrue(story.getId() != 0);

		Explorer explorer;
		explorer = this.explorerService.findByPrincipal();

		Assert.isTrue(story.getExplorer().equals(explorer));

		this.storyRepository.delete(story);

		explorer.getStories().remove(story);

		this.explorerService.save(explorer);

	}

	//OTHER METHODS

	public Collection<Story> findByPrincipal() {
		Collection<Story> result;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		result = explorer.getStories();

		return result;
	}
}

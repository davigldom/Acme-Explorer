
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.StageRepository;
import domain.Stage;
import domain.Trip;

@Service
@Transactional
public class StageService {

	@Autowired
	private StageRepository		stageRepository;

	// Supporting Services
	@Autowired
	private ManagerService		managerService;
	@Autowired
	private TripService			tripService;
	@Autowired
	private SystemConfigService	sysconfigService;


	//private TripService tripService;

	// Constructors

	public StageService() {
		super();
	}

	// Simple CRUD methods

	public Stage create() {
		Stage result;
		result = new Stage();
		return result;
	}

	public Stage findOne(final int stageId) {
		Assert.isTrue(stageId != 0);
		Stage result;
		result = this.stageRepository.findOne(stageId);
		return result;
	}

	public Collection<Stage> findAll() {
		Collection<Stage> result;
		result = this.stageRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Stage save(final Stage stage, final Trip trip) {
		Assert.notNull(stage);
		Assert.isTrue(!trip.isPublished());
		Assert.isTrue(stage.getPrice() > 0.0);
		final double priceVAT = stage.getPrice() * ((this.sysconfigService.findConfig().getVAT()) / 100 + 1);
		stage.setPrice(priceVAT);
		Stage result;
		result = this.stageRepository.save(stage);
		if (trip.getStages().contains(stage))
			trip.setPrice(trip.getPrice() - stage.getPrice() + result.getPrice());

		else {
			trip.getStages().add(result);
			trip.setPrice(trip.getPrice() + result.getPrice());
		}

		//We save the trip to update its price
		this.tripService.save(trip);
		return result;
	}
	public void delete(final Stage stage, final Trip trip) {
		Assert.notNull(stage);
		Assert.isTrue(trip.getManager().equals(this.managerService.findByPrincipal()));
		Assert.isTrue(stage.getId() != 0);
		Assert.isTrue(!trip.isPublished());
		Assert.isTrue(this.stageRepository.exists(stage.getId()));
		trip.setPrice(trip.getPrice() - stage.getPrice());
		trip.getStages().remove(stage);
		//We save the trip to update its price
		this.tripService.save(trip);
		this.stageRepository.delete(stage);
	}

	//Other methods-----------------------------------------------------------

	public Collection<Stage> findByTrip(final int tripId) {
		Collection<Stage> result;

		result = this.stageRepository.findByTrip(tripId);

		return result;
	}

	public Stage findOneToEdit(final int stageId, final int tripId) {
		Assert.isTrue(stageId != 0);
		Stage result;
		result = this.stageRepository.findOne(stageId);
		Assert.isTrue(this.tripService.findOne(tripId).getManager().equals(this.managerService.findByPrincipal()));
		return result;
	}
}

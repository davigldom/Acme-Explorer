
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SurvivalClassRepository;
import domain.Explorer;
import domain.Manager;
import domain.SurvivalClass;
import domain.Trip;

@Service
@Transactional
public class SurvivalClassService {

	@Autowired
	private SurvivalClassRepository	survivalClassRepository;

	@Autowired
	private ManagerService			managerService;
	@Autowired
	private ExplorerService			explorerService;


	//CRUD METHODS

	public SurvivalClass create() { // (final Trip trip)
		// Assert.notNull(trip);

		SurvivalClass result;
		result = new SurvivalClass();

		Manager manager;
		manager = this.managerService.findByPrincipal();

		// Assert.isTrue(trip.getManager().equals(manager));

		result.setManager(manager);
		// result.setTrip(trip);

		//		manager.getSurvivalClasses().add(result);
		// trip.getSurvivalClasses().add(result);

		return result;
	}

	public Collection<SurvivalClass> findAll() {
		return this.survivalClassRepository.findAll();
	}

	public SurvivalClass findOne(final int id) {
		return this.survivalClassRepository.findOne(id);
	}

	public SurvivalClass save(final SurvivalClass s) {
		Assert.notNull(s);
		final Trip t = s.getTrip();

		Manager manager;
		manager = this.managerService.findByPrincipal();

		Assert.isTrue(s.getManager().equals(manager));
		Assert.isTrue(s.getTrip().getManager().equals(manager));
		Assert.isTrue(s.getMoment().before(s.getTrip().getEndDate()) && s.getMoment().after(s.getTrip().getStartDate()));
		Assert.isTrue(s.getTrip().getStartDate().after(new Date(System.currentTimeMillis())));

		final SurvivalClass savedSc = this.survivalClassRepository.save(s);

		manager.getSurvivalClasses().add(savedSc);
		t.getSurvivalClasses().add(savedSc);

		//		this.managerService.save(manager);
		//		this.tripService.save(t);

		return savedSc;
	}

	public void delete(final SurvivalClass s) {
		Assert.notNull(s);
		Assert.isTrue(s.getId() != 0);
		final Trip t = s.getTrip();

		Manager manager;
		manager = this.managerService.findByPrincipal();

		Assert.isTrue(s.getManager().equals(manager));

		this.survivalClassRepository.delete(s);

		manager.getSurvivalClasses().remove(s);
		t.getSurvivalClasses().remove(s);

		//		this.managerService.save(manager);
		//		this.tripService.save(t);
	}

	//OTHER METHODS

	public Collection<SurvivalClass> findByPrincipal() {
		Collection<SurvivalClass> result;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		result = manager.getSurvivalClasses();

		return result;
	}

	public Collection<SurvivalClass> findAvailableToEnrol() {
		Collection<SurvivalClass> result;
		Explorer explorer;

		explorer = this.explorerService.findByPrincipal();
		result = this.survivalClassRepository.findAvailableToEnrol(explorer.getId());

		return result;
	}

	public SurvivalClass findOneToEdit(final int id) {
		final SurvivalClass sc;
		Manager manager;

		manager = this.managerService.findByPrincipal();
		sc = this.findOne(id);

		Assert.notNull(sc);
		Assert.isTrue(sc.getManager().equals(manager));

		return sc;
	}

	public void enrol(final int survivalClassId) {
		Assert.notNull(survivalClassId);

		final Explorer principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		final SurvivalClass sc = this.findOne(survivalClassId);
		Assert.notNull(sc);
		principal.getSurvivalClasses().add(sc);

		this.explorerService.save(principal);

	}

	public void unenrol(final int survivalClassId) {
		Assert.notNull(survivalClassId);

		final Explorer principal = this.explorerService.findByPrincipal();
		Assert.notNull(principal);

		principal.getSurvivalClasses().remove(this.findOne(survivalClassId));

		this.explorerService.save(principal);
	}

	public Collection<SurvivalClass> findEnrolledClasses() {
		Collection<SurvivalClass> result;
		Explorer principal;

		principal = this.explorerService.findByPrincipal();
		result = principal.getSurvivalClasses();
		Assert.notNull(result);

		return result;

	}
}

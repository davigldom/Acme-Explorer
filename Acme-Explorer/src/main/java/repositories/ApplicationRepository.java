
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	//C-Level--------------------------------------------------------------------

	//Requirement 12.2
	@Query("select a from Application a where a.trip.manager.id=?1")
	Collection<Application> findByManagerId(int managerId);

	//Requirement 13.2
	@Query("select a from Application a where a.explorer.id=?1 order by a.status")
	Collection<Application> findByExplorerId(int explorerId);

	//Requirement 14.6
	//The average, the minimum, the maximum, and the standard deviation of the number of applications per trip.
	@Query("select avg(t.applications.size) from Trip t")
	Double averageApplicationsPerTrip();

	@Query("select min(t.applications.size) from Trip t")
	Double minimumApplicationsPerTrip();

	@Query("select max(t.applications.size) from Trip t")
	Double maximumApplicationsPerTrip();

	@Query("select sqrt(sum(t.applications.size*t.applications.size)/count(t.applications.size)-(avg(t.applications.size)*avg(t.applications.size))) from Trip t")
	Double standardDerivationApplicationsPerTrip();

	//The ratio of applications with status REJECTED.
	@Query("select sum(case when (a.status=1) then 1.0 else 0.0 end)/count(a) FROM Application a")
	Double ratioApplicationsRejected();
		
	//The ratio of applications with status PENDING.
	@Query("select sum(case when (a.status=0) then 1.0 else 0.0 end)/count(a) FROM Application a")
	Double ratioApplicationsPending();

	//The ratio of applications with status DUE.
	@Query("select sum(case when (a.status=2) then 1.0 else 0.0 end)/count(a) FROM Application a")
	Double ratioApplicationsDue();

	//The ratio of applications with status ACCEPTED.
	@Query("select sum(case when (a.status=3) then 1.0 else 0.0 end)/count(a) FROM Application a")
	Double ratioApplicationsAccepted();

	//The ratio of applications with status CANCELLED.
	@Query("select sum(case when (a.status=4) then 1.0 else 0.0 end)/count(a) FROM Application a")
	Double ratioApplicationsCancelled();

}

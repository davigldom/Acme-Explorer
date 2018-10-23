
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//B LEVEL

	//Manage his or her finder, which includes modifying it and consulting its results, that is, the trips that meet the search criteria.
	@Query("select e.finder from Explorer e where e.id=?1")
	Finder getFinderByExplorerId(int explorerId);

	@Query("select f from Finder f where f.id=?1")
	Finder findOneToEdit(int finderId);
	
	@Query("select f from Finder f join f.trips t where t.id=?1")
	Collection<Finder> findByTrip(int tripId);
}

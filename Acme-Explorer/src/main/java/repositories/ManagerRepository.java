
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccountId(int id);

	//B-Level--------------------------------------------------------------------

	// An actor who is authenticated as an administrator must be able to:
	// List suspicious managers
	@Query("select m from Manager m where m.suspicious = true")
	Collection<Manager> findAllManagersSuspicious();

	//  Display a dashboard with the following information:
	// The ratio of suspicious managers
	@Query("select sum(case when (m.suspicious=1) then 1.0 else 0.0 end)/count(m) from Manager m")
	Double ratioManagersSuspicious();

	@Query("select m.userAccount.username from Manager m where m.banned = true")
	Collection<String> findAllManagersBanned();
}

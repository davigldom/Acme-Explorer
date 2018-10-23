
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EmergencyContact;

@Repository
public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Integer> {

	@Query("select e.emergencyContacts from Explorer e where e.id=?1")
	Collection<EmergencyContact> findByExplorerId(int explorerId);
}

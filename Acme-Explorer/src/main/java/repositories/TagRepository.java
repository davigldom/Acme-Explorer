
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

	@Query("select t from Tag t where t.tagValues is empty")
	Collection<Tag> findNotUsed();
	
	@Query("select t from Tag t where t.name =?1")
	Tag findTagName(String name);
}

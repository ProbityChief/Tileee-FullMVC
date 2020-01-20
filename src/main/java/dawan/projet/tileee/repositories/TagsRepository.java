package dawan.projet.tileee.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import dawan.projet.tileee.bean.Tag;

public interface TagsRepository extends CrudRepository<Tag, Integer> {
	Tag findByTagName(String tagName);
	
	@Query("FROM Tag t JOIN FETCH t.cards WHERE t.rand=?1")
	Tag findByRand(String rand);
}

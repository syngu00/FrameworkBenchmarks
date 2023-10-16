package hello.repository;

import hello.document.FortuneDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FortuneRepository extends MongoRepository<FortuneDocument, Integer> {
}

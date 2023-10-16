package hello.repository;

import hello.document.WorldDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorldRepository extends MongoRepository<WorldDocument, Integer> {
}

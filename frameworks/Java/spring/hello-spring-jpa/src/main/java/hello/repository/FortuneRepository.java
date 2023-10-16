package hello.repository;

import hello.entity.FortuneEntity;
import hello.model.Fortune;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FortuneRepository extends JpaRepository<FortuneEntity, Integer> {
}

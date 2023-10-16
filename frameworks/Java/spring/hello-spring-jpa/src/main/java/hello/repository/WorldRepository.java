package hello.repository;

import hello.entity.WorldEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface WorldRepository extends JpaRepository<WorldEntity, Integer> {

    @Modifying
    @Query("update WorldEntity set randomNumber = :newNumber where id = :id")
    @Transactional
    void setNewValue(int id, int newNumber);
}

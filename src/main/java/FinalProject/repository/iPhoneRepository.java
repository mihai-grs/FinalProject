package FinalProject.repository;

import FinalProject.model.iPhone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface iPhoneRepository extends JpaRepository<iPhone, Long> {
    Page<iPhone> findAll(Pageable pageable);
    Optional<iPhone> findByModelName(String modelName);
}

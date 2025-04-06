package gdghufs.jandies.repository;

import gdghufs.jandies.entity.Farm;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public interface FarmRepository extends JpaRepository<Farm, Long> {
    Optional<Farm> findByName(String name);
}
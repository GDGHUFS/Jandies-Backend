package gdghufs.jandies.repository;

import gdghufs.jandies.entity.Farm;
import gdghufs.jandies.entity.Fortune;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Transactional
public interface FortuneRepository extends JpaRepository<Fortune, Long> {
    @Query(value = "SELECT * FROM fortune ORDER BY id LIMIT 1 OFFSET :offset", nativeQuery = true)
    Fortune findTodayFortune(@Param("offset") int offset);
}
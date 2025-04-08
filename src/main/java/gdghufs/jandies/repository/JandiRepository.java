package gdghufs.jandies.repository;

import gdghufs.jandies.entity.Auth;
import gdghufs.jandies.entity.Jandi;
import gdghufs.jandies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JandiRepository extends JpaRepository<Jandi, Long> {

    List<Jandi> findAllById_UserId(Long idUserId);

    List<Jandi> findAllById_UserIdOrderById_date(Long idUserId);

    List<Jandi> findAllById_UserIdOrderById_dateDesc(Long idUserId);
}
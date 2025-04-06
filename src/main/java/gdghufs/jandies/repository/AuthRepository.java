package gdghufs.jandies.repository;

import gdghufs.jandies.entity.Auth;
import gdghufs.jandies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    boolean existsByUser(User user);

    Optional<Auth> findByRefreshToken(String refreshToken);

    Optional<Auth> findByUser(User user);

}
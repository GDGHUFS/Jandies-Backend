package gdghufs.jandies.repository;

import gdghufs.jandies.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findBygithubId(Long githubId);

    Optional<User> findByid(Long id);
}
package gdghufs.jandies.repository;

import gdghufs.jandies.entity.LinkTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LinkTreeRepository extends JpaRepository<LinkTree, Long> {

    Optional<LinkTree> findByid(Long id);


}
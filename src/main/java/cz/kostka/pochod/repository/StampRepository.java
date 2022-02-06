package cz.kostka.pochod.repository;

import cz.kostka.pochod.domain.Stamp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by dkostka on 2/6/2022.
 */
@Repository
public interface StampRepository extends JpaRepository<Stamp, Long> {
}

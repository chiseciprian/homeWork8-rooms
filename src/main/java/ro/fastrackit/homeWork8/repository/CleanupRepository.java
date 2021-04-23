package ro.fastrackit.homeWork8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fastrackit.homeWork8.model.entity.Cleanup;

import java.util.List;

@Repository
public interface CleanupRepository extends JpaRepository<Cleanup, Long> {
    List<Cleanup> findCleanupsByRoomId(long roomId);
}

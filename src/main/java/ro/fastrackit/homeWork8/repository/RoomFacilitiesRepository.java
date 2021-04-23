package ro.fastrackit.homeWork8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.fastrackit.homeWork8.model.entity.RoomFacilities;

@Repository
public interface RoomFacilitiesRepository extends JpaRepository<RoomFacilities, Long> {
}

package ro.fastrackit.homeWork8;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ro.fastrackit.homeWork8.model.entity.Cleanup;
import ro.fastrackit.homeWork8.model.entity.Room;
import ro.fastrackit.homeWork8.model.entity.RoomFacilities;
import ro.fastrackit.homeWork8.repository.CleanupRepository;
import ro.fastrackit.homeWork8.repository.RoomFacilitiesRepository;
import ro.fastrackit.homeWork8.repository.RoomRepository;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RoomRepository roomRepository;
    private final CleanupRepository cleanupRepository;
    private final RoomFacilitiesRepository roomFacilitiesRepository;

    @Override
    public void run(String... args) {
        List<Room> rooms = roomRepository.saveAll(List.of(
                Room.builder()
                        .number("32A")
                        .floor(1)
                        .hotelName("SunWing")
                        .build(),
                Room.builder()
                        .number("32B")
                        .floor(1)
                        .hotelName("SunWing")
                        .build()
        ));

        cleanupRepository.saveAll(List.of(
                Cleanup.builder()
                        .room(rooms.get(0))
                        .date(LocalDate.now())
                        .build()
        ));

        roomFacilitiesRepository.saveAll(List.of(
                RoomFacilities.builder()
                        .room(rooms.get(0))
                        .hasTv(true)
                        .hasDoubleBed(true)
                        .build(),
                RoomFacilities.builder()
                        .room(rooms.get(1))
                        .hasTv(false)
                        .hasDoubleBed(false)
                        .build()
        ));
    }
}

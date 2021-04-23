package ro.fastrackit.homeWork8.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ro.fastrackit.homeWork8.controller.exception.EntityNotFoundException;
import ro.fastrackit.homeWork8.model.RoomsFilters;
import ro.fastrackit.homeWork8.model.entity.Cleanup;
import ro.fastrackit.homeWork8.model.entity.Room;
import ro.fastrackit.homeWork8.service.CleanupService;
import ro.fastrackit.homeWork8.service.RoomService;

import java.util.List;

@RestController
@RequestMapping("rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final CleanupService cleanupService;

    @GetMapping
    List<Room> getAll(RoomsFilters filters) {
        return roomService.getAll(filters);
    }

    @GetMapping(path = "{roomId}")
    Room getRoomById(@PathVariable long roomId) {
        return roomService.getRoomById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + roomId + " not found"));
    }

    @GetMapping(path = "/cleanup/room/{roomId}")
    List<Cleanup> getCleanupByRoomId(@PathVariable long roomId) {
        return cleanupService.getCleanupByRoomId(roomId);
    }

    @PatchMapping("{roomId}")
    Room patchRoom(@RequestBody JsonPatch patch, @PathVariable long roomId) {
        return roomService.patchProduct(roomId, patch);
    }

    @DeleteMapping(path = "{roomId}")
    void deleteRoomById(@PathVariable long roomId) {
        roomService.deleteRoomById(roomId);
    }
}

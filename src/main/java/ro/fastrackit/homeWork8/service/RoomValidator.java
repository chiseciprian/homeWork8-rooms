package ro.fastrackit.homeWork8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.fastrackit.homeWork8.controller.exception.ValidationException;
import ro.fastrackit.homeWork8.model.entity.Room;
import ro.fastrackit.homeWork8.repository.RoomRepository;

import java.util.Optional;

import static java.util.Optional.empty;

@Component
@RequiredArgsConstructor
public class RoomValidator {
    private final RoomRepository repo;

    public void validateExistsOrThrow(long roomId) {
        exists(roomId).ifPresent(ex -> {
            throw ex;
        });
    }

    public void validateReplaceThrow(long roomId, Room newRoom) {
        exists(roomId)
                .or(() -> validate(newRoom))
                .ifPresent(ex -> {
                    throw ex;
                });
    }

    private Optional<ValidationException> validate(Room room) {
        if (room.getNumber() == null) {
            return Optional.of(new ValidationException("Number cannot be null"));
        } else if (room.getHotelName() == null) {
            return Optional.of(new ValidationException("Hotel name cannot be null"));
        } else if (repo.existsByNumber(room.getNumber())) {
            return Optional.of(new ValidationException("Name cannot be duplicate"));
        } else {
            return empty();
        }
    }

    private Optional<ValidationException> exists(long roomId) {
        return repo.existsById(roomId)
                ? empty()
                : Optional.of(new ValidationException("Room with id " + roomId + " doesn't exist"));
    }
}

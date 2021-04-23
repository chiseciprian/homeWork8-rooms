package ro.fastrackit.homeWork8.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ro.fastrackit.homeWork8.controller.exception.EntityNotFoundException;
import ro.fastrackit.homeWork8.controller.exception.ValidationException;
import ro.fastrackit.homeWork8.model.RoomsFilters;
import ro.fastrackit.homeWork8.model.entity.Room;
import ro.fastrackit.homeWork8.repository.RoomDao;
import ro.fastrackit.homeWork8.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository repository;
    private final RoomDao roomDao;
    private final RoomValidator validator;
    private final ObjectMapper mapper;

    public List<Room> getAll(RoomsFilters filters) {
        return roomDao.getAll(filters);
    }

    public Optional<Room> getRoomById(long roomId) {
        return repository.findById(roomId);
    }

    public void deleteRoomById(long roomId) {
        repository.deleteById(roomId);
    }

    @SneakyThrows
    public Room patchProduct(long roomId, JsonPatch patch) {
        validator.validateExistsOrThrow(roomId);
        Room dbRoom = repository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find room with id " + roomId));

        JsonNode patchedProductJson = patch.apply(mapper.valueToTree(dbRoom));
        Room patchedProduct = mapper.treeToValue(patchedProductJson, Room.class);
        return replaceRoom(roomId, patchedProduct);
    }

    private Room replaceRoom(long roomId, Room newRoom) {
        newRoom.setId(roomId);
        validator.validateReplaceThrow(roomId, newRoom);

        Room dbProduct = repository.findById(roomId)
                .orElseThrow(() -> new ValidationException("Couldn't find room with id " + roomId));
        copyProduct(newRoom, dbProduct);
        return repository.save(dbProduct);
    }

    private void copyProduct(Room newRoom, Room dbProduct) {
        dbProduct.setFloor(newRoom.getFloor());
        dbProduct.setHotelName(newRoom.getHotelName());
        dbProduct.setNumber(newRoom.getNumber());
    }
}

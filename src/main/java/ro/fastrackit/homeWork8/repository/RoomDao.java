package ro.fastrackit.homeWork8.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ro.fastrackit.homeWork8.model.RoomsFilters;
import ro.fastrackit.homeWork8.model.entity.Room;
import ro.fastrackit.homeWork8.model.entity.RoomFacilities;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Repository
public class RoomDao {
    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    @Autowired
    public RoomDao(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = this.entityManager.getCriteriaBuilder();
    }

    public List<Room> getAll(RoomsFilters filters) {
        CriteriaQuery<Room> criteriaRoom = criteriaBuilder.createQuery(Room.class);
        Root<Room> root = criteriaRoom.from(Room.class);
        CriteriaQuery<RoomFacilities> roomFacilitiesCriteriaQuery = criteriaBuilder.createQuery(RoomFacilities.class);
        Root<RoomFacilities> roomFacilitiesRoot = roomFacilitiesCriteriaQuery.from(RoomFacilities.class);
        List<Predicate> whereClauseForRoom = roomFilters(filters, root);
        List<Predicate> whereClauseForRoomFacilities = roomFacilitiesFilters(filters, roomFacilitiesRoot);
        List<Room> roomList = getRooms(criteriaRoom, root, whereClauseForRoom);
        List<Room> roomsFromRoomFacilities = getRoomsByRoomFacilitiesFilter(roomFacilitiesCriteriaQuery, roomFacilitiesRoot, whereClauseForRoomFacilities);
        roomList.retainAll(roomsFromRoomFacilities);
        return roomList;
    }

    private List<Predicate> roomFacilitiesFilters(RoomsFilters filters, Root<RoomFacilities> roomFacilitiesRoot) {
        List<Predicate> whereClauseFacilities = new ArrayList<>();
        ofNullable(filters.getHasTv())
                .ifPresent(hasTv -> whereClauseFacilities.add(criteriaBuilder.equal(roomFacilitiesRoot.get("hasTv"), hasTv)));
        ofNullable(filters.getHasDoubleBed())
                .ifPresent(hasDoubleBed -> whereClauseFacilities.add(criteriaBuilder.equal(roomFacilitiesRoot.get("hasDoubleBed"), hasDoubleBed)));
        return whereClauseFacilities;
    }

    private List<Predicate> roomFilters(RoomsFilters filters, Root<Room> root) {
        List<Predicate> whereClause = new ArrayList<>();
        ofNullable(filters.getNumber())
                .ifPresent(number -> whereClause.add(criteriaBuilder.equal(root.get("number"), number)));
        ofNullable(filters.getHotel())
                .ifPresent(hotel -> whereClause.add(criteriaBuilder.equal(root.get("hotelName"), hotel)));
        ofNullable(filters.getFloor())
                .ifPresent(floor -> whereClause.add(criteriaBuilder.equal(root.get("floor"), floor)));
        return whereClause;
    }

    private List<Room> getRooms(CriteriaQuery<Room> criteriaRoom, Root<Room> root, List<Predicate> whereClause) {
        CriteriaQuery<Room> queryRoom = criteriaRoom.select(root).where(whereClause.toArray(new Predicate[0]));
        return entityManager.createQuery(queryRoom).getResultList();
    }

    private List<Room> getRoomsByRoomFacilitiesFilter(CriteriaQuery<RoomFacilities> roomFacilitiesCriteriaQuery, Root<RoomFacilities> roomFacilitiesRoot, List<Predicate> whereClauseFacilities) {
        CriteriaQuery<RoomFacilities> queryRoomFacilities = roomFacilitiesCriteriaQuery.select(roomFacilitiesRoot).where(whereClauseFacilities.toArray(new Predicate[0]));
        List<RoomFacilities> roomFacilitiesList = entityManager.createQuery(queryRoomFacilities).getResultList();
        return roomFacilitiesList.stream()
                .map(RoomFacilities::getRoom)
                .distinct()
                .collect(Collectors.toList());
    }
}

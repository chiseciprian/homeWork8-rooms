package ro.fastrackit.homeWork8.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomFacilities {
    @Id
    @GeneratedValue
    private Long id;

    private boolean hasTv;
    private boolean hasDoubleBed;

    @OneToOne
    private Room room;
}

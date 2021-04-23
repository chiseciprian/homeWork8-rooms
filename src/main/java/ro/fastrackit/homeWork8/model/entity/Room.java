package ro.fastrackit.homeWork8.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class  Room {
    @Id
    @GeneratedValue
    private Long id;

    private String number;
    private int floor;
    private String hotelName;
}

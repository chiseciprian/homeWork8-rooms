package ro.fastrackit.homeWork8.model;

import lombok.Value;

@Value
public class RoomsFilters {
    String number;
    Integer floor;
    String hotel;
    Boolean hasTv;
    Boolean hasDoubleBed;
}
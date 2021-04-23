package ro.fastrackit.homeWork8.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CleaningProcedure {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int outcome;

    @ManyToMany
    private List<Cleanup> cleanup;
}

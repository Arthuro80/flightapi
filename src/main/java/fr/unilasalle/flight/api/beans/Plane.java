package fr.unilasalle.flight.api.beans;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.*;



@Entity
@Table(name="planes")

@Getter
@Setter
@NoArgsConstructor
public class Plane extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name="planes_sequence_in_java_code",
            sequenceName="planes_sequence_in_database", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="planes_sequence_in_database")
    private Long id;

    @NotBlank(message="operator must be set")
    @Column(nullable=false)
    private String operator;

    @NotBlank(message="model must be set")
    @Column(nullable=false)
    private String model;

    @NotBlank(message="immatriculation must be set")
    @Size(max=6, message = "immatriculation must be 6 less lenght")
    @Column(nullable=false)
    private String immatriculation;

    @NotNull(message="capacity must be set")
    @Min(value=2, message="capacity has to be more than 2")
    @Column(nullable=false)
    private Integer capacity;
}
package fr.unilasalle.flight.api.beans;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name="flights")

@Getter
@Setter
@NoArgsConstructor

public class Flight extends PanacheEntityBase {
    @Id
    @SequenceGenerator(name="flight_sequence_in_java_code",
            sequenceName="planes_sequence_in_database", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="planes_sequence_in_database")
    private Long id;

    @NotBlank(message="Number must be set")
    @Column(nullable=false)
    private String number;

    @NotBlank(message="Origin must be set")
    @Column(nullable=false)
    private String origin;

    @NotBlank(message="destination must be set")
    @Column(nullable=false)
    private String destination;

    @NotBlank(message="departure_date must be set")
    @Column(nullable=false)
    private String departure_date;

    @NotBlank(message="departure_time must be set")
    @Column(nullable=false)
    private String departure_time;

    @NotBlank(message="arrival_date must be set")
    @Column(nullable=false)
    private String arrival_time;

    @NotBlank(message="plane_id must be set")
    @Column(nullable=false)
    private String plane_id;

}
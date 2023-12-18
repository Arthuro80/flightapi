package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Passenger;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PassengerRepository implements PanacheRepositoryBase<Passenger, Long> {


}

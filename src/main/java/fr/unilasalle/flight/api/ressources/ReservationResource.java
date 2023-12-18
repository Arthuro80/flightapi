package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Passenger;
import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repositories.PassengerRepository;
import fr.unilasalle.flight.api.repositories.ReservationRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/reservations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservationResource {

    @Inject
    ReservationRepository reservationRepository;

    @Inject
    PassengerRepository passengerRepository;

    @GET
    public Response getAllReservations() {
        var reservations = reservationRepository.listAll();
        return Response.ok(reservations).build();
    }

    @GET
    @Path("/flight/{flightId}")
    public Response getReservationsByFlight(@PathParam("flightId") Long flightId) {
        List<Reservation> reservations = reservationRepository.find("flight.id", flightId).list();
        if (reservations.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(reservations).build();
    }

    @GET
    @Path("/{id}")
    public Response getReservationById(@PathParam("id") Long id) {
        var reservation = reservationRepository.findById(id);
        if (reservation != null) {
            return Response.ok(reservation).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Transactional
    public Response addReservation(Reservation reservation) {
        Passenger passenger = findOrCreatePassenger(reservation.getPassenger());

        reservation.setPassenger(passenger);
        reservationRepository.persist(reservation);
        return Response.status(Response.Status.CREATED).entity(reservation).build();
    }

    private Passenger findOrCreatePassenger(Passenger passenger) {
        // Recherchez le passager par un attribut unique, comme l'e-mail
        Passenger existingPassenger = passengerRepository.find("emailAddress", passenger.getEmailAddress()).firstResult();

        if (existingPassenger == null) {
            passengerRepository.persist(passenger);
            return passenger;
        } else {
            return existingPassenger;
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteReservation(@PathParam("id") Long id) {
        Reservation reservation = reservationRepository.findById(id);
        if (reservation != null) {
            reservationRepository.delete(reservation);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}

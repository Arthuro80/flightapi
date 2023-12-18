package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Flight;
import fr.unilasalle.flight.api.beans.Reservation;
import fr.unilasalle.flight.api.repositories.FlightRepository;
import fr.unilasalle.flight.api.repositories.ReservationRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/Flight")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FlightRessource extends GenericRessource {

    @Inject
    FlightRepository flightRepository;

    @Inject
    ReservationRepository reservationRepository;

    @Inject
    Validator validator;

    @GET
    public Response getFlights() {
        var list = flightRepository.listAll();
        return getOr404(list);
    }

    @GET
    @Path("/destination/{destination}")
    public Response getFlightsByDestination(@PathParam("destination") String destination) {
        var flights = flightRepository.findByDestination(destination);
        return getOr404(flights);
    }

    @GET
    @Path("/{id}")
    public Response getFlightById(@PathParam("id") Long id) {
        var flight = flightRepository.findById(id);
        return getOr404(flight);
    }

    @POST
    @Transactional
    public Response addFlight(Flight flight) {
        var violations = validator.validate(flight);
        if (!violations.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorWrapper(violations)).build();
        }

        flightRepository.persist(flight);
        return Response.status(Response.Status.CREATED).entity(flight).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteFlight(@PathParam("id") Long id) {
        Flight flight = flightRepository.findById(id);
        if (flight != null) {
            // Supprimer d'abord toutes les réservations associées à ce vol
            deleteAssociatedReservations(flight);

            flightRepository.delete(flight);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private void deleteAssociatedReservations(Flight flight) {
        List<Reservation> reservations = reservationRepository.find("flight", flight).list();
        for (Reservation reservation : reservations) {
            reservationRepository.delete(reservation);
        }
    }

}

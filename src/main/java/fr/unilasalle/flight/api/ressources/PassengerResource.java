package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Passenger;
import fr.unilasalle.flight.api.repositories.PassengerRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

@Path("/passengers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PassengerResource {

    @Inject
    PassengerRepository passengerRepository;

    @GET
    public Response getAllPassengers() {
        var passengers = passengerRepository.listAll();
        return Response.ok(passengers).build();
    }

    @GET
    @Path("/{id}")
    public Response getPassengerById(@PathParam("id") Long id) {
        var passenger = passengerRepository.findById(id);
        if (passenger != null) {
            return Response.ok(passenger).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updatePassenger(@PathParam("id") Long id, Passenger updatedPassenger) {
        Passenger passenger = passengerRepository.findById(id);
        if (passenger == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        passenger.setSurname(updatedPassenger.getSurname());
        passenger.setFirstname(updatedPassenger.getFirstname());
        passenger.setEmailAddress(updatedPassenger.getEmailAddress());
        // Mettre à jour d'autres champs si nécessaire
        passengerRepository.persist(passenger);
        return Response.ok(passenger).build();
    }

    @POST
    @Transactional
    public Response addPassenger(Passenger passenger) {
        passengerRepository.persist(passenger);
        return Response.status(Response.Status.CREATED).entity(passenger).build();
    }

}

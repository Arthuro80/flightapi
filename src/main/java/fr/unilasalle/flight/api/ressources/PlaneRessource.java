package fr.unilasalle.flight.api.ressources;

import fr.unilasalle.flight.api.beans.Plane;
import fr.unilasalle.flight.api.repositories.PlaneRepository;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Plane")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)


public class PlaneRessource extends GenericRessource {
    @Inject
    PlaneRepository repository;

    @Inject
    Validator validator;


    @GET
    public Response getPlanes(){
        var list=repository.listAll();
        return getOr404(list);
    }
    @GET
    @Path("/{id}")
    public Response getPlaneById(@PathParam("id") Long id) {
        var plane = repository.findById(id);
        return getOr404(plane);
    }

    @GET
    @Path("/manufacturer/{manufacturer}")
    public Response getPlanesByManufacturer(@PathParam("manufacturer") String manufacturer) {
        var planes = repository.findByManufacturer(manufacturer); // Assurez-vous que cette méthode est implémentée dans PlaneRepository
        return getOr404(planes);
    }


    @POST
    @Transactional
    public Response createPlane(Plane plane){
        var violations = validator.validate(plane);

        if(!violations.isEmpty()){
            return Response.status(400).entity(
                    new GenericRessource.ErrorWrapper(violations)).build();
        }

        try{
            repository.persistAndFlush(plane);
            return Response.ok().status(201).build();
        } catch (PersistenceException e){
            return Response.serverError().entity(
                    new ErrorWrapper("Error while creating the plane")).build();
        }
    }
}
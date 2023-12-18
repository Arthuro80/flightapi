package fr.unilasalle.flight.api.repositories;

import fr.unilasalle.flight.api.beans.Plane;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.inject.Model;

import java.util.List;

@Model
public class PlaneRepository implements PanacheRepositoryBase<Plane,Long> {
    public List<Plane> findByOperator(String operatorParameter){
        return find("operator", operatorParameter).list();
    }
    public List<Plane> findByManufacturer(String manufacturer) {
        return find("manufacturer", manufacturer).list(); // Remplacez "manufacturer" par le nom exact du champ dans la classe Plane si différent
    }
}
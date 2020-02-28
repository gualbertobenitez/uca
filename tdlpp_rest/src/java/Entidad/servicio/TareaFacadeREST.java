/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad.servicio;

import Entidad.Persona;
import Entidad.Tarea;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author Gualberto Benitez
 */
@Stateless
@Path("tarea")
public class TareaFacadeREST extends AbstractFacade<Tarea> {
    @PersistenceContext(unitName = "tdlpp_restPU")
    private EntityManager em;

    public TareaFacadeREST() {
        super(Tarea.class);
    }

   @POST
    @Consumes({"application/json"})
    @Override
    @Produces({"application/json"})
    public Tarea createR(Tarea entity) {
        return super.createR(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Tarea edit(@PathParam("id") Integer id, Tarea entity) {
        return super.editR(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Tarea find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Tarea> findAll() {
        return super.findAll();
    }
    
     @GET
    @Path("persona/{duenoId}")
    @Produces({"application/json"})
    //@Produces("text/plain")
    public List<Tarea> findByDuenoId(@PathParam("duenoId") Integer duenoId) {
        
        Query query=getEntityManager().createNamedQuery("Tarea.findByDueno", Tarea.class).setParameter("duenoId",getEntityManager().find(Persona.class, duenoId));
        return query.getResultList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Tarea> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

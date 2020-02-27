/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad.servicio;

import Entidad.Persona;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author Gualberto Benitez
 */
@Stateless
@Path("persona")
public class PersonaFacadeREST extends AbstractFacade<Persona> {
    @PersistenceContext(unitName = "tdlpp_restPU")
    private EntityManager em;

    public PersonaFacadeREST() {
        super(Persona.class);
    }

    @POST
    @Consumes({"application/json"})
    @Override
    @Produces({"application/json"})
    public Persona createR(Persona entity) {
        return super.createR(entity);
    }

    @POST
    @Path("login/{user}/{pass}")
    @Produces({"application/json"})
    public Response login(@PathParam("user") String userName, @PathParam("pass") Integer password) {
        List<Persona> p;
        //p=getEntityManager().find(Persona.class, 1);
        Query query=getEntityManager().createNamedQuery("Persona.findByNombreAndId", Persona.class).setParameter("nombre",userName).setParameter("personaId", password);
        p=query.getResultList();
        if (p.size()==1)
            return Response.ok(p.get(0)).build();
        else
            return Response.ok().build();
    }
    
    
    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Integer id, Persona entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Persona find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Persona> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("rol/{rolId}")
    @Produces({"application/json"})
    public List<Persona> findAllByRol(@PathParam("rolId") String rolId) {
        Query query=getEntityManager().createNamedQuery("Persona.findByRol", Persona.class).setParameter("rol",rolId.charAt(0));
        //return super.findAll();
        return query.getResultList();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Persona> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

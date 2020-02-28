/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad.servicio;

import Entidad.Actividad;
import Entidad.Proyecto;
import Entidad.Tarea;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("actividad")
public class ActividadFacadeREST extends AbstractFacade<Actividad> {
    @PersistenceContext(unitName = "tdlpp_restPU")
    private EntityManager em;

    public ActividadFacadeREST() {
        super(Actividad.class);
    }

    @POST
    @Consumes({"application/json"})
    @Override
    @Produces({"application/json"})
    public Actividad createR(Actividad entity) {
        return super.createR(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Actividad edit(@PathParam("id") Integer id, Actividad entity) {
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
    public Actividad find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/tarea")
    @Produces({"application/json"})
    //@Produces("text/plain")
    public List<Tarea> findWithTarea(@PathParam("id") Integer id) {
        //Collection<Actividad> act= p.getActividadCollection();
        List<Tarea> actT=null;
        Collection tar;
        try{
            tar=getEntityManager().find(Actividad.class, id).getTareaCollection();
           //actL=(List<Actividad>) act.stream().collect(Collectors.toList());
            actT=new ArrayList<>(tar);
        }
        catch(Exception e) {
            //return Response.noContent().build();
        }
        return actT;
        
    }
    
    @GET
    @Path("{id}/tarea/{tid}")
    @Produces({"application/json"})
    public Tarea findWithTareaId(@PathParam("id") Integer id,@PathParam("tid") Integer tid) {
       Tarea tar;
       tar=getEntityManager().find(Tarea.class, tid);
       return tar;
    }
    
    @GET
    @Override
    @Produces({"application/json"})
    public List<Actividad> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({ "application/json"})
    public List<Actividad> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

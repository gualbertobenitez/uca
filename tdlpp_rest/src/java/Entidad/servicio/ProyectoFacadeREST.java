/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidad.servicio;

import Entidad.Actividad;
import Entidad.Persona;
import Entidad.Proyecto;
import Entidad.Tarea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import static javax.persistence.GenerationType.values;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Gualberto Benitez
 */
@Stateless
@Path("proyecto")
public class ProyectoFacadeREST extends AbstractFacade<Proyecto> {
    @PersistenceContext(unitName = "tdlpp_restPU")
    private EntityManager em;

    public ProyectoFacadeREST() {
        super(Proyecto.class);
    }

    @POST
    @Consumes({"application/json"})
    @Override
    @Produces({"application/json"})
    public Proyecto createR(Proyecto entity) {
        return super.createR(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    @Produces({"application/json"})
    public Proyecto edit(@PathParam("id") Integer id, Proyecto entity) {
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
    public Proyecto find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Path("{id}/actividad")
    @Produces({"application/json"})
    //@Produces("text/plain")
    public List<Actividad> findWithActivity(@PathParam("id") Integer id) {
        //Collection<Actividad> act= p.getActividadCollection();
        List<Actividad> actL=null;
        Collection act;
        try{
            act=getEntityManager().find(Proyecto.class, id).getActividadCollection();
           //actL=(List<Actividad>) act.stream().collect(Collectors.toList());
            actL=new ArrayList<>(act);
        }
        catch(Exception e) {
            //return Response.noContent().build();
        }
        return actL;
        
    }
    
    @GET
    @Path("{id}/actividad/{id}/tarea")
    @Produces({"application/json"})
    //@Produces("text/plain")
    public List<Tarea> findWithActivityWithTarea(@PathParam("id") Integer id, @PathParam("aid") Integer aid) {
        List<Tarea> tarL=null;
        Collection tar;
        try{
            tar=getEntityManager().find(Actividad.class, id).getTareaCollection();
           //actL=(List<Actividad>) act.stream().collect(Collectors.toList());
            tarL=new ArrayList<>(tar);
        }
        catch(Exception e) {
            //return Response.noContent().build();
        }
        return tarL;
        
    }
    
    @GET
    @Path("{id}/actividad/{aid}")
    @Produces({"application/json"})
    //@Produces("text/plain")
    public Actividad findWithActivityId(@PathParam("id") Integer id,@PathParam("aid") Integer aid) {
        Actividad act;
        act=getEntityManager().find(Actividad.class, aid);
        return act;
        
    }
    
    @GET
    @Path("persona/{duenoId}")
    @Produces({"application/json"})
    //@Produces("text/plain")
    public List<Proyecto> findByDuenoId(@PathParam("duenoId") Integer duenoId) {
        
        Query query=getEntityManager().createNamedQuery("Proyecto.findByDueno", Proyecto.class).setParameter("duenoId",getEntityManager().find(Persona.class, duenoId));
//        p=query.getResultList();
//        if (p.size()==1)
//            return Response.ok(p.get(0)).build();
//        else
            //return Response.ok(query.getResultList()).build();
             return query.getResultList();
    }
    
    @GET
    @Override
    @Produces({"application/json"})
    public List<Proyecto> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Proyecto> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    
//    @GET
//    @Path("test")
//  @Produces({"application/json"})
//  public Response getPersons(@Context UriInfo uriInfo) {
//      List<Proyecto> proyectos = super.findAll();
//      proyectos.forEach(p -> initLinks(p, uriInfo));
//
//      GenericEntity<List<Proyecto>> genericEntity =
//              new GenericEntity<List<Proyecto>>(proyectos) {};
//
//      Link self = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder())
//                      .rel("self").build();
//      Link historyLink = Link.fromUriBuilder(uriInfo.getAbsolutePathBuilder()
//                                                    .path("history")
//                                                    .queryParam("year", "1990"))
//                             .rel("history").build();
//
//      return Response.ok(genericEntity)
//                     .links(self, historyLink).build();
//  }
//
//     private void initLinks(Proyecto proyecto, UriInfo uriInfo) {
//      //create self link
//      UriBuilder uriBuilder = uriInfo.getRequestUriBuilder();
//      uriBuilder = uriBuilder.path(Integer.toString(proyecto.getProyectoId()));
//      Link.Builder linkBuilder = Link.fromUriBuilder(uriBuilder);
//      Link selfLink = linkBuilder.rel("self").build();
//      //also we can add other meta-data by using: linkBuilder.param(..),
//      // linkBuilder.type(..), linkBuilder.title(..)
//      proyecto.setLinks(Arrays.asList(selfLink));
//  }
}

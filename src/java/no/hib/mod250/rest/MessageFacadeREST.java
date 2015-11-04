/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.mod250.rest;

import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import no.hib.mod250.entities.Message;

/**
 *
 * @author Anders
 */
@Stateless
@Path("messages")
public class MessageFacadeREST extends AbstractFacade<Message> {
    
    @PersistenceContext(unitName = "ChatServerPU")
    private EntityManager em;

    public MessageFacadeREST() {
        super(Message.class);
    }

    @POST
    @Consumes("application/json")
    @Path("new")
    public void newMessage(final Message m) {
        m.setPostedTime(Calendar.getInstance());
        this.create(m);
    }

    @GET
    @Path("last")
    @Produces({"application/json"})
    public List<Message> getMessages() {      
        Query q = em.createQuery("SELECT m FROM Message m ORDER BY m.postedTime DESC").setMaxResults(100);

        return q.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hib.mod250.rest;

import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    
//    @EJB
//    private RoomFacade rf;

    public MessageFacadeREST() {
        super(Message.class);
    }

    @POST
    @Path("new")
    public void newMessage(final Message m) {
        
        
        m.setPostedTime(Calendar.getInstance());
       
    
        super.create(m);
    }

    @GET
    @Path("last")
    @Produces({"application/json"})
    public List<Message> getMessages() {      
      //  Query q = em.createQuery("SELECT m FROM Message m ORDER BY m.postedTime DESC").setMaxResults(100);
     Query q = em.createQuery("SELECT m FROM Message m ORDER BY m.postedTime");
        if(q.getResultList().size() > 100)
        return q.getResultList().subList(q.getResultList().size() - 100, q.getResultList().size());
        
        else
            
        return q.getResultList();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

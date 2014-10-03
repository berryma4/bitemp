/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp;

import edu.msu.frib.olog.bitemp.bitemporal.TimeUtils;
import javax.persistence.*;
import org.junit.*;

/**
 *
 * @author berryman
 */
public class TestingStuff {
    private static EntityManagerFactory factory;
    @Test
    public void Testing() {
        factory = Persistence.createEntityManagerFactory("olog_test");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        
        TimeUtils.setReference(TimeUtils.day(1,1,2010));
        Log log = new Log();
        log.getEntries().set(new Entry());
        log.getEntries().set(new Entry(), TimeUtils.from(TimeUtils.current()));
        
        em.getTransaction().commit();
        em.close();
        
        System.out.println(log.getEntries().getTrace().toString());
        //System.out.println(log.getEntries().now().getCreationDate().toString());
        
        
    }
}

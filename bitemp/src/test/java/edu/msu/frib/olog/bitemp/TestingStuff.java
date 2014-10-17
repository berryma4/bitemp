/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp;

import edu.msu.frib.olog.bitemp.entity.Log;
import edu.msu.frib.olog.bitemp.entity.Entry;
import edu.msu.frib.olog.bitemp.entity.bitemporal.TimeUtils;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import org.junit.*;

/**
 *
 * @author berryman
 */
@Stateless
@Local
public class TestingStuff {
    @PersistenceContext(unitName = "olog_test")
    private EntityManager em = null;
    
    @Test
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void Testing() {

        TimeUtils.setReference(TimeUtils.day(1,1,2010));
        Log log = new Log();
       // log.getEntries().set(new Entry(TimeUtils.current().toDate(),log, "owner", "source", "description"));
       // log.getEntries().set(new Entry(TimeUtils.current().toDate(),log, "owner2", "source2", "description2"), TimeUtils.from(TimeUtils.current()));
        
        em.persist(log);

        System.out.println(log.getEntries().getTrace().toString());
        //System.out.println(log.getEntries().now().getCreationDate().toString());
        
        
    }
}

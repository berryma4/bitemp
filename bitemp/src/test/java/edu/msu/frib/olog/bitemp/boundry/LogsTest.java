/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp.boundry;

import edu.msu.frib.olog.bitemp.entity.Log;
import edu.msu.frib.olog.bitemp.entity.XmlLog;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author berryman
 */
public class LogsTest {
    
    Logs logs;
    
    @Before
    public void init(){
        this.logs = new Logs();
        this.logs.em = mock(EntityManager.class);
        this.logs.tracer = mock(Logger.class);
    }
    
    void mockQuery(String name, List<Log> results) {
        Query mockedQuery = mock(Query.class);
        when(mockedQuery.getResultList()).thenReturn(results);
        when(mockedQuery.setParameter(Matchers.anyString(), Matchers.anyObject())).thenReturn(mockedQuery);
        when(this.logs.em.createNamedQuery(name)).thenReturn(mockedQuery);
    }
    
    @Test
    public void successfulLog() {
        Log log = new Log();
        merge(log);
        this.logs.save(log);
    }
    
    void merge(Log log) {
        when(this.logs.em.merge(log)).thenReturn(log);
    }
    
}

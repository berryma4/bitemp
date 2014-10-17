/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp.boundry;

import edu.msu.frib.olog.bitemp.entity.bitemporal.TimeUtils;
import edu.msu.frib.olog.bitemp.control.UserValidator;
import edu.msu.frib.olog.bitemp.entity.Entry;
import edu.msu.frib.olog.bitemp.entity.Log;
import edu.msu.frib.olog.bitemp.entity.XmlLog;
import java.util.stream.Collector;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author berryman
 */
@Stateless
public class Logs {
    @Inject
    UserValidator lv;
    
    @PersistenceContext
    EntityManager em;
    
    @Inject
    Logger tracer;
    
    public static final String UUID = "uuid";
    
    public JsonObject save(Log log){
        tracer.info("log arrived: " + log.toString());
        TimeUtils.setReference(TimeUtils.day(1,1,2010));
        Entry entry = new Entry(log, TimeUtils.current().toDate(), "owner", "source", "description");
        log.getEntries().set(entry);
        em.persist(log);
        return convert(log);
    }
    
    public List<Log> all() {
        return this.em.createNamedQuery(Log.findAll).getResultList();
    }
    
    public JsonArray allAsJson() {
        Collector<JsonObject, ?, JsonArrayBuilder> jsonCollector
                = Collector.of(Json::createArrayBuilder, JsonArrayBuilder::add,
                        (left, right) -> {
                            left.add(right);
                            return left;
                        });
        return all().stream().map(this::convert).
                collect(jsonCollector).build();

    }
    
    
    JsonObject convert(Log log) {

        return Json.createObjectBuilder().
                add("id", log.getId()).
                add(UUID, log.getId()).build();
    }
    
    
}

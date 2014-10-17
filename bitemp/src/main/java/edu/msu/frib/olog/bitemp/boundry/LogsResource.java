/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp.boundry;

import edu.msu.frib.olog.bitemp.entity.Log;
import edu.msu.frib.olog.bitemp.entity.XmlLog;
import java.net.URI;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.dozer.DozerBeanMapperSingletonWrapper;

/**
 *
 * @author berryman
 */
@Stateless
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("logs")
public class LogsResource {
    
    @Inject
    Logs logs;
    
    @POST
    @Produces({"application/olog-v2+xml", "application/olog-v2+json"})
    @Consumes({"application/olog-v2+xml", "application/olog-v2+json"})
    public Response save(XmlLog request, @Context UriInfo info) {
        Log logMapped = DozerBeanMapperSingletonWrapper.getInstance().map(request, Log.class, "v2");
        JsonObject log = logs.save(logMapped);
        long id = log.getInt(Logs.UUID);
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).entity(log).build();
    }
    
    @GET
    @Produces({"application/olog-v2+xml", "application/olog-v2+json"})
    public Response all() {
        JsonArray registrationList = this.logs.allAsJson();
        if (registrationList == null || registrationList.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(registrationList).build();
    }
    
}

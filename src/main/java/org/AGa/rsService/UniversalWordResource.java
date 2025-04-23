package org.AGa.rsService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;

@Path("{path: .*}")
public class UniversalWordResource {

    @Inject
    private DictionaryService dictionaryService;

    private static final String configuredPath = ConfigurationLoader.getProperty("search.path");

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response handleDynamicRequest(@PathParam("path") String path,
                                         @QueryParam("letters") String letters) {
        if (path == null || !path.equals(configuredPath)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Invalid path\"}")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        List<String> results = dictionaryService.findWords(letters);
        return Response.ok(results).build();
    }
}

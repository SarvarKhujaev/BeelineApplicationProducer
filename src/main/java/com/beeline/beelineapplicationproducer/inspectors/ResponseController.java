package com.beeline.beelineapplicationproducer.inspectors;

import javax.ws.rs.core.Response;

public class ResponseController extends LogInspector {
    protected ResponseController() {}

    protected Response getResponse (
            final String message,
            final Response.Status status
    ) {
        return Response.ok()
                .status( status )
                .entity( message )
                .build();
    }
}

package org.acme.controller;

import org.acme.dto.validationgroups.ForCreate;
import org.acme.dto.validationgroups.ForUpdate;
import org.acme.dto.MyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.ConvertGroup;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/hello")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GreetingResource {
    private static final Logger log = LoggerFactory.getLogger(GreetingResource.class);

    @GET
    public String hello() {
        return "Hello RESTEasy";
    }

    @POST
    public void create(@Valid @ConvertGroup(to = ForCreate.class) MyDTO myDTO) {
    }

    @PUT
    @Path("{id}")
    public void update(@PathParam("id") @Valid @NotNull @NotBlank @Pattern(regexp = "[a-z]{3,7}") String id,
                       @Valid @ConvertGroup(to = ForUpdate.class) MyDTO myDTO) {
    }
}
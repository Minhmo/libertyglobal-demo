package com.libertyglobal.demo.schedule.api;

import com.codahale.metrics.annotation.Timed;
import com.libertyglobal.demo.schedule.db.ScheduleDAO;
import com.libertyglobal.demo.schedule.model.Program;
import com.libertyglobal.demo.schedule.validation.CorrectDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Resource that contains necessary API for managing TV schedule
 */
@Path("/schedule")
@Produces(MediaType.APPLICATION_JSON)
public class TVScheduleResource {
    private final ScheduleDAO scheduleDAO;

    private static final AtomicLong initCounter = new AtomicLong();
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private final String defaultName;
    private final AtomicLong counter;

    public TVScheduleResource(ScheduleDAO scheduleDAO, String defaultName) {
        this.scheduleDAO = scheduleDAO;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
        LOGGER.warn("TVScheduleResource number {} created", initCounter.incrementAndGet());
    }

    /**
     * Returns the name of every program in schedule.
     *
     * @return
     */
    @GET
    @Timed
    @Path("/all")
    public Set<String> getAll() {
        return scheduleDAO.getPrograms();
    }

    /**
     * Creates a program and adds to schedule.
     *
     * @param program
     * @return STATUS 409 if program creation failed, STATUS 200 otherwise
     */
    @PUT
    @Path("/create")
    public Response create(@Valid Program program) {
        Program created = scheduleDAO.create(program);

        if (created == null) {
            LOGGER.warn("Unable to create program", program);
            return Response
                    .status(Response.Status.CONFLICT)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Could not create program")
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(created)
                .build();
    }

    /**
     * Method that updates an existing program
     *
     * @param program
     * @return
     */
    @POST
    public Response update(@Valid Program program) {
        Program updated = scheduleDAO.update(program);

        if (updated == null) {
            LOGGER.warn("Unable to update updated");

            return Response
                    .status(Response.Status.FORBIDDEN)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Program does not exist.")
                    .build();
        }

        return Response
                .status(Response.Status.CREATED)
                .type(MediaType.APPLICATION_JSON)
                .entity(updated)
                .build();

    }

    /**
     * Method that deletes an existing program from schedule by ID.
     *
     * @param id
     * @return
     */
    @DELETE
    @Path("/delete")
    public Response delete(@QueryParam("id") @NotNull String id) {
        boolean removed = scheduleDAO.delete(id);

        if (!removed) {
            LOGGER.warn("Unable to delete the program", id);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .entity("Entity not found")
                    .build();
        }

        return Response
                .status(Response.Status.OK)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity("Deleted")
                .build();
    }


    /**
     * Method that filters schedule by Date or keywords (can be either or combination of both).
     *
     * @param dateStr
     * @param words
     * @return
     */
    @GET
    @Timed
    @Path("/filter")
    public List<Program> filtered(@QueryParam("date") @CorrectDateFormat(pattern = "yyyy-MM-dd", message = "Incorrect date format")
                                          String dateStr, @QueryParam("words") List<String> words) {
        LocalDate date = LocalDate.parse(dateStr);
        return scheduleDAO.getByDateAndWords(date, words);
    }
}

package com.libertyglobal.demo.schedule;

import com.libertyglobal.demo.schedule.api.TVScheduleResource;
import com.libertyglobal.demo.schedule.client.ExternalScheduleResource;
import com.libertyglobal.demo.schedule.db.ScheduleDAO;
import com.libertyglobal.demo.schedule.model.Program;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import java.time.LocalDate;
import java.util.List;


public class TVScheduleApplication extends Application<TVScheduleConfiguration> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) throws Exception {
        new TVScheduleApplication().run(args);
    }

    @Override
    public String getName() {
        return "tv-schedule";
    }

    @Override
    public void initialize(Bootstrap<TVScheduleConfiguration> bootstrap) {
    }

    @Override
    public void run(TVScheduleConfiguration configuration,
                    Environment environment) {
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
        ExternalScheduleResource scheduleService = new ExternalScheduleResource(client);
        environment.jersey().register(scheduleService);

        // Get the schedule from WEB.
        List<Program> schedule = scheduleService.getSchedule(LocalDate.now());
        ScheduleDAO scheduleDAO = new ScheduleDAO(schedule);

        final TVScheduleResource resource = new TVScheduleResource(scheduleDAO, "TVSchedule");

        environment.jersey().register(resource);
    }
}
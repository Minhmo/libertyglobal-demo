package com.libertyglobal.demo.schedule.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libertyglobal.demo.schedule.model.Program;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExternalScheduleResource {
    private final Client client;

    public ExternalScheduleResource(Client client) {
        this.client = client;
    }

    public List<Program> getSchedule(LocalDate date) {
        List<Program> schedule = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            LocalDate nextDate = date.plus(i, ChronoUnit.DAYS);

            WebTarget webTarget = client.target("http://api.tvmaze.com/").path("schedule")
                    .queryParam("country", "GB")
                    .queryParam("date", nextDate.toString());

            String body = requestSchedule(webTarget);
            schedule.addAll(parseSchedule(body));
        }

        return schedule;
    }

    public List<Program> parseSchedule(String body) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            Iterator<JsonNode> shows = jsonNode.elements();
            ArrayList<Program> programs = new ArrayList<>();

            shows.forEachRemaining(e -> programs.add(Program.fromJson(e)));
            return programs;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to parse JSON", e);
        }
    }

    private String requestSchedule(WebTarget webTarget) {
        Response response = webTarget.request(MediaType.APPLICATION_JSON_TYPE).get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        return response.readEntity(String.class);
    }
}

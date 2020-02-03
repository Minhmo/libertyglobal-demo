package com.libertyglobal.demo.schedule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.libertyglobal.demo.schedule.validation.CorrectRange;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@CorrectRange(message = "Program start must be earlier than program end.")
@Data
public class Program {
    @JsonProperty
    private String id;

    @JsonProperty
    @NotNull
    private String name;

    @JsonProperty
    @NotNull
    private String channel;

    @JsonProperty
    @NotNull
    private LocalDate date;

    @JsonProperty
    @NotNull
    private LocalTime start;

    @JsonProperty
    @NotNull
    private LocalTime end;

    /**
     * For Jackson serialization
     */
    public Program() {
    }

    public Program(String id, String name, String channel, LocalDate date, LocalTime start, LocalTime end) {
        this.id = id;
        this.name = name;
        this.channel = channel;
        this.date = date;
        this.start = start;
        this.end = end;
    }

//    @JsonProperty
//    public String getId() {
//        return id;
//    }
//
//    @JsonProperty
//    @NotNull
//    public String getName() {
//        return name;
//    }
//
//    @JsonProperty
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    @JsonProperty
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    @JsonProperty
//    @NotNull
//    public String getChannel() {
//        return channel;
//    }
//
//    @JsonProperty
//    public void setChannel(String channel) {
//        this.channel = channel;
//    }
//
//    @JsonProperty
//    @NotNull
//    public LocalDate getDate() {
//        return date;
//    }
//
//    @JsonProperty
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    @JsonProperty
//    @NotNull
//    public LocalTime getStart() {
//        return start;
//    }
//
//    @JsonProperty
//    public void setStart(LocalTime start) {
//        this.start = start;
//    }
//
//    @JsonProperty
//    @NotNull
//    public LocalTime getEnd() {
//        return end;
//    }
//
//    @JsonProperty
//    public void setEnd(LocalTime end) {
//        this.end = end;
//    }

    public static Program fromJson(JsonNode json) {

//        ObjectMapper objectMapper = new ObjectMapper();

        LocalDate airdate = LocalDate.parse(json.path("airdate").asText());
        LocalTime startTime = LocalTime.parse(json.path("airtime").asText());

//            long runtime = Long.parseLong();
        LocalTime endTime = startTime.plus(json.path("runtime").asLong(), ChronoUnit.MINUTES);

        JsonNode show = json.path("show");

        String name = show.path("name").asText();
        String channel = show.path("network").path("name").asText();

        return new Program(UUID.randomUUID().toString(), name, channel, airdate, startTime, endTime);
    }
}

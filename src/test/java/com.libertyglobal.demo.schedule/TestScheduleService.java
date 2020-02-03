package com.libertyglobal.demo.schedule;

import com.libertyglobal.demo.schedule.client.ExternalScheduleResource;
import com.libertyglobal.demo.schedule.model.Program;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestScheduleService {
    private String jsonExample = "[{\"id\":1791701,\"url\":\"http://www.tvmaze.com/episodes/1791701/breakfast-2020-02-03-03022020\",\"name\":\"03/02/2020\",\"season\":2020,\"number\"" +
            ":34,\"airdate\":\"2020-02-03\",\"airtime\":\"06:00\",\"airstamp\":\"2020-02-03T06:00:00+00:00\",\"runtime\":195,\"image\":null,\"summary\":\"<p>The latest news, sport, " +
            "business and weather from the BBC's Breakfast team.</p>\",\"show\":{\"id\":3377,\"url\":\"http://www.tvmaze.com/shows/3377/breakfast\"," +
            "\"name\":\"Breakfast\",\"type\":\"News\",\"language\":\"English\",\"genres\":[],\"status\":\"Running\",\"runtime\":195,\"premiered\":\"2002-09-27\",\"officialSite\"" +
            ":\"http://www.bbc.co.uk/programmes/b006v5tb\",\"schedule\":{\"time\":\"06:00\",\"days\":[\"Monday\",\"Tuesday\",\"Wednesday\",\"Thursday\",\"Friday\",\"Saturday\",\"Sunday\"]}" +
            ",\"rating\":{\"average\":null},\"weight\":53,\"network\":{\"id\":12,\"name\":\"BBC One\",\"country\":" +
            "{\"name\":\"United Kingdom\",\"code\":\"GB\",\"timezone\":\"Europe/London\"}},\"webChannel\":null,\"externals\":{\"tvrage\":null,\"thetvdb\":77938,\"imdb\":null}," +
            "\"image\":{\"medium\":\"http://static.tvmaze.com/uploads/images/medium_portrait/168/421375.jpg\"," +
            "\"original\":\"http://static.tvmaze.com/uploads/images/original_untouched/168/421375.jpg\"},\"summary\":\"<p>The latest news, sport, business and weather from the BBC's " +
            "Breakfast team.</p>\",\"updated\":1579389433,\"_links\":{\"self\":{\"href\":\"http://api.tvmaze.com/shows/3377\"},\"previousepisode\":{\"href\":\"" +
            "http://api.tvmaze.com/episodes/1791701\"},\"nextepisode\":{\"href\":\"http://api.tvmaze.com/episodes/1791702\"}}},\"_links\":{\"self\":{\"href\":" +
            "\"http://api.tvmaze.com/episodes/1791701\"}}}]";

    private ExternalScheduleResource externalScheduleResource = new ExternalScheduleResource(null);

    @Test
    public void test_json_parsing() {
        List<Program> programs = externalScheduleResource.parseSchedule(jsonExample);

        assertEquals(programs.size(), 1);
        assertEquals(programs.get(0).getName(), "Breakfast");
    }

}

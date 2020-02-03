Dropwizard Skeleton
================

Intro
-------
Demo project for technical skills assessment for Liberty Global.
Project has API, to manage schedule, basic filtering (correct date format, non null fields, start - end time validation).
Application calls external API in order to retrieve programs, parses them and stores in memory.

Running
-----
In project root directory run:

```mvn clean package && java -jar target/dropwizard-tv-schedule-1.0-SNAPSHOT.jar server example.yml```

By default runs on ```localhost:8080/```

To run tests ```mvn clean test```


URLs
----
* POST    /schedule **Updates program** (takes model as body parameter)
* GET     /schedule/all **Gets all program names**
* PUT     /schedule/create **Creates a program** (takes model as body parameter)
* DELETE  /schedule/delete **Deletes the program** (parameters: id (String, query parameter))
* GET     /schedule/filter **Filters schedule by date or keywords** (parameters: date (String ("yyyy-MM-dd"), query parameter) and words (list of strings, query parameter))

Model format
----
```$xslt
{
    "id": "ab430397-15c0-46d5-a042-987fd699eee7",
    "name": "MY PROGRAM2",
    "channel": "Channel 1",
    "date": [ //year, month, day
        2020,
        2,
        4
    ],
    "start": [ //hour, minute
        21,
        0
    ],
    "end": [ //hour, minute
        22,
        0
    ]
}
```

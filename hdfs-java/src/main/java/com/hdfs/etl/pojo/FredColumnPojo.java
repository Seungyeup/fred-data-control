package com.hdfs.etl.pojo;

import com.hdfs.etl.util.DefaultLocalDateTimeDeserializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({
        "id", //: "AKUR",
        "realtime_start", //: "2023-09-26",
        "realtime_end", //: "2023-09-26",
        "title", //: "Unemployment Rate in Alaska",
        "observation_start", //: "1976-01-01",
        "observation_end", //: "2023-08-01",
        "frequency", //: "Monthly",
        "frequency_short", //: "M",
        "units", //: "Percent",
        "units_short", //: "%",
        "seasonal_adjustment", //: "Seasonally Adjusted",
        "seasonal_adjustment_short", //: "SA",
        "last_updated", //: "2023-09-21 10:08:05-05",
        "popularity", //: 13,
        "group_popularity", //: 16
        "notes"
})
public class FredColumnPojo {

    @JsonProperty("id")
    private String id;

    @JsonProperty("realtime_start")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate realtime_start;

    @JsonProperty("realtime_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate realtime_end;

    @JsonProperty("title")
    private String title;

    @JsonProperty("observation_start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate observation_start;

    @JsonProperty("observation_end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate observation_end;

    @JsonProperty("frequency")
    private String frequency;

    @JsonProperty("frequency_short")
    private String frequency_short;

    @JsonProperty("units")
    private String units;

    @JsonProperty("units_short")
    private String units_short;

    @JsonProperty("seasonal_adjustment")
    private String seasonal_adjustment;

    @JsonProperty("seasonal_adjustment_short")
    private String seasonal_adjustment_short;

    @JsonProperty("last_updated")
    @JsonDeserialize(using = DefaultLocalDateTimeDeserializer.class)
    private LocalDateTime last_updated;

    @JsonProperty("popularity")
    private short popularity;

    @JsonProperty("group_popularity")
    private short group_popularity;

    @JsonProperty("notes")
    private String notes;

}

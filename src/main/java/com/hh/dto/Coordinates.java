package com.hh.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {

    @JsonProperty("lon")
    private String longitude;
    @JsonProperty("lat")
    private String latitude;
}

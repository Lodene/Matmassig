package com.school.matmassig.userpreferencesservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserPreferenceDTO {
    private Long id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("genre")
    private Integer genre;

    @JsonProperty("value")
    private String value;

    @JsonProperty("topic")
    private String topic;
}

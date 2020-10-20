package ru.digital.league.manualfias.dto.delta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FiasDeltaDto {

    @JsonProperty("VersionId")
    private Long versionId;

    @JsonProperty("TextVersion")
    private String textVersion;
}


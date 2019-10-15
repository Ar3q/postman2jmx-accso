package com.loadium.postman2jmx.model.postman.variables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmanVariable {

    @JsonProperty("key")
    private String key;

    @JsonProperty("value")
    private String value;

    @JsonProperty("enabled")
    private boolean enabled;

    public PostmanVariable() {
        this.key = "";
        this.value = "";
        this.enabled = false; // TODO good default value?
    }
}

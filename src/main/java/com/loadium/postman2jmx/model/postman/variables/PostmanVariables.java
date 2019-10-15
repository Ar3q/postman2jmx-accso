package com.loadium.postman2jmx.model.postman.variables;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostmanVariables {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("values")
    private List<PostmanVariable> variables;

    public PostmanVariables() {
        id = "";
        name = "";
        variables = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<PostmanVariable> getVariables() {
        return variables;
    }
}

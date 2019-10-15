package com.loadium.postman2jmx.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loadium.postman2jmx.model.postman.variables.PostmanVariables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PostmanVariablesParser {
    private ObjectMapper objectMapper;
    private Map<String, String> variables;

    public PostmanVariablesParser() {
        this.objectMapper = new ObjectMapper();
        this.variables = new HashMap<>();
    }


    public Map<String, String> readVariables(String fileName) throws IOException {
        PostmanVariables jsonRepresentation = readVariablesJsonToModel(fileName);
        Map<String, String> variables = new HashMap<>();
        jsonRepresentation.getVariables().forEach(
                variableMapping -> variables.put(variableMapping.getKey(), variableMapping.getValue())
        );
        return variables;
    }

    private PostmanVariables readVariablesJsonToModel(String fileName) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(fileName));
        return objectMapper.readValue(jsonData, PostmanVariables.class);
    }

}

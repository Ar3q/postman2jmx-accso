package com.loadium.postman2jmx.builder;

import com.loadium.postman2jmx.model.postman.PostmanCollection;
import com.loadium.postman2jmx.model.jmx.JmxFile;

import java.util.Map;

public interface IJmxFileBuilder {

    JmxFile build(PostmanCollection postmanCollection, String jmxOutputFilePath, Map<String, String> variables) throws Exception;
}

package com.loadium.postman2jmx.builder;

import com.loadium.postman2jmx.model.jmx.JmxFile;
import com.loadium.postman2jmx.model.postman.PostmanCollection;

import java.util.Map;

public class JmxFileBuilder extends AbstractJmxFileBuilder {

    @Override
    public JmxFile build(PostmanCollection postmanCollection, String jmxOutputFilePath, Map<String, String> variables) throws Exception {
        return super.buildJmxFile(postmanCollection, jmxOutputFilePath, variables);
    }
}

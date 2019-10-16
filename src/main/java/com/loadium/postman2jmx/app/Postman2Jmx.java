package com.loadium.postman2jmx.app;

import com.loadium.postman2jmx.builder.JmxFileBuilder;
import com.loadium.postman2jmx.exception.InvalidArgumentsException;
import com.loadium.postman2jmx.model.postman.PostmanCollection;
import com.loadium.postman2jmx.parser.IParser;
import com.loadium.postman2jmx.parser.ParserFactory;
import com.loadium.postman2jmx.parser.PostmanVariablesParser;
import com.loadium.postman2jmx.parser.variables.VariablesResolver;
import com.loadium.postman2jmx.utils.CollectionVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


public class Postman2Jmx {

    private static Logger logger = LoggerFactory.getLogger(Postman2Jmx.class.getName());

    // TODO: replace with actual location of the variables JSONs / provide means for dynamic path specification
    private static String GLOBAL_VARIABLES = ".\\input\\variables\\globals.postman_globals.json";

    private static String AT_VARIABLES = ".\\input\\variables\\AT.postman_environment.json";
    private static String CI_VARIABLES = ".\\input\\variables\\CI.postman_environment.json";
    private static String ET_VARIABLES = ".\\input\\variables\\ET.postman_environment.json";
    private static String ET_WS_VARIABLES = ".\\input\\variables\\ET_WS.postman_environment.json";
    private static String INT_VARIABLES = ".\\input\\variables\\INT.postman_environment.json";
    private static String SPRINT_VARIABLES = ".\\input\\variables\\Sprint.postman_environment.json";
    private static String LOCALHOST_VARIABLES = ".\\input\\variables\\localhost.postman_environment.json";


    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                throw new InvalidArgumentsException();
            }

            String postmanCollectionJson = args[0];
            String jmxOutputFile = args[1];

            logger.info("Trying to parse postman collection file: {}", postmanCollectionJson);
            IParser parser = ParserFactory.getParser(CollectionVersion.V2);
            PostmanCollection postmanCollection = parser.parse(postmanCollectionJson);
            logger.info("Successfully parsed postman collection file: {}, Total parsed item count: {} ", postmanCollectionJson, postmanCollection.getItems().size());

            // parse Postman variables from separate JSON files
            PostmanVariablesParser postmanVariablesParser = ParserFactory.getPostmanVariablesParser();
            Map<String, String> variables = new HashMap<>();
            logger.info("Trying to parse global variables from JSON file: {}", GLOBAL_VARIABLES);
            variables.putAll(postmanVariablesParser.readVariables(GLOBAL_VARIABLES));
            logger.info("Trying to parse localhost environment variables from JSON file: {}", LOCALHOST_VARIABLES);
            variables.putAll(postmanVariablesParser.readVariables(LOCALHOST_VARIABLES));
            // TODO: parse other files / provide means to manage different environments
//            logger.info("Trying to parse AT environment variables from JSON file: {}", AT_VARIABLES);
//            variables.putAll(postmanVariablesParser.readVariables(AT_VARIABLES));

            // replace variable references in variable definitions with their values
            VariablesResolver resolver = VariablesResolver.getInstance();
            variables = resolver.resolve(variables);

            logger.info("Trying to build jmx file: {}", jmxOutputFile);
            JmxFileBuilder jmxFileBuilder = new JmxFileBuilder();
            jmxFileBuilder.build(postmanCollection, jmxOutputFile, variables);
            logger.info("Successfully build jmx file: {} ", jmxOutputFile);

        } catch (Exception e) {
            logger.error("Error occurred!", e);
        }
    }


}

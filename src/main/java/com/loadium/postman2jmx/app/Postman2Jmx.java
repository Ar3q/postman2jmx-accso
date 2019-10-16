package com.loadium.postman2jmx.app;

import com.loadium.postman2jmx.builder.JmxFileBuilder;
import com.loadium.postman2jmx.exception.InvalidArgumentsException;
import com.loadium.postman2jmx.model.postman.PostmanCollection;
import com.loadium.postman2jmx.model.postman.PostmanItem;
import com.loadium.postman2jmx.model.postman.PostmanUrl;
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
    private static final String GLOBAL_VARIABLES = ".\\input\\variables\\globals.postman_globals.json";

    private static final String AT_VARIABLES = ".\\input\\variables\\AT.postman_environment.json";
    private static final String CI_VARIABLES = ".\\input\\variables\\CI.postman_environment.json";
    private static final String ET_VARIABLES = ".\\input\\variables\\ET.postman_environment.json";
    private static final String ET_WS_VARIABLES = ".\\input\\variables\\ET_WS.postman_environment.json";
    private static final String INT_VARIABLES = ".\\input\\variables\\INT.postman_environment.json";
    private static final String SPRINT_VARIABLES = ".\\input\\variables\\Sprint.postman_environment.json";
    private static final String LOCALHOST_VARIABLES = ".\\input\\variables\\localhost.postman_environment.json";

    private static final String AT_ARG = "AT";
    private static final String CI_ARG = "CI";
    private static final String ET_ARG = "ET";
    private static final String ET_WS_ARG = "ET_WS";
    private static final String INT_ARG = "INT";
    private static final String SPRINT_ARG = "SPRINT";
    private static final String LOCALHOST_ARG = "LOCALHOST";


    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                throw new InvalidArgumentsException();
            }

            String postmanCollectionJson = args[0];
            String jmxOutputFile = args[1];
            String environment = args[2];

            logger.info("Trying to parse postman collection file: {}", postmanCollectionJson);
            IParser parser = ParserFactory.getParser(CollectionVersion.V2);
            PostmanCollection postmanCollection = parser.parse(postmanCollectionJson);
            logger.info("Successfully parsed postman collection file: {}, Total parsed item count: {} ", postmanCollectionJson, postmanCollection.getItems().size());

            // parse Postman variables from separate JSON files
            PostmanVariablesParser postmanVariablesParser = ParserFactory.getPostmanVariablesParser();
            Map<String, String> variables = new HashMap<>();
            logger.info("Trying to parse global variables from JSON file: {}", GLOBAL_VARIABLES);
            variables.putAll(postmanVariablesParser.readVariables(GLOBAL_VARIABLES));
            logger.info("Trying to parse {} environment variables from JSON file: {}", environment, getEnvironmentVariablesPath(environment));
            variables.putAll(postmanVariablesParser.readVariables(getEnvironmentVariablesPath(environment)));

            // replace variable references in variable definitions with their values
            logger.info("Replace variable references in all variable values with their values...");
            VariablesResolver resolver = VariablesResolver.getInstance();
            variables = resolver.resolve(variables, VariablesResolver.POSTMAN_VAR_REF_PATTERN);

            // replace all references in raw URLs with their values to enable correct URL decomposition
            logger.info("Replace variable references in postman URLs with their values...");
            for (PostmanItem item : postmanCollection.getItems()) {
                PostmanUrl url = item.getRequest().getUrl();
                // resolve with JMeter variable reference syntax (converter pre-converts to JMeter syntax)
                String resolved = resolver.resolve(variables, url.getRaw(), VariablesResolver.JMETER_VAR_REF_PATTERN);
                url.setRaw(resolved);
            }

            logger.info("Trying to build jmx file: {}", jmxOutputFile);
            JmxFileBuilder jmxFileBuilder = new JmxFileBuilder();
            jmxFileBuilder.build(postmanCollection, jmxOutputFile, variables);
            logger.info("Successfully build jmx file: {} ", jmxOutputFile);

        } catch (Exception e) {
            logger.error("Error occurred!", e);
        }
    }

    private static String getEnvironmentVariablesPath(String envArg) throws InvalidArgumentsException {
        switch (envArg) {
            case AT_ARG: return AT_VARIABLES;
            case CI_ARG: return CI_VARIABLES;
            case ET_ARG: return ET_VARIABLES;
            case ET_WS_ARG: return ET_VARIABLES;
            case INT_ARG: return INT_VARIABLES;
            case SPRINT_ARG: return SPRINT_VARIABLES;
            case LOCALHOST_ARG: return LOCALHOST_VARIABLES;
            default: throw new InvalidArgumentsException();
        }
    }


}

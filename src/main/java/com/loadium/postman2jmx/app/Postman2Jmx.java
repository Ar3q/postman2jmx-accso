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

    public static void main(String[] args) {
        try {
            if (args.length != 3) {
                throw new InvalidArgumentsException();
            }

            String postmanCollectionJsonPath = args[0];
            String postmanEnvironmentsJsonPath = args[1];
            String jmxOutputFile = args[2];

            logger.info("Trying to parse postman collection file: {}", postmanCollectionJsonPath);
            IParser parser = ParserFactory.getParser(CollectionVersion.V2);
            PostmanCollection postmanCollection = parser.parse(postmanCollectionJsonPath);
            logger.info("Successfully parsed postman collection file: {}, Total parsed item count: {} ", postmanCollectionJsonPath, postmanCollection.getItems().size());

            // parse Postman variables from separate JSON files
            PostmanVariablesParser postmanVariablesParser = ParserFactory.getPostmanVariablesParser();
            Map<String, String> variables = new HashMap<>();
            logger.info("Trying to parse environment variables from JSON file: {}", postmanEnvironmentsJsonPath);
            variables.putAll(postmanVariablesParser.readVariables(postmanEnvironmentsJsonPath));

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

}

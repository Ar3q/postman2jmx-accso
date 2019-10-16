package com.loadium.postman2jmx.parser.variables;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariablesResolver {
    public static final String POSTMAN_VAR_REF_PATTERN = "\\{\\{([^\\{\\}]*)\\}\\}";
    public static final String JMETER_VAR_REF_PATTERN = "\\$\\{([^\\{\\}\\$]*)\\}";

    private static VariablesResolver instance = null;
    public static VariablesResolver getInstance() {
        if (instance==null) {
            instance = new VariablesResolver();
        }
        return instance;
    }
    private VariablesResolver() {}

    /**
     * Reads the specified string and replaces all variable references in it with its corresponding values in mappings.
     * @param mappings
     * @param varValue
     * @return
     */
    public String resolve(Map<String, String> mappings, String varValue, String pattern) {
        Pattern varRefPattern = Pattern.compile(pattern);
        Matcher m = varRefPattern.matcher(varValue);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            // replace variable reference with its value
            m.appendReplacement(sb, mappings.get(m.group(1)));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * Replaces all variable references in variable values with their corresponding values.
     * @param mappings
     * @return
     */
    public Map<String, String> resolve(Map<String, String> mappings, String pattern) {
        Map<String, String> resolved = new HashMap<>();
        mappings.forEach((varName, value) -> {
                    resolved.put(varName, resolve(mappings, value, pattern));
                });
        return resolved;
    }
}

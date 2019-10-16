package com.loadium.postman2jmx.parser.variables;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariablesResolver {
    private static VariablesResolver instance = null;
    public static VariablesResolver getInstance() {
        if (instance==null) {
            instance = new VariablesResolver();
        }
        return instance;
    }
    private VariablesResolver() {}

    public Map<String, String> resolve(Map<String, String> mappings) {
        Pattern varRefPattern = Pattern.compile("\\{\\{([^\\{\\}]*)\\}\\}");
        Map<String, String> resolved = new HashMap<>();
        mappings.forEach((varName, value) -> {
                    Matcher m = varRefPattern.matcher(value);
                    StringBuffer sb = new StringBuffer();
                    while (m.find()) {
                        // replace variable reference with its value
                        m.appendReplacement(sb, mappings.get(m.group(1)));
                    }
                    m.appendTail(sb);
                    resolved.put(varName, sb.toString());
                });
        return resolved;
    }
}

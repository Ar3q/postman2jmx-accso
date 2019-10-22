package com.loadium.postman2jmx.model.jmx;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.protocol.http.config.gui.HttpDefaultsGui;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSampler;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.TestElementProperty;

public class JmxHTTPRequestDefaults {
    public static ConfigTestElement newInstance() {
        ConfigTestElement httpRequestDefaults = new ConfigTestElement();
        httpRequestDefaults.setProperty(TestElement.GUI_CLASS, HttpDefaultsGui.class.getName());
        httpRequestDefaults.setProperty(TestElement.TEST_CLASS, ConfigTestElement.class.getName());
        httpRequestDefaults.setName("HTTP Request Defaults");

        // create arguments - change if needed
        httpRequestDefaults.setProperty(new TestElementProperty(HTTPSampler.ARGUMENTS, new HTTPArgumentsPanel().createTestElement()));

        // set settings fields
        httpRequestDefaults.setProperty(HTTPSampler.DOMAIN, "");
        httpRequestDefaults.setProperty(HTTPSampler.PORT, "");
        httpRequestDefaults.setProperty(HTTPSampler.PROTOCOL, "");
        httpRequestDefaults.setProperty(HTTPSampler.CONTENT_ENCODING, "UTF-8");
        httpRequestDefaults.setProperty(HTTPSampler.PATH, "");
        httpRequestDefaults.setProperty(HTTPSampler.CONCURRENT_POOL, "");
        httpRequestDefaults.setProperty(HTTPSampler.CONNECT_TIMEOUT, "");
        httpRequestDefaults.setProperty(HTTPSampler.RESPONSE_TIMEOUT, "");

        return httpRequestDefaults;
    }
}

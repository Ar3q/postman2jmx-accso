package com.loadium.postman2jmx.builder;

import com.loadium.postman2jmx.model.jmx.JmxHTTPSamplerProxy;
import com.loadium.postman2jmx.model.postman.PostmanItem;
import com.loadium.postman2jmx.model.postman.PostmanRawBody;
import com.loadium.postman2jmx.utils.ValueUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.protocol.http.util.HTTPArgument;

public class JmxRawBodyBuilder extends AbstractJmxBodyBuilder {

    @Override
    public HTTPSamplerProxy buildJmxBody(PostmanItem postmanItem) throws Exception {

        HTTPSamplerProxy httpSamplerProxy = JmxHTTPSamplerProxy.newInstance(postmanItem);

        String httpMethod = httpSamplerProxy.getMethod();
        if ("post".equalsIgnoreCase(httpMethod) || "put".equalsIgnoreCase(httpMethod)) { // TODO: create body for delete method too?
            httpSamplerProxy.setPostBodyRaw(true);
            Arguments arguments = new Arguments();
            PostmanRawBody raw = postmanItem.getRequest().getBody().getRaw();

            if (raw.getValue() != null && !raw.getValue().isEmpty()) {
                HTTPArgument argument = new HTTPArgument();
                argument.setEnabled(true);
                argument.setAlwaysEncoded(false);
                argument.setMetaData("=");

                // substitute postman variable references with jmeter syntax
                String jmeterBody = ValueUtils.value(raw.getValue());

                argument.setValue(jmeterBody);
                arguments.addArgument(argument);
            }
            httpSamplerProxy.setArguments(arguments);
        }

        return httpSamplerProxy;
    }
}

package com.loadium.postman2jmx.model.jmx;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;

import java.util.Map;

public class JmxTestPlan {

    public static TestPlan newInstance(String name) {
        TestPlan testPlan = new TestPlan();
        testPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        testPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        testPlan.setName(name);
        testPlan.setEnabled(true);
        testPlan.setFunctionalMode(false);
        testPlan.setComment("");
        testPlan.setSerialized(false);
        testPlan.setTestPlanClasspath("");
        testPlan.setUserDefinedVariables(new Arguments());
        return testPlan;
    }

    public static TestPlan newInstance(String name, Map<String, String> variables) {
        TestPlan testPlan = newInstance(name);
        Arguments args = new Arguments();
        variables.forEach(
                (varName, varValue) -> args.addArgument(varName, varValue)
        );
        testPlan.setUserDefinedVariables(args);
        return testPlan;
    }
}

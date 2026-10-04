package org.example.tests;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/feautures",
        glue = "org.example.stepdefinitions",
        monochrome = true,
        plugin = {"html:target/cucumber.html"}
)
public class TestNGRunner extends AbstractTestNGCucumberTests {



}
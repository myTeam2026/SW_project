/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package runners;

/**
 *
 * @author hp
 */

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features",
                
      
    glue = {"steps"}  ,                           
    plugin = {"pretty", "html:target/cucumber-reports/login.html"}, 
    monochrome = true                               
)
public class TestRunner {
}

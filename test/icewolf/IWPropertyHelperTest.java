/*   
   Icewolf -- a lightweight web-browser
   Copyright 2017 Nick Ivanov, Gregory Bowen, Dylan Parsons

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package icewolf;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class IWPropertyHelperTest {
    
    public IWPropertyHelperTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getProperty method, of class IWPropertyHelper.
     */
    @Test
    public void testGetProperty() {
        System.out.println("Test getProperty...");
        String property = "application_name";
        String expResult = "Icewolf";
        String result = IWPropertyHelper.getProperty(property);
        assertEquals(expResult, result);
    }
   
    @Test
    public void testApplicationPropertiesExists() {
        System.out.println("Make sure the properties file exists...");
        File propertiesFile = new File(IWPropertyHelper.PROPERTY_FILEPATH);
        
        if(!propertiesFile.exists()) {
            fail("File doesn't exist.");
        }
        
        if(propertiesFile.isDirectory()) {
            fail("The properties file is a directory, not a regular file.");
        }
    }
}

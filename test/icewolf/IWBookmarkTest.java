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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class IWBookmarkTest {
    
    public IWBookmarkTest() {
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

    @Test
    public void testGetId() {
        System.out.println("getId");
        IWBookmark instance = new IWBookmark("one", "two", "three", "four");
        String expResult = "one";
        String result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "one";
        IWBookmark instance = new IWBookmark("one", "two", "three", "four");
        instance.setId(id);
    }

    @Test
    public void testGetName() {
        System.out.println("getName");
        IWBookmark instance = new IWBookmark("one", "two", "three", "four");
        String expResult = "two";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        IWBookmark instance = new IWBookmark();
        instance.setName(name);
    }

    @Test
    public void testGetUrl() {
        System.out.println("getUrl");
        IWBookmark instance = new IWBookmark("one", "two", "three", "four");
        String expResult = "three";
        String result = instance.getUrl();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCategory() {
        System.out.println("getCategory");
        IWBookmark instance = new IWBookmark("one", "two", "three", "four");
        String expResult = "four";
        String result = instance.getCategory();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetUrl() {
        System.out.println("setUrl");
        String url = "";
        IWBookmark instance = new IWBookmark();
        instance.setUrl(url);
    }

    @Test
    public void testSetCategory() {
        System.out.println("setCategory");
        String category = "";
        IWBookmark instance = new IWBookmark();
        instance.setCategory(category);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        IWBookmark instance = new IWBookmark("one", "two", "three", "four");
        String expResult = "IWBookmark{id=one, name=two, url=three, category=four}";
        String result = instance.toString();
        assertEquals(expResult, result);
    }
    
}

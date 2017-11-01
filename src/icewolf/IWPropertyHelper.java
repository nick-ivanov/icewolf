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

import java.util.*;
import java.io.*;
import java.util.Map.Entry;

public class IWPropertyHelper {
    final static String PROPERTY_FILEPATH = "src/icewolf/application.properties";

    static String getProperty(String property) {
        try {
            FileReader reader = new FileReader(PROPERTY_FILEPATH);
            Properties propertiesObj = new Properties();
            propertiesObj.load(reader);
            return propertiesObj.getProperty(property);
        } catch (Exception ex) {
            System.out.println("FATAL ERROR: " + ex.getMessage());
            System.exit(1);
        }
        
        return null;
    }
    
    static Set<Entry<Object,Object>> getPropertySet() {
        try {
            FileReader reader = new FileReader(PROPERTY_FILEPATH);
            Properties propertiesObj = new Properties();
            propertiesObj.load(reader);
            return propertiesObj.entrySet();
        } catch (Exception ex) {
            System.out.println("FATAL ERROR: " + ex.getMessage());
            System.exit(1);
        }
        
        return null;
    }
}

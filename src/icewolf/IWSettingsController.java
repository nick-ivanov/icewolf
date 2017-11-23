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

public class IWSettingsController {
    private IWDatabaseHelper dbHelper;

    public IWSettingsController() {
        dbHelper = new IWDatabaseHelper();
    }
    
    public int getMinimumWidth() {
        return Integer.parseInt(dbHelper.getSetting("minimum_width"));
    }
    
    public void setMinimumWidth(int value) {
        dbHelper.updateSetting("minimum_width", String.format("%d", value));
    }
    
    public int getMinimumHeight() {
        return Integer.parseInt(dbHelper.getSetting("minimum_height"));
    }
    
    public void setMinimumHeight(int value) {
        dbHelper.updateSetting("minimum_height", String.format("%d", value));
    }
    
    public String getSearchEngine() {
        return dbHelper.getSetting("search_engine");
    }
    
    public void setSearchEngine(String engine) {
        dbHelper.updateSetting("search_engine", engine);
    }
    
    public String getHomepage() {
        return dbHelper.getSetting("homepage");
    }
    
    public void setHomepage(String url) {
        dbHelper.updateSetting("homepage", url);
    }
    
    public void setUseHomepage(boolean value) {
        if(value) {
            dbHelper.updateSetting("use_homepage", "true");
        } else {
            dbHelper.updateSetting("use_homepage", "false");
        }
    }
    
    public boolean getUseHomepage() {
        if(dbHelper.getSetting("use_homepage").equals("true")) {
            return true;
        } else if (dbHelper.getSetting("use_homepage").equals("false")) {
            return false;
        } else {
            System.err.println("Fatal error [getUseHomepage()]");
            System.exit(1);
        }
        
        return false;
    }
}

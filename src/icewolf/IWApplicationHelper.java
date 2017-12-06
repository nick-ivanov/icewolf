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

import java.util.ArrayList;
import java.util.List;

public class IWApplicationHelper 
{
    private static IWApplicationHelper myObj;
    private static List<String> urlInQueue;
    private static List<String> urlToSkipForDownloadCheck;
    /**
     * Create private constructor
     */
    private IWApplicationHelper(){
         
    }
    /**
     * Create a static method to get instance.
     */
    public static IWApplicationHelper getInstance()
    {
        if(myObj == null)
        {
            myObj = new IWApplicationHelper();
            urlInQueue = new ArrayList<String>();
            initSkipUrls();
        }
        return myObj;
    }
     
    public List<String> getUrlQueue()
    {
        return urlInQueue;
    }
    
    public List<String> getSkipUrlList()
    {
        return urlToSkipForDownloadCheck;
    }
    
    private static void initSkipUrls()
    {
        urlToSkipForDownloadCheck = new ArrayList<String>();
        urlToSkipForDownloadCheck.add("accounts.google.com");
        urlToSkipForDownloadCheck.add("www.google.com");
    }
}

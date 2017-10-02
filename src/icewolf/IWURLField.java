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

import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author gbowen
 */
public class IWURLField extends TextField {

    private WebView webView;
    private boolean securedHTTP; //If true, use HTTPS on web URL's
    
    public IWURLField(WebView webView, boolean secured) {
        this.webView = webView;
        this.securedHTTP = secured;
        setText(IWPropertyHelper.getProperty("default_homepage"));
    }
    
    public void setSecured(boolean newStatus){
        this.securedHTTP = newStatus;
    }
    
    public void loadPage(){
        if(securedHTTP){
            webView.getEngine().load(java.net.URI.create
                ("https://" + getText()).toString());
        }
        else{
            webView.getEngine().load(java.net.URI.create
                ("http://" + getText()).toString());
        }
    }
}
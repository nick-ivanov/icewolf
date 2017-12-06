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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

/**
 *
 * @author gbowen
 */
public class IWURLField extends TextField {

    private WebView webView;
    private Tab tab;
    private boolean securedHTTP; //If true, use HTTPS on web URL's
    
    public IWURLField(WebView webView, boolean secured, Tab tab) {
        this.webView = webView;
        this.tab = tab;
        this.securedHTTP = secured;
        setText(IWPropertyHelper.getProperty("default_homepage"));
        
        webView.getEngine().getLoadWorker().stateProperty().addListener(
        new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                if (newValue == State.SUCCEEDED) {
                    tab.setText(webView.getEngine().getTitle());
                }
                
                if (newValue == State.FAILED) {
                    if(getText().toString().equals("")) {
                        webView.getEngine().loadContent("<html><title>Empty Tab</title><body></body></html>");
                    } else {
                        webView.getEngine().loadContent("<html><title>Oops!</title><body><b>Page not found!</b></body></html>");
                    }
                }
            }
        });
    }
    
    public void setSecured(boolean newStatus){
        this.securedHTTP = newStatus;
    }
    
    public void loadPage()
    {
        if(securedHTTP)
        {
            try {
            webView.getEngine().load(java.net.URI.create
                ("https://" + getText()).toString());

            } catch (Exception ex) {
                System.out.println("gotcha: " + ex.getMessage());
                
                if(getText().toString().equals("")) {
                    webView.getEngine().loadContent("<html><title>Empty Tab</title><body></body></html>");
                } else {
                    webView.getEngine().loadContent("<html><title>Oops!</title><body><b>Page not found!</b></body></html>");
                }
            }
        }
        else
        {
            try {
            webView.getEngine().load(java.net.URI.create
                ("http://" + getText()).toString());

            } catch (Exception ex) {
                System.out.println("gotcha: " + ex.getMessage());
                
                if(getText().toString().equals("")) {
                    webView.getEngine().loadContent("<html><title>Empty Tab</title><body></body></html>");
                } else {
                    webView.getEngine().loadContent("<html><title>Oops!</title><body><b>Page not found!</b></body></html>");
                }            }
        }
    }
}
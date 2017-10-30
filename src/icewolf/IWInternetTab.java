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
/**
 *
 * @author gbowen
 */
package icewolf;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;
import javafx.concurrent.Worker;

public class IWInternetTab extends Tab {

    private Label searchLabel;
    private WebView tabWebView;
    private IWSearchBox searchField;
    private IWURLField urlField;
    private Button newTabBtn;
    private HBox hbox;
    private BorderPane bPane;
    
    
    public IWInternetTab(String webAddress, TabPane tabPane) {
        searchLabel = new Label("Search: ");
    
        tabWebView = new WebView();
        textProperty().bind(tabWebView.getEngine().titleProperty());
        tabWebView.setStyle("-fx-pref-width: 780; -fx-pref-height: 1530;");
        
        searchField = new IWSearchBox(tabWebView, 0);
        searchField.setOnAction((event) -> {searchField.search();});
        searchField.setStyle("-fx-pref-width: 260");
        
        urlField = new IWURLField(tabWebView, false);
        urlField.setOnAction((event) -> {urlField.loadPage();});
        urlField.setText(webAddress);
        urlField.loadPage();
        urlField.setStyle("-fx-pref-width: 300");
        
         tabWebView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                urlField.setText(tabWebView.getEngine().getLocation());
            }
        });
        
        newTabBtn = new Button("New Tab");
        newTabBtn.setOnAction((ActionEvent event) -> {
            Tab newTab = new IWInternetTab("www.smsu.edu", tabPane);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });
        
        hbox = new HBox();
        HBox.setHgrow(urlField, Priority.ALWAYS);
        hbox.setStyle("-fx-padding: 10px;-fx-alignment: baseline-center;");
        hbox.getChildren().addAll(newTabBtn, urlField, searchLabel, searchField);
        
        bPane = new BorderPane();
        bPane.setTop(hbox);
        bPane.setCenter(tabWebView);
        setContent(bPane);
        setClosable(true);
        setOnClosed((event) -> {onTabClosed();});
    }
    
    private void onTabClosed(){
        tabWebView.getEngine().load(null);
        System.gc();
    }
}

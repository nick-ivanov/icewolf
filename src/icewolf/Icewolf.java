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

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.scene.web.WebView;

public class Icewolf extends Application {
    @Override
    public void start(Stage primaryStage) {
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox();
        vbox.setStyle("-fx-padding: 10px;");
        javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(); 
        hbox.setStyle("-fx-padding: 10px;-fx-alignment: baseline-center;");
        
        
        Label httpLabel = new Label("http:// ");
        TextField urlTextField = new TextField(IWPropertyHelper.getProperty("default_homepage"));
        
        Separator inputBarSeparator = new Separator();
        inputBarSeparator.setOrientation(Orientation.VERTICAL);
        inputBarSeparator.setPadding(new Insets(0, 10, 0, 10));
        
        Label searchLabel = new Label("Search: ");
        TextField searchTextField = new TextField();
        
        TabPane tabPane = new TabPane();
        Tab tab = new Tab();
        tab.setText("Homepage");
        
        WebView mainWebView = new WebView();
        tab.setContent(mainWebView);
        tab.setClosable(true);
        tabPane.getTabs().add(tab);
        
        urlTextField.setStyle("-fx-pref-width: 300px;");
        searchTextField.setStyle("-fx-pref-width: 300px;");
        
        //root.setStyle("-fx-pref-width: 780; -fx-pref-height: 530;");
        mainWebView.setStyle("-fx-pref-width: 780; -fx-pref-height: 1530;");
        mainWebView.getEngine().load(java.net.URI.create
                ("http://" + urlTextField.getText()).toString());

        urlTextField.setOnAction((event) -> {
            mainWebView.getEngine().load(java.net.URI.create
                    ("http://" + urlTextField.getText()).toString());
        });
        
        searchTextField.setOnAction((event) -> {
            String rawSearch = "http://www.google.com/search?q=" + searchTextField.getText().toString();
            mainWebView.getEngine().load(rawSearch.replaceAll("\\s","+"));
        });
        
        IWMenuBar menuBar = new IWMenuBar(primaryStage, urlTextField, searchTextField, mainWebView);

        hbox.getChildren().addAll(httpLabel, urlTextField, inputBarSeparator, searchLabel, searchTextField);
        //vbox.getChildren().addAll(menuBar, hbox, mainWebView);
        vbox.getChildren().addAll(menuBar, hbox, tabPane);
        
        //primaryStage.setTitle("Super Simple Web Browser");
        primaryStage.setTitle(IWPropertyHelper.getProperty("default_window_title"));
        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}

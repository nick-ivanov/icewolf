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
        Label searchLabel = new Label("Search: ");
        
        Separator inputBarSeparator = new Separator();
        inputBarSeparator.setOrientation(Orientation.VERTICAL);
        inputBarSeparator.setPadding(new Insets(0, 10, 0, 10));
        
        TabPane tabPane = new TabPane();
        Tab tab = new Tab();
        
        WebView mainWebView = new WebView();
        tab.textProperty().bind(mainWebView.getEngine().titleProperty());
        tab.setContent(mainWebView);
        tab.setClosable(true);
        tabPane.getTabs().add(tab);
        
        IWSearchBox searchField = new IWSearchBox(mainWebView, 0);
        IWURLField urlField = new IWURLField(mainWebView, false);
        
        urlField.setStyle("-fx-pref-width: 300px;");
        searchField.setStyle("-fx-pref-width: 300px;");
        
        urlField.loadPage();
        //root.setStyle("-fx-pref-width: 780; -fx-pref-height: 530;");
        mainWebView.setStyle("-fx-pref-width: 780; -fx-pref-height: 1530;");

        urlField.setOnAction((event) -> {urlField.loadPage();});
        
        searchField.setOnAction((event) -> {searchField.search();});
        
        IWMenuBar menuBar = new IWMenuBar(primaryStage, urlField, searchField, mainWebView);

        hbox.getChildren().addAll(httpLabel, urlField, inputBarSeparator, searchLabel, searchField);
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

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
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Icewolf extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox();
        vbox.setStyle("-fx-padding: 10px;");
        javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(); 
        hbox.setStyle("-fx-padding: 10px;-fx-alignment: baseline-center;");
        
        IWMenuBar menuBar = new IWMenuBar(primaryStage);
        
        javafx.scene.control.Label httplabel =
                new javafx.scene.control.Label("http:// ");
        javafx.scene.web.WebView root = new javafx.scene.web.WebView();
        
        javafx.scene.control.TextField url =
                new javafx.scene.control.TextField("smsu.edu");
        url.setStyle("-fx-pref-width: 700px;");
        //root.setStyle("-fx-pref-width: 780; -fx-pref-height: 530;");
        root.setStyle("-fx-pref-width: 780; -fx-pref-height: 1530;");
        root.getEngine().load(java.net.URI.create
                ("http://" + url.getText()).toString());

        url.setOnAction((event) -> {
            root.getEngine().load(java.net.URI.create
                    ("http://" + url.getText()).toString());
        });

        hbox.getChildren().addAll(httplabel, url);
        vbox.getChildren().addAll(menuBar, hbox, root);
        
        primaryStage.setTitle("Super Simple Web Browser");
        primaryStage.setScene(new javafx.scene.Scene(vbox, 800, 600));
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

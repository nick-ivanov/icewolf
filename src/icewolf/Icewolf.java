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
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Icewolf extends Application 
{
    protected TabPane tabPane = new TabPane();
    
    @Override
    public void start(Stage primaryStage) 
    {
        IWSettingsController settings = new IWSettingsController();
        IWMenuBar menuBar = new IWMenuBar(primaryStage, tabPane);

        IWInternetTab iTab;
        
        String actualPage = "";
        
        if(settings.getUseHomepage()) {
            System.out.println("use homepage");
            actualPage = settings.getHomepage();
        } else {
            System.out.println("dont use homepage");
        }
        
        iTab = new IWInternetTab(actualPage, tabPane);
        tabPane.getTabs().add(iTab);
        
        VBox vbox = new VBox();
        vbox.setStyle("-fx-padding: 10px;");
        vbox.getChildren().addAll(menuBar, tabPane);
        Scene scene = new Scene(vbox, settings.getMinimumWidth(), settings.getMinimumHeight());
        
        primaryStage.setTitle(IWPropertyHelper.getProperty("default_window_title"));
        primaryStage.setMinWidth(settings.getMinimumWidth());
        primaryStage.setMinHeight(settings.getMinimumHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        IWApplicationHelper mHelper = IWApplicationHelper.getInstance();
        launch(args);
    } 
    
}

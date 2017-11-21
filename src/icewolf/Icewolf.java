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
        IWDatabaseHelper dbHelper = new IWDatabaseHelper();
        
        String category = dbHelper.addBookmarkCategory("Fun");
        dbHelper.addBookmarkCategory("Sran");
        
        System.out.println(dbHelper.getAllBookmarkCategories().toString());
        
        dbHelper.addBookmark("Google", "http://google.com", category);
        dbHelper.addBookmark("Twitter", "http://twitter.com", category);
        
        dbHelper.deleteCategoryWithBookmarks(category);
        
        System.out.println(dbHelper.getAllBookmarks().toString());
        System.out.println(dbHelper.getBookmarksInCategory(category).toString());

                
        dbHelper.updateSetting("foo", "car");
        
        System.out.println(dbHelper.getSetting("foo"));
        
        IWMenuBar menuBar = new IWMenuBar(primaryStage);
        IWInternetTab iTab = new IWInternetTab(IWPropertyHelper.getProperty("default_homepage"), tabPane);
        tabPane.getTabs().add(iTab);
        
        VBox vbox = new VBox();
        vbox.setStyle("-fx-padding: 10px;");
        vbox.getChildren().addAll(menuBar, tabPane);
        Scene scene = new Scene(vbox, 800, 600);
        
        primaryStage.setTitle(IWPropertyHelper.getProperty("default_window_title"));
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    } 
    
}

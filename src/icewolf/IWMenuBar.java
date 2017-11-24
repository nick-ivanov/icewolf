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

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class IWMenuBar extends MenuBar {
        TextField urlTextField, searchTextField;
        WebView webView;
        
        TabPane tabPane;
        
	final Menu main_menu = new Menu("Menu");
	final Menu help_menu = new Menu("Help");
	
        final MenuItem sample_module = new MenuItem("Sample Module");
        final MenuItem settings_module = new MenuItem("Settings");
        final MenuItem bookmarks_module = new MenuItem("Bookmarks");

	final MenuItem quit_item = new MenuItem("Quit");
        
	final MenuItem manual_item = new MenuItem("Manual");
	final MenuItem about_item = new MenuItem("About");
        
	public IWMenuBar(Stage stage, TabPane tabPane) {
                this.tabPane = tabPane;
                
		main_menu.getItems().addAll(sample_module, settings_module, bookmarks_module, quit_item);
		help_menu.getItems().addAll(manual_item, about_item);
		this.getMenus().addAll(main_menu, help_menu);
		
		setEvents(stage);
	}
	
	private void setEvents(Stage stage) {
		EventHandler<ActionEvent> MEHandler =
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				String name = ((MenuItem)ae.getTarget()).getText();
				
                                System.out.println("name: " + name);
                                
				if(name.equals("Quit")) { Platform.exit(); }
                                
				if(name.equals("Manual")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle(IWPropertyHelper.getProperty("application_name") + " Help");
					alert.setHeaderText("How to use " + IWPropertyHelper.getProperty("application_name"));
					String content = "The manual will be here if somebody pays me for this program.";
					alert.setContentText(content);
					alert.showAndWait();
				}
                                
				if(name.equals("About")) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("About " + IWPropertyHelper.getProperty("application_name"));
					alert.setHeaderText("About " + IWPropertyHelper.getProperty("application_name") + " Project");
					String content = "Copyright (C) 2017 '\n\n'Distributed under the Apache License, Version 2.0";
					alert.setContentText(content);
					alert.showAndWait();
				}
                                
                                if(name.equals("Sample Module")) {
                                    IWSampleModule sampleModule = new IWSampleModule(stage, urlTextField, searchTextField, webView);
                                }
                                
                                boolean settingsOpen = false;
                                
                                if(name.equals("Settings")) {
                                    for(Tab tab : tabPane.getTabs()) {
                                        if(tab.getText().equals(IWPropertyHelper.getPropertyWithSpaces("settings_tab_text", "`"))) {
                                            settingsOpen = true;
                                            tabPane.getSelectionModel().select(tab);
                                        }
                                    }
                                    
                                    if(!settingsOpen) {
                                        IWSettingsModule settingsModule = new IWSettingsModule(stage, tabPane);
                                        tabPane.getTabs().add(settingsModule);
                                        tabPane.getSelectionModel().select(settingsModule);
                                    }
                                    
                                }
                                
                                boolean bookmarksOpen = false;
                                
                                if(name.equals("Bookmarks")) {
                                    for(Tab tab : tabPane.getTabs()) {
                                        if(tab.getText().equals(IWPropertyHelper.getPropertyWithSpaces("bookmarks_tab_text", "`"))) {
                                            bookmarksOpen = true;
                                            tabPane.getSelectionModel().select(tab);
                                        }
                                    }
                                    
                                    if(!bookmarksOpen) {
                                        IWBookmarksModule bookmarksModule = new IWBookmarksModule(stage, tabPane);
                                        tabPane.getTabs().add(bookmarksModule);
                                        tabPane.getSelectionModel().select(bookmarksModule);
                                    }
                                    
                                }
			}
		};
		
		quit_item.setOnAction(MEHandler);
		manual_item.setOnAction(MEHandler);
		about_item.setOnAction(MEHandler);
                sample_module.setOnAction(MEHandler);
                settings_module.setOnAction(MEHandler);
                bookmarks_module.setOnAction(MEHandler);
	}
}
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
import javafx.stage.Stage;

public class IWMenuBar extends MenuBar {
	final Menu menu1 = new Menu("Menu");
	final Menu menu2 = new Menu("Help");
	
	final MenuItem quit_item = new MenuItem("Quit");
	final MenuItem manual_item = new MenuItem("Manual");
	final MenuItem about_item = new MenuItem("About");
	
	//HelpWindow hw = new HelpWindow();
	
	public IWMenuBar(Stage stage) {
		menu1.getItems().add(quit_item);
		menu2.getItems().addAll(manual_item, about_item);
		this.getMenus().addAll(menu1, menu2);
		
		setEvents(stage);
	}
	
	private void setEvents(Stage stage) {
		EventHandler<ActionEvent> MEHandler =
				new EventHandler<ActionEvent>() {
			public void handle(ActionEvent ae) {
				String name = ((MenuItem)ae.getTarget()).getText();
				
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
					String content = "Copyright (C) 2017 Nick Ivanov, Gregory Bowen, " +
							"Dylan Parsons\n\nDistributed under the Apache License, Version 2.0";
					alert.setContentText(content);
					alert.showAndWait();
				}
				
				// hw.setText(name + " chosen");
			}
		};
		
		quit_item.setOnAction(MEHandler);
		manual_item.setOnAction(MEHandler);
		about_item.setOnAction(MEHandler);
	}
}
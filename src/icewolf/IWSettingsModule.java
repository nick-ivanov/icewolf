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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class IWSettingsModule extends Tab {
    
    public IWSettingsModule(Stage stage, TabPane pane) {
        
        IWSettingsController settings = new IWSettingsController();
        
        VBox vbox = new VBox();
        vbox.setSpacing(15.0);
        vbox.setAlignment(Pos.BASELINE_LEFT);
        vbox.setPadding(new Insets(15.0));
        
        HBox hbox1 = new HBox();
        hbox1.setSpacing(15.0);
        hbox1.setAlignment(Pos.BASELINE_LEFT);
        
        
        HBox hbox2 = new HBox();
        hbox2.setSpacing(15.0);
        hbox2.setAlignment(Pos.BASELINE_LEFT);
        
        HBox hbox3 = new HBox();
        hbox3.setSpacing(15.0);
        hbox3.setAlignment(Pos.BASELINE_LEFT);
        
        HBox hbox4 = new HBox();
        hbox4.setSpacing(15.0);
        hbox4.setAlignment(Pos.BASELINE_LEFT);
        
        Label label1 = new Label("Homepage");
        Label label2 = new Label("Startup behavior");
        Label label3 = new Label("Search engine");
        
        TextField homepageTextField = new TextField();
        homepageTextField.setPrefColumnCount(32);
        homepageTextField.setText(settings.getHomepage());
        
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radio1 = new RadioButton();
        radio1.setText("Load homepage");
        radio1.setToggleGroup(toggleGroup);
        RadioButton radio2 = new RadioButton();
        radio2.setText("Use empty tab");
        radio2.setToggleGroup(toggleGroup);
        
        if(settings.getUseHomepage()) {
            radio1.setSelected(true);
            radio2.setSelected(false);
        } else {
            radio1.setSelected(false);
            radio2.setSelected(true);
        }
        
        ComboBox searchEngineComboBox = new ComboBox();
        searchEngineComboBox.getItems().addAll("Google", "Yahoo", "Bing", "DuckDuckGo", "Internet Archive", "Amazon");
        
        searchEngineComboBox.setValue(settings.getSearchEngine());
        
        Button saveButton = new Button("Save");
        Button cancelButton = new Button("Cancel");
        
        hbox1.getChildren().addAll(label1, homepageTextField);
        hbox2.getChildren().addAll(label2, radio1, radio2);
        hbox3.getChildren().addAll(label3, searchEngineComboBox);
        hbox4.getChildren().addAll(saveButton, cancelButton);
        
        vbox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4);
        
        this.setContent(vbox);
        this.setText(IWPropertyHelper.getPropertyWithSpaces("settings_tab_text", "`"));
        
        saveButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                settings.setHomepage(homepageTextField.getText());
                
                if(radio1.isSelected()) {
                    settings.setUseHomepage(true);
                } else if(radio2.isSelected()) {
                    settings.setUseHomepage(false);
                } else {
                    System.err.println("Fatal error [saveButton.setOnMouseClicked()].");
                    System.exit(1);
                }
                
                settings.setSearchEngine((String) searchEngineComboBox.getValue());
            }
        });
        
        final Tab tabToClose = this;
        
        cancelButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                pane.getTabs().remove(tabToClose);
            }
        });
        
    }
}

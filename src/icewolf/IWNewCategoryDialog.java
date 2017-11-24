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

import java.util.HashMap;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IWNewCategoryDialog {
    private ListView listView;
    private HashMap<Integer,String> hm;

    public IWNewCategoryDialog(ListView lv, HashMap<Integer,String> hm) {
        this.listView = lv;
        this.hm = hm;
    }
   
    public void go() {
        final Stage dialog = new Stage();
        dialog.setTitle("Create new bookmark category");

        dialog.initModality(Modality.NONE);
        dialog.initOwner((Stage) listView.getScene().getWindow());

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15));
        vbox.setSpacing(15);
        vbox.setAlignment(Pos.BASELINE_CENTER);
        
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(15));
        hbox1.setSpacing(15);
        hbox1.setAlignment(Pos.BASELINE_CENTER);
        
        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(15));
        hbox2.setSpacing(15);
        hbox2.setAlignment(Pos.BASELINE_CENTER);
        
        Label title = new Label("Create new bookmark category");
        title.setFont(new Font(24));
        
        Label label1 = new Label("Category name:");
        TextField categoryNameTextInput = new TextField();
        
        Button addButton = new Button("Add Category");
        addButton.setDisable(true);
        Button cancelButton = new Button("Cancel");
        
        hbox1.getChildren().addAll(label1, categoryNameTextInput);
        hbox2.getChildren().addAll(addButton, cancelButton);
        
        vbox.getChildren().addAll(title, hbox1, hbox2);
             
        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        categoryNameTextInput.setOnKeyReleased(new EventHandler() {
            @Override
            public void handle(Event event) {
                if(!categoryNameTextInput.getText().equals("")) {
                    addButton.setDisable(false);
                } else {
                    addButton.setDisable(true);
                }
            }
        });
               
        addButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        System.out.println("data: " + categoryNameTextInput.getText());
                        IWDatabaseHelper dbHelper = new IWDatabaseHelper();
                        dbHelper.addBookmarkCategory(categoryNameTextInput.getText());
                        updateBookmarkCategories();
                        dialog.close();
                    }
                });
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        dialog.close();
                    }
                });

        Scene dialogScene = new Scene(vbox, 400, 300);
        //dialogScene.getStylesheets().add("//style sheet of your choice");
        dialog.setScene(dialogScene);
        dialog.show();
    }


    private void updateBookmarkCategories() {
        IWDatabaseHelper dbHelper = new IWDatabaseHelper();
        
        listView.getItems().clear();
        int i = 0;
        for (IWBookmarkCategory cat : dbHelper.getAllBookmarkCategories()) {
            listView.getItems().add(cat.getName());
            hm.put(i, cat.getId());
        }
    }    
}

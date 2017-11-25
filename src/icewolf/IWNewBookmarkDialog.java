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

public class IWNewBookmarkDialog {

    private ListView listView;
    private HashMap<Integer, String> hm;
    private HashMap<Integer, String> namehm;
    private HashMap<Integer, String> urlhm;
    private HashMap<Integer, String> idhm;
    private ListView bookmarkListView;

    public IWNewBookmarkDialog(ListView lv, ListView lv1, HashMap<Integer, String> hm, HashMap<Integer, String> idhm, 
            HashMap<Integer, String> namehm, HashMap<Integer, String> urlhm) {
        this.listView = lv;
        this.bookmarkListView = lv1;
        this.hm = hm;
        this.namehm = namehm;
        this.urlhm = urlhm;
        this.idhm = idhm;
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

        HBox hbox3 = new HBox();
        hbox3.setPadding(new Insets(15));
        hbox3.setSpacing(15);

        hbox3.setAlignment(Pos.BASELINE_CENTER);

        Label title = new Label("Create bookmark in category '" + listView.getSelectionModel().getSelectedItem().toString() + "'");
        title.setFont(new Font(24));

        Label label1 = new Label("Bookmark name:");
        TextField bookmarkNameTextInput = new TextField();
        bookmarkNameTextInput.setPrefColumnCount(25);

        Label label2 = new Label("URL:");
        TextField urlTextInput = new TextField();
        urlTextInput.setPrefColumnCount(25);

        Button addButton = new Button("Add Bookmark");
        addButton.setDisable(true);
        Button cancelButton = new Button("Cancel");

        hbox1.getChildren().addAll(label1, bookmarkNameTextInput);
        hbox2.getChildren().addAll(label2, urlTextInput);
        hbox3.getChildren().addAll(addButton, cancelButton);

        vbox.getChildren().addAll(title, hbox1, hbox2, hbox3);

        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        bookmarkNameTextInput.setOnKeyReleased(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!bookmarkNameTextInput.getText().equals("")) {
                    addButton.setDisable(false);
                } else {
                    addButton.setDisable(true);
                }
            }
        });

        urlTextInput.setOnKeyReleased(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!urlTextInput.getText().equals("")) {
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
                System.out.println("data: " + bookmarkNameTextInput.getText());
                IWDatabaseHelper dbHelper = new IWDatabaseHelper();
                System.out.println("category: " + hm.get(listView.getSelectionModel().getSelectedIndex()));
                dbHelper.addBookmark(bookmarkNameTextInput.getText(), urlTextInput.getText(), hm.get(listView.getSelectionModel().getSelectedIndex()));
                updateBookmarks();
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

        Scene dialogScene = new Scene(vbox, 640, 480);
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
            i++;
        }
    }

    private void updateBookmarks() {
        IWDatabaseHelper dbHelper = new IWDatabaseHelper();
        bookmarkListView.getItems().clear();
        int i = 0;
        namehm.clear();
        urlhm.clear();
        idhm.clear();
        
        for (IWBookmark bmrk : dbHelper.getBookmarksInCategory(hm.get(listView.getSelectionModel().getSelectedIndex()))) {
            bookmarkListView.getItems().add(bmrk.getName() + " [" + bmrk.getUrl() + "]");
            namehm.put(i, bmrk.getName());
            urlhm.put(i, bmrk.getUrl());
            idhm.put(i, bmrk.getId());
            i++;
        }
    }
}

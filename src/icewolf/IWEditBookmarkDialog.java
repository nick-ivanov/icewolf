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

public class IWEditBookmarkDialog {

    private ListView listView;
    private HashMap<Integer, String> hm;
    private HashMap<Integer, String> hm1;
    private ListView bookmarkListView;
    private String name;
    private String url;
    private String id;
    private int catindex;

    public IWEditBookmarkDialog(ListView lv, ListView lv1, HashMap<Integer, String> hm, HashMap<Integer, String> hm1, String id, String name, String url, int catindex) {
        this.listView = lv;
        this.bookmarkListView = lv1;
        this.hm = hm;
        this.hm1 = hm1;
        this.name = name;
        this.url = url;
        this.id = id;
        this.catindex = catindex;
    }

    public void go() {
        final Stage dialog = new Stage();
        dialog.setTitle("Edit bookmark");

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

        Label title = new Label("Edit bookmark");
        title.setFont(new Font(24));

        Label label1 = new Label("Bookmark name:");
        TextField bookmarkNameTextInput = new TextField();
        bookmarkNameTextInput.setText(name);
        bookmarkNameTextInput.setPrefColumnCount(25);

        Label label2 = new Label("URL:");
        TextField urlTextInput = new TextField();
        urlTextInput.setText(url);
        urlTextInput.setPrefColumnCount(25);

        Button editButton = new Button("Edit Bookmark");
        editButton.setDisable(true);
        Button cancelButton = new Button("Cancel");

        hbox1.getChildren().addAll(label1, bookmarkNameTextInput);
        hbox2.getChildren().addAll(label2, urlTextInput);
        hbox3.getChildren().addAll(editButton, cancelButton);

        vbox.getChildren().addAll(title, hbox1, hbox2, hbox3);

        HBox dialogHbox = new HBox(20);
        dialogHbox.setAlignment(Pos.CENTER);

        bookmarkNameTextInput.setOnKeyReleased(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!bookmarkNameTextInput.getText().equals("")) {
                    editButton.setDisable(false);
                } else {
                    editButton.setDisable(true);
                }
            }
        });

        urlTextInput.setOnKeyReleased(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (!urlTextInput.getText().equals("")) {
                    editButton.setDisable(false);
                } else {
                    editButton.setDisable(true);
                }
            }
        });

        editButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                System.out.println("data: " + bookmarkNameTextInput.getText());
                IWDatabaseHelper dbHelper = new IWDatabaseHelper();
                System.out.println("category: " + hm.get(listView.getSelectionModel().getSelectedIndex()));

                System.out.println("ARG1: " + hm1.get(bookmarkListView.getSelectionModel().getSelectedIndex()));
                System.out.println("ALT ARG1: " + id);
                System.out.println("ARG2: " + bookmarkNameTextInput.getText());
                System.out.println("ARG3: " + urlTextInput.getText());
                
                dbHelper.updateBookmark(id, bookmarkNameTextInput.getText(), urlTextInput.getText());
                
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
        for (IWBookmark bmrk : dbHelper.getBookmarksInCategory(hm.get(catindex))) {
            bookmarkListView.getItems().add(bmrk.getName() + " [" + bmrk.getUrl() + "]");
        }
    }
}

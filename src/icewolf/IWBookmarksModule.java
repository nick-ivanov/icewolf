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

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class IWBookmarksModule extends Tab {

    private ListView listView1;
    private ListView listView2;
    private IWDatabaseHelper dbHelper;
    private HashMap<Integer, String> catIds = new HashMap<>();

    public IWBookmarksModule(Stage stage, TabPane pane) {
        dbHelper = new IWDatabaseHelper();

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15.0));
        hbox.setSpacing(15.0);

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(15.0));
        vbox.setSpacing(15.0);

        Button newCategoryButton = new Button("New Category");
        newCategoryButton.setPrefWidth(200);

        Button editCategoryButton = new Button("Edit Category");
        editCategoryButton.setPrefWidth(200);

        Button deleteCategoryButton = new Button("Delete Category");
        deleteCategoryButton.setPrefWidth(200);

        Button newBookmarkButton = new Button("New Bookmark in Category");
        newBookmarkButton.setPrefWidth(200);

        Button editBookmarkButton = new Button("Edit Bookmark");
        editBookmarkButton.setPrefWidth(200);

        Button deleteBookmarkButton = new Button("Delete Bookmark");
        deleteBookmarkButton.setPrefWidth(200);

        Button closeButton = new Button("Close");
        closeButton.setPrefWidth(200);

        vbox.getChildren().addAll(newCategoryButton, editCategoryButton,
                deleteCategoryButton, newBookmarkButton, editBookmarkButton,
                deleteBookmarkButton, closeButton);

        listView1 = new ListView();
        listView1.setPrefWidth(200.0);

        listView2 = new ListView();
        listView2.setPrefWidth(stage.getScene().getWidth() - 440.0);

        updateBookmarkCategories();

        hbox.getChildren().addAll(vbox, listView1, listView2);

        this.setContent(hbox);
        this.setText(IWPropertyHelper.getPropertyWithSpaces("bookmarks_tab_text", "`"));

        listView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listView2.getItems().clear();
                for (IWBookmark bmrk : dbHelper.getBookmarksInCategory(catIds.get(listView1.getSelectionModel().getSelectedIndex()))) {
                    listView2.getItems().add(bmrk.getName() + "[" + bmrk.getUrl() + "]");
                }
            }
        });

        stage.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
                listView2.setPrefWidth(stage.getScene().getWidth() - 440.0);
            }
        });

        newCategoryButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                IWNewCategoryDialog dialog = new IWNewCategoryDialog(listView1, catIds);
                dialog.go();
                updateBookmarkCategories();
            }
        });

        editCategoryButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {

            }
        });

        deleteCategoryButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {

            }
        });

        newBookmarkButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {

            }
        });

        editBookmarkButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {

            }
        });

        deleteBookmarkButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {

            }
        });

        final Tab tabToClose = this;

        closeButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                pane.getTabs().remove(tabToClose);
            }
        });
    }

    private void updateBookmarkCategories() {
        listView1.getItems().clear();
        int i = 0;
        for (IWBookmarkCategory cat : dbHelper.getAllBookmarkCategories()) {
            listView1.getItems().add(cat.getName());
            catIds.put(i, cat.getId());
        }
    }

}

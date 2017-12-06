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
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private HashMap<Integer, String> bIds = new HashMap<>();
    private HashMap<Integer, String> bNames = new HashMap<>();
    private HashMap<Integer, String> bUrls = new HashMap<>();
    private TabPane tabPane;
    private int catindex;

    public IWBookmarksModule(Stage stage, TabPane pane) {
        dbHelper = new IWDatabaseHelper();
        tabPane = pane;

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
        editCategoryButton.setDisable(true);

        Button deleteCategoryButton = new Button("Delete Category");
        deleteCategoryButton.setPrefWidth(200);
        deleteCategoryButton.setDisable(true);

        Button newBookmarkButton = new Button("New Bookmark");
        newBookmarkButton.setPrefWidth(200);
        newBookmarkButton.setDisable(true);

        Button editBookmarkButton = new Button("Edit Bookmark");
        editBookmarkButton.setPrefWidth(200);
        editBookmarkButton.setDisable(true);

        Button deleteBookmarkButton = new Button("Delete Bookmark");
        deleteBookmarkButton.setPrefWidth(200);
        deleteBookmarkButton.setDisable(true);

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
        updateBookmarks();

        hbox.getChildren().addAll(vbox, listView1, listView2);

        this.setContent(hbox);
        this.setText(IWPropertyHelper.getPropertyWithSpaces("bookmarks_tab_text", "`"));

        listView1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (listView1.getSelectionModel().isEmpty()) {
                    editCategoryButton.setDisable(true);
                    newBookmarkButton.setDisable(true);
                    deleteCategoryButton.setDisable(true);
                } else {
                    editCategoryButton.setDisable(false);
                    newBookmarkButton.setDisable(false);
                    deleteCategoryButton.setDisable(false);
                    catindex = listView1.getSelectionModel().getSelectedIndex();
                }

                updateBookmarks();
            }
        });

        listView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                int ind = listView2.getSelectionModel().getSelectedIndex();

                if (listView2.getSelectionModel().isEmpty()) {
                    editBookmarkButton.setDisable(true);
                    deleteBookmarkButton.setDisable(true);

                } else {
                    editBookmarkButton.setDisable(false);
                    deleteBookmarkButton.setDisable(false);
                }

                if (event.getClickCount() == 2) {
                    updateBookmarks();

                    String url = bUrls.get(ind);

                    
                    if (url.substring(0, 8).equals("https://")) {
                        url = url.substring(8);
                    } else if (url.substring(0, 7).equals("http://")) {
                        url = url.substring(7);
                    }

                    System.out.println("Double click detected at: " + url);

                    IWInternetTab tab = new IWInternetTab(url, tabPane);
                    tabPane.getTabs().add(tab);
                }
            }
        });

        stage.getScene().widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
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
                IWEditCategoryDialog dialog = new IWEditCategoryDialog(
                        listView1, catIds.get(listView1.getSelectionModel().getSelectedIndex()),
                        listView1.getSelectionModel().getSelectedIndex(), catIds);
                dialog.go();
                updateBookmarkCategories();
            }
        });

        deleteCategoryButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Deleting a category");
                alert.setHeaderText("Are you sure you want to delete category '"
                        + listView1.getSelectionModel().getSelectedItem().toString() + "'?");
                alert.setContentText("All bookmarks in the category will be cascade-deleted!");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    IWDatabaseHelper dbHelper = new IWDatabaseHelper();
                    dbHelper.deleteCategoryWithBookmarks(catIds.get(listView1.getSelectionModel().getSelectedIndex()));
                    updateBookmarkCategories();
                    editCategoryButton.setDisable(true);
                    newBookmarkButton.setDisable(true);
                    deleteCategoryButton.setDisable(true);
                    updateBookmarks();
                }
                updateBookmarkCategories();
            }
        });

        newBookmarkButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                IWNewBookmarkDialog dialog = new IWNewBookmarkDialog(listView1, listView2, catIds, bIds, bNames, bUrls);
                dialog.go();
                updateBookmarks();
            }
        });

        editBookmarkButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                int ind = listView2.getSelectionModel().getSelectedIndex();
                IWEditBookmarkDialog dialog = new IWEditBookmarkDialog(listView1, listView2,
                        catIds, bIds, bIds.get(ind), bNames.get(ind), bUrls.get(ind), catindex);

                dialog.go();
                updateBookmarks();
            }
        });

        deleteBookmarkButton.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Deleting a bookmark");
                alert.setHeaderText("Are you sure you want to delete bookmark '"
                        + listView2.getSelectionModel().getSelectedItem().toString() + "'?");
                alert.setContentText("The bookmark will be deleted permanently!");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    IWDatabaseHelper dbHelper = new IWDatabaseHelper();
                    dbHelper.deleteBookmark(bIds.get(listView2.getSelectionModel().getSelectedIndex()));
                    deleteBookmarkButton.setDisable(true);
                    updateBookmarks();
                }
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
        catIds.clear();
        int i = 0;
        for (IWBookmarkCategory cat : dbHelper.getAllBookmarkCategories()) {
            listView1.getItems().add(cat.getName());
            catIds.put(i, cat.getId());
            i++;
        }
    }

    private void updateBookmarks() {
        listView2.getItems().clear();
        bIds.clear();
        bNames.clear();
        bUrls.clear();

        int i = 0;
        for (IWBookmark bmrk : dbHelper.getBookmarksInCategory(catIds.get(listView1.getSelectionModel().getSelectedIndex()))) {
            listView2.getItems().add(bmrk.getName() + " [" + bmrk.getUrl() + "]");
            bIds.put(i, bmrk.getId());
            bNames.put(i, bmrk.getName());
            bUrls.put(i, bmrk.getUrl());
            i++;
        }

        System.out.println("BURLS: " + bUrls.toString());
    }

}

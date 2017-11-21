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

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class IWDownloadBox 
{
    private URL url;
    private File filePath;
    private String fileName;
    private long contentLength;
    private InputStream inputStream;
    private Thread downloadThread;
    private IWDownloadService dwnldSrvc = new IWDownloadService();
    
    IWDownloadBox(URL url, long contentLength, File filePath, String fileName, InputStream input)
    {
        this.url = url;
        this.contentLength = contentLength;
        this.filePath = filePath;
        this.fileName = fileName;
        this.inputStream = input;
        
        Stage subStage = new Stage();
        subStage.setTitle("Download " + fileName);
        
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(4);
        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        
        Label fileURLLabel = new Label("File URL:");
        grid.add(fileURLLabel, 0, 1);
        
        Label fileURLText = new Label(url.toString());
        grid.add(fileURLText, 1, 1);
        
        Label fileNameLabel = new Label("File Name:");
        grid.add(fileNameLabel, 0, 2);
        
        Label fileNameText = new Label(fileName);
        grid.add(fileNameText, 1, 2);
        
        Label fileSizeLabel = new Label("File Size (bytes):");
        grid.add(fileSizeLabel, 0, 3);
        
        Label fileSizeText = new Label(String.valueOf(contentLength));
        grid.add(fileSizeText, 1, 3);
        
        Label fileSaveLabel = new Label("Saving to:");
        grid.add(fileSaveLabel, 0, 4);
        
        Label fileSaveText = new Label(filePath.getPath());
        grid.add(fileSaveText, 1, 4);
        
        Button dwnldBtn = new Button("Download");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(dwnldBtn);
        grid.add(hbBtn, 1, 5);
        
        Label fileProgressLabel = new Label("Progress:");
        fileProgressLabel.setVisible(false);
        grid.add(fileProgressLabel, 0, 6);
         
        final ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        grid.add(progressIndicator, 1, 6);
        
        
        dwnldBtn.setOnAction(new EventHandler<ActionEvent>() 
        {
            @Override
            public void handle(ActionEvent e) 
            {
                fileProgressLabel.setVisible(true);
                progressIndicator.setVisible(true);
                dwnldBtn.setVisible(false);
                dwnldSrvc.initService(url, filePath, fileName, contentLength, inputStream);
                dwnldSrvc.start();
                progressIndicator.progressProperty().bind(dwnldSrvc.getTask().progressProperty());
            }
        });
        
        Scene scene = new Scene(grid, 500, 150);
        subStage.setResizable(false);
        subStage.setScene(scene);
        subStage.setOnCloseRequest(new EventHandler<WindowEvent>()
        {
            public void handle(WindowEvent we) 
            {
                dwnldSrvc.stopDownload();
                dwnldSrvc.cancel();
                System.out.println("Download Stopped.");
            }
        }); 
        subStage.show();
    } 
}

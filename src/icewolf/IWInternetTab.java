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
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebView;

public class IWInternetTab extends Tab 
{

    private Label searchLabel;
    private WebView tabWebView;
    private IWSearchBox searchField;
    private IWURLField urlField;
    private Button newTabBtn;
    private HBox hbox;
    private BorderPane bPane;
    
    public IWInternetTab(String webAddress, TabPane tabPane) 
    {
        
        searchLabel = new Label("Search: ");
    
        tabWebView = new WebView();
        //textProperty().bind(tabWebView.getEngine().titleProperty());
        this.setText("Empty Tab");

        tabWebView.setStyle("-fx-pref-width: 780; -fx-pref-height: 1530;");
        
        searchField = new IWSearchBox(tabWebView, 0);
        searchField.setOnAction((event) -> {searchField.search();});
        searchField.setStyle("-fx-pref-width: 260");
        
        urlField = new IWURLField(tabWebView, false, this);

        urlField.setOnAction((event) -> { urlField.loadPage(); });

        urlField.setText(webAddress);

        urlField.loadPage();
       
        urlField.setStyle("-fx-pref-width: 300");
        
        tabWebView.getEngine().locationProperty().addListener((observable, oldURL, newURL) -> 
        {
            urlField.setText(newURL);
            downloadFileIfAvailable(urlField.getText());          
        });
                
        newTabBtn = new Button("New Tab");
        
        newTabBtn.setOnAction((ActionEvent event) -> {
            Tab newTab = new IWInternetTab("www.smsu.edu", tabPane);
            tabPane.getTabs().add(newTab);
            tabPane.getSelectionModel().select(newTab);
        });
        
        hbox = new HBox(6);
        HBox.setHgrow(urlField, Priority.ALWAYS);
        hbox.setStyle("-fx-padding: 10px;-fx-alignment: baseline-center;");
        hbox.getChildren().addAll(newTabBtn, urlField, searchLabel, searchField);
        
        bPane = new BorderPane();
        bPane.setTop(hbox);
        bPane.setCenter(tabWebView);
        setContent(bPane);
        setClosable(true);
        setOnClosed((event) -> {onTabClosed();});
    }
    
    private void onTabClosed(){
        tabWebView.getEngine().load(null);
        System.gc();
    }
    
    private void downloadFileIfAvailable(String pageURL)
    {
        try
        {
            HttpURLConnection httpConn;
            URL url = new URL(pageURL);
            long contentLength;
            String fileName = "";
            InputStream inputStream;
            httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_FORBIDDEN)
            {
                
                contentLength = httpConn.getContentLength();
                if(contentLength == -1)
                {
                    httpConn.disconnect();
                    return;
                }
                String contentType = httpConn.getContentType();
                if(contentType.contains("text/html"))
                {
                    httpConn.disconnect();
                    return;
                }
                String disposition = httpConn.getHeaderField("Content-Disposition");
                if (disposition != null) 
                {
                    System.out.println("Dis = " + disposition);
                    int index = disposition.indexOf("filename=");
                    if (index > 0) 
                    {
                        fileName = "";
                        
                        for(int i=index+9; i<disposition.length();i++)
                        {
                            if(disposition.charAt(i)==';')
                            {
                                break;
                            }
                            else if(disposition.charAt(i)!='"')
                            {
                                fileName += disposition.charAt(i);
                            }    
                        }
                    }
                }
                else
                {
                    fileName = pageURL.substring(pageURL.lastIndexOf("/") + 1,pageURL.length());
                }
                if(Math.abs(fileName.lastIndexOf(".") - fileName.length())> 4)
                {
                    httpConn.disconnect();
                    return;
                }
                if (!IWApplicationHelper.getInstance().getUrlQueue().contains(fileName)
                        && !contentType.contains("text") && fileName.contains(".") && contentLength > 1) 
                {
                    IWApplicationHelper.getInstance().getUrlQueue().add(fileName);
                    // System.out.println(contentType);
                    // Download File
                    File file = new File(System.getProperty("user.home") + "/Downloads/IWDownloads/");
                    try 
                    {
                        if (!file.exists()) 
                        {
                            file.mkdir();
                        }
                        File download = new File(file + "/" + fileName);
                        inputStream = responseCode == 200 ? httpConn.getInputStream() : httpConn.getErrorStream();

                        new IWDownloadBox(url, contentLength, file, fileName, inputStream);
                    }
                    catch (Exception e) 
                    {
                        e.printStackTrace();
                    }
                } 
                else 
                {
                    httpConn.disconnect();
                }
            }
            else
            {
                System.out.println("Response is " + responseCode);
            }
                /*else 
                {
                    fileName = pageURL.substring(pageURL.lastIndexOf("/") + 1,pageURL.length());
                }*/
                //System.out.println(contentType);
                
        }
        catch(Exception e)
        {
            
        }
    }
    
    
}

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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class IWDownloadService extends Service
{
    private URL url;
    private File filePath;
    private String fileName;
    private long contentLength;
    private InputStream inputStream;
    private Task downloadTask;
    private FileOutputStream outputStream;
    
    @Override
    protected Task createTask() 
    {
        downloadTask = new Task<Void>() 
        {
            @Override
            public Void call()
            {
                try 
                {
                    outputStream = new FileOutputStream(filePath.getAbsolutePath() + 
                            File.separator + fileName);
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;
                    long totalBytesRead = 0;
                    int percentCompleted = 0;

                    while ((bytesRead = inputStream.read(buffer)) != -1) 
                    {
                        outputStream.write(buffer, 0, bytesRead);
                        totalBytesRead += bytesRead;
                        percentCompleted = (int) (totalBytesRead * 100 / contentLength);
                        updateProgress(percentCompleted, 100);
                    }
                    outputStream.close();
                } 
                catch (IOException ex) 
                {
                    
                }
                return null;
            }
        };
       return downloadTask;
    }
    
    public void initService(URL url, File filePath, String fileName, long content, InputStream in)
    {
        this.contentLength = content;
        this.url = url;
        this.fileName = fileName;
        this.filePath = filePath;
        this.inputStream = in;
    }
    
    public Task getTask()
    {
        return downloadTask;
    }
    
    public void stopDownload()
    {
        try 
        {
            if(outputStream!=null)
            {
                outputStream.close();
            }
        } 
        catch (IOException ex) 
        {
            
        }
    }
    
}

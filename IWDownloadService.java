/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IWDatabaseHelper {
    private final String DATABASE_NAME = "icewolf.db";
    private Connection connection = null;
    
    public IWDatabaseHelper() {
        
        
        File dbFile = new File(DATABASE_NAME);
        
        if(dbFile.exists()) {
            connect();
        } else {
            connect();
            init();
            System.out.println("Database does not exist.");
        }
    }
    
    private void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
            System.out.println("Database connection: SUCCESS!");
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [connect()]: " + ex.getMessage());
            System.exit(1);
        }
    }
    
    private void init() {
        try {
            Statement statement = connection.createStatement();
        
            String sql = "CREATE TABLE IF NOT EXISTS bookmarks (\n"
                + "	bookmarkId text PRIMARY KEY,\n"
                + "	bookmarkName text NOT NULL,\n"
                + "	bookmarkUrl text NOT NULL,\n"
                + "	bookmarkCategory text NOT NULL\n"    
                + ");";
        
            statement.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS bookmark_categories (\n"
                + "	categoryId text PRIMARY KEY,\n"
                + "	categoryName text NOT NULL\n"  
                + ");";
            
            statement.execute(sql);
            
            sql = "CREATE TABLE IF NOT EXISTS settings (\n"
                + "	parameterName text PRIMARY KEY,\n"
                + "	value text NOT NULL\n"
                + ");";
            
            statement.execute(sql);
            
            addSetting("homepage", IWPropertyHelper.getProperty("default_homepage"));
            addSetting("search_engine", "duckduckgo");
            addSetting("minimum_width", "800");
            addSetting("minimum_height", "600");
            
            addBookmarkCategory("Default");
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [init()]: " + ex.getMessage());
            System.exit(1);
        }
    }
    
    public String addBookmarkCategory(String name) {
        String categoryId = java.util.UUID.randomUUID().toString();
        
        try {
            Statement statement = connection.createStatement();
            
            String sql = "INSERT INTO bookmark_categories VALUES (\n"
                + "'" + categoryId + "', " + "'" + name + "');";
            
            statement.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [addBookmarkCategory()]: " + ex.getMessage());
            System.exit(1);
        }
        
        return categoryId;
    }
    
    
    public String addBookmark(String name, String url, String category) {
        String bookmarkId = java.util.UUID.randomUUID().toString();
        
        try {
            Statement statement = connection.createStatement();
            
            String sql = "INSERT INTO bookmarks VALUES (\n"
                + "'" + bookmarkId + "', '" + name + "', '" + url + "', '" + category + "');";
            
            statement.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [addBookmark()]: " + ex.getMessage());
            System.exit(1);
        }
        
        return bookmarkId;
    }
    
    public void updateSetting(String key, String value) {        
        try {
            Statement statement = connection.createStatement();
            
            String sql = "UPDATE settings SET \n"
                + "value='" + value + "' WHERE parameterName='" + key + "';";
            
            statement.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [updateSetting()]: " + ex.getMessage());
            System.exit(1);
        }        
    }
    
    private void addSetting(String key, String value) {        
        try {
            Statement statement = connection.createStatement();
            
            String sql = "INSERT INTO settings VALUES (\n"
                + "'" + key + "', '" + value + "');";
            
            statement.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [addSetting()]: " + ex.getMessage());
            System.exit(1);
        }        
    }
    
    public String getSetting(String key) {   
        String setting = "";
        
        try {
            Statement statement = connection.createStatement();
            
            String sql = "SELECT value FROM settings WHERE (\n"
                + "parameterName='" + key + "');";
            
            ResultSet result = statement.executeQuery(sql);
            result.next();
            setting = result.getString(1);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [getSetting()]: " + ex.getMessage());
            System.exit(1);
        }
        
        return setting;
    }
    
    ArrayList<IWBookmarkCategory> getAllBookmarkCategories() {
        ArrayList<IWBookmarkCategory> categories = new ArrayList<>();
        
        try {
            Statement statement = connection.createStatement();
            
            String sql = "SELECT categoryId, categoryName FROM bookmark_categories;";
            
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) {
                categories.add(new IWBookmarkCategory(result.getString(1), result.getString(2)));
            }
            
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [getAllBookmarkCategories()]: " + ex.getMessage());
            System.exit(1);
        }
        
        return categories;
    }
    
    ArrayList<IWBookmark> getAllBookmarks() {
        ArrayList<IWBookmark> bookmarks = new ArrayList<>();
        
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT bookmarkId, bookmarkName, bookmarkUrl, bookmarkCategory FROM bookmarks;";
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) {
                bookmarks.add(new IWBookmark(result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
            }
            
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [getAllBookmarks()]: " + ex.getMessage());
            System.exit(1);
        }
        
        return bookmarks;
    }
    
    ArrayList<IWBookmark> getBookmarksInCategory(String category) {
        ArrayList<IWBookmark> bookmarks = new ArrayList<>();
        
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT bookmarkId, bookmarkName, bookmarkUrl, bookmarkCategory "
                    + " FROM bookmarks WHERE bookmarkCategory='" + category + "';";
            ResultSet result = statement.executeQuery(sql);
            
            while(result.next()) {
                bookmarks.add(new IWBookmark(result.getString(1), result.getString(2), result.getString(3), result.getString(4)));
            }
            
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [getBookmarksInCategory()]: " + ex.getMessage());
            System.exit(1);
        }
        
        return bookmarks;
    }
    
    public void deleteBookmark(String id) {        
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM bookmarks WHERE bookmarkId='" + id + "';";
            
            statement.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [deleteBookmark()]: " + ex.getMessage());
            System.exit(1);
        }
    }

    public void deleteCategoryWithBookmarks(String id) {        
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM bookmarks WHERE bookmarkCategory='" + id + "';";
            statement.execute(sql);
            
            sql = "DELETE FROM bookmark_categories WHERE categoryId='" + id + "';";
            statement.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem [deleteCategoryWithBookmarks()]: " + ex.getMessage());
            System.exit(1);
        }
    }
    
}

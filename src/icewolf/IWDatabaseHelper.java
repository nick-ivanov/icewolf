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
import java.sql.SQLException;
import java.sql.Statement;

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
            System.err.println("Fatal database problem: " + ex.getMessage());
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
                + "	value text NOT NULL,\n"
                + "	defaultValue text NOT NULL\n"
                + ");";
            
            statement.execute(sql);
        } catch (SQLException ex) {
            System.err.println("Fatal database problem: " + ex.getMessage());
            System.exit(1);
        }
    }
    
    
}

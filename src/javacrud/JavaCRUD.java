/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacrud;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author lserd
 */
public class JavaCRUD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String dbURL="jdbc:sqlserver://192.168.56.101:1433;databaseName=TestJavaDB";
        String username = "sa";
        String password = "Jurras1c";
        String sql;
        
        //Part 1: Connecting to the database
        try (Connection conn = DriverManager.getConnection(dbURL,username,password))
        {
            if(conn != null) 
            {
                System.out.println("Connected");
                
                
                //Part 2:  JDBC Execute INSERT Statement example
                sql = "INSERT INTO Users (username, password, fullname, email) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, "bill");
                statement.setString(2,"secretpass");
                statement.setString(3,"Bill Gates");
                statement.setString(4,"bill.gates@microsoft.com");
                
                int rowsInserted = statement.executeUpdate();
      
                if(rowsInserted > 0) 
                {
                    System.out.println("A new user was inserted successfully!");
                }
                
                //Part 3: JDBC Executing INSERT Statement Example
                sql = "SELECT * FROM users";
                Statement statement2 = conn.createStatement();
                ResultSet result =  statement2.executeQuery(sql);
                
                int count=0;
                
                while(result.next())
                {
                    String name=result.getString(2);
                    String pass=result.getString(3);
                    String fullname = result.getString("fullname");
                    String email = result.getString("email");
                    
                    String output = "User #%d: %s - %s - %s - %s";
                    System.out.println(String.format(output, ++count, name, pass, fullname, email));
                }
                
                
                //Part 4:  JDBC Executing UPDATE Statement example
                sql = "UPDATE users SET password=?, fullname=?, email=? WHERE username=?";
                PreparedStatement statement3 = conn.prepareStatement(sql);
                statement3.setString(1, "123456789");
                statement3.setString(2, "William Henry Bill Gates");
                statement3.setString(3, "bill.gates@microsoft.com");
                statement3.setString(4, "bill");
                
                int rowsUpdated = statement3.executeUpdate();
                
                if(rowsUpdated > 0)
                {
                    System.out.println("An existing user was updated successfully!");
                }
                
                //Part 5:  JDBC Execute DELETE Statement example
                sql = "DELETE from users WHERE username=?";
                PreparedStatement statement4 = conn.prepareStatement(sql);
                statement4.setString(1,"bill");
                
                int rowsDeleted = statement4.executeUpdate();
                
                if (rowsDeleted > 0)
                {
                    System.out.println("A user was deleted successfully!");
                }
                
                
                System.out.println("Disconnected");
            }
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }
    
}

/*f
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package databasemanager;

import java.sql.*;

/**
 * @author Mariana
 */
public class DatabaseManager {

private String databaseStringConnection;
private String databaseUser;
private String databaseUserPassword;
private Connection databaseConnection;
private boolean isConnectionWorking = true;
private String connectionErrorMessage;
private String tableSpacing = "%-50s";

    //Gets the private property value
    public boolean getIsConnectionWorking(){
        return isConnectionWorking;
    } 
    
    //Gets the private property value
     public String getConnectionErrorMessage(){
        return connectionErrorMessage;
    }   
    
    //Class constructor receiving the parameters to connect with the database
    public DatabaseManager(String databaseStringConnection, String databaseUser, String databaseUserPassword){
        //Sets the variables values. Use the "this" to identify that the variable receiving the value is the one declared in the class
        this.databaseStringConnection= databaseStringConnection;
        this.databaseUser= databaseUser;
        this.databaseUserPassword = databaseUserPassword;
        
        //Calls the database connection method
        makeDatabaseConnection();
    }
    
    private void makeDatabaseConnection(){
        //Code inside try catch block to avoid system interruption in case of exception
        try{
          //creates database connection with the properties given
          Class.forName("com.mysql.cj.jdbc.Driver");
          databaseConnection = DriverManager.getConnection(databaseStringConnection, databaseUser, databaseUserPassword);
        }
        catch(SQLException ex){
            //Sets the error and message into the variables
            connectionErrorMessage = "SQL Error: " + ex.getMessage();
            isConnectionWorking = false;
        }
        catch(ClassNotFoundException ex){
            //Sets the error and message into the variables
            connectionErrorMessage = "SQL Error: " + ex.getMessage();
            isConnectionWorking = false;
        }
    }
    
    public void ExecuteSelectCommandAndPrint(String query, String[] headerColumns){
        try {
            //Calls the method that search into the database
            ResultSet resultData = ExecuteSelectCommand(query);

            //Checks if the result set is not null
            if (resultData.isBeforeFirst()) {
                //Calls the print table method
                printTableHeader(headerColumns);

                //Loop that goes through all the rows returned
                while (resultData.next()) {
                    //Calls the method that prints the row
                    printTableRow(resultData, headerColumns);
                }
            } else {
                //Returns a message to the user if no result is found
                System.out.println("No records found for search.");
            }
        } catch (SQLException e) {
            //Returns a message in case of error
            System.out.println("ERROR " + e.toString());
        }
        
    }
    
    public ResultSet ExecuteSelectCommand(String query){
        try{
            //Calls the method that execute the query inside the database
            Statement  statement = databaseConnection.createStatement();
            return statement.executeQuery(query); 
        }
        catch(Exception ex){
            //Prints the error message
            System.out.println("An error happen trying to execute the search in the database.");
            System.out.println("Error message:" + ex.toString());
            return null;
        }
    }

    private void printTableHeader(String[] headerColumns) {
        //Using StringBuilder, another method to concat Strings
        StringBuilder header = new StringBuilder("\n");

        //Loop for each element of the array with the column name
        for (String columnName : headerColumns) {
            //Uses the method append to concatenate the formatted text into the header variable
            header.append(String.format(tableSpacing, columnName));
        }
        //Prints the header
        System.out.println(header.toString());
    }    
    
    private void printTableRow(ResultSet resultSet, String[] headerColumns){
        try{
            //Using StringBuilder, another method to concat Strings
            StringBuilder row = new StringBuilder();

            //Loop for each element of the array with the column name. Uses the header to print the elements in order
            for (String columnName : headerColumns) {
                //Uses the column name to get the specific column of the resultset
                row.append(String.format(tableSpacing, resultSet.getString(columnName)));
            }
            // Prints the row 
            System.out.println(row.toString());            
        }
        catch(Exception ex){
            //Prints the error message
            System.out.println("An error happen trying to print the search result.");
            System.out.println("Error message:" + ex.toString());
        }       
    }
}

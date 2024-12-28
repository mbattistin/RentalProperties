/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalca.marianabattistin;

import databasemanager.DatabaseManager;
import inpututilities.InputUtilities;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mariana
 */

//This class was made to use in the main class. So the functions don't need to be all static.
public class MainControllerManager {
    //Creates a new instance for the InputUtilities library to verify user inputs
    private InputUtilities inputUtilities = new InputUtilities();
    //Creates a new instance for the DatabaseManager library to connect to the database
    private DatabaseManager databaseManager = new DatabaseManager("jdbc:mysql://localhost:3306/dream_house_database","root","123456");
    
    //This method is static to be called in the Main
    public static void ExecuteMain(){
        //Doing an instance of the own class, it is possible to use this new object to call non-static methods
        MainControllerManager mainControllerManager = new MainControllerManager();
        //Checks if the database connection is working
        if(mainControllerManager.databaseManager.getIsConnectionWorking()){
            //Call the method that prints the main menu
            mainControllerManager.showMainMenuOptions();
        }
        //If the database connection is not working, there is no point in keep the system working. So a message is shown to the user
        else{
            System.out.println("Sorry, it happen an error connecting to the database, the system will not be able to operate.");
            System.out.println(mainControllerManager.databaseManager.getConnectionErrorMessage());
        }
    }
    
    public  void showMainMenuOptions(){
        int selectedMenuOption = 0;
        
        //display menu options
        System.out.println("==========================================================================================");
        System.out.println("|                                     System Menu                                        |");
        System.out.println("==========================================================================================");
        System.out.println("| 1 – List all properties rented out by all clients whose name contains a keyword/letter |");
        System.out.println("| 2 – List all the properties for a specific owner                                       |");
        System.out.println("| 3 – List all the properties which have a certain rent amount                           |");
        System.out.println("| 4 – List all properties which the address contains a keyword/letter                    |");
        System.out.println("| 5 – List all clients who have properties rented out for a specific duration            |");
        System.out.println("| 6 – List all clients who pay a certain rent amount                                     |");
        System.out.println("| 7 – List all owners who have a certain amount of properties                            |");
        System.out.println("| 8 – Exit                                                                               |");
        System.out.println("==========================================================================================");
        //Uses the inputUtility library ask the user input until is a valid input
        selectedMenuOption = inputUtilities.askUserForInt("Select one option from the menu", 1, 8);
        //Goes to find wich action was selected
        menuOptionActions(selectedMenuOption);
    }
     
      void menuOptionActions(int selectedMenuOption){
         //switch case used because there is an only variable to validate and it's value is limited
         switch (selectedMenuOption){
             case 1 -> {
                 //calls the method with the proper action required
                 listAllPropertiesRentedByClients();
                 //goes back to the menu option
                 showMainMenuOptions();
            }
                 
             case 2 -> {
                 //calls the method with the proper action required
                 listAllPropertiesWithSpecificOwner();
                 //goes back to the menu option
                 showMainMenuOptions();
            }
                 
             case 3 -> {
                 //calls the method with the proper action required
                 listPropertiesByRentAmount();
                 //goes back to the menu option
                 showMainMenuOptions();
            } 
                 
             case 4 -> {
                 //calls the method with the proper action required
                 listAllPropertiesByAddress();
                 //goes back to the menu option
                 showMainMenuOptions();
            }
             case 5 -> {
                 //calls the method with the proper action required
                 listAllPropertiesBySpecificDuration();
                 //goes back to the menu option
                 showMainMenuOptions();
            }
             case 6 -> {
                 //calls the method with the proper action required
                 listClientsByRentAmount();
                 //goes back to the menu option
                 showMainMenuOptions();
            }
                 
             case 7 -> {
                 //calls the method with the proper action required
                 listOwnersByPropertyAmount();
                 //goes back to the menu option
                 showMainMenuOptions();
            }
             //the defauld it's the exit option, so this method doesn't call any action, just displays a message, and the program stops
             default -> System.out.println("Thank you for visiting our system. See you next time!");
         }
    }

    //Prints the menu option for comparison and returns the proper result to compare
    private  String getComparisonOptions(String optionMenuTitle, String valueToCompare){
        int selectedMenuOption = 0;
        
        //Displays comparison menu options
        System.out.println(optionMenuTitle);
        System.out.println("1 – Bigger than " + valueToCompare);
        System.out.println("2 – Smaller than " + valueToCompare);
        System.out.println("3 – Equal to " + valueToCompare);

        //Validates the user input to see if the number is a valid input
        selectedMenuOption = inputUtilities.askUserForInt("Select one option from the menu", 1, 3);
        
        //Depending on the option selected, it returns the MySql symbol used in the command
        return switch (selectedMenuOption) {
            case 1 -> " > ";
            case 2 -> " < ";
            default -> " = ";
        }; 
    }      
    
    //Lists all the properties which have a certain rent amount
    private void listPropertiesByRentAmount(){
        //Asks the user for a rent amount until it gets a valid input
        Double keyword = inputUtilities.askUserForDouble("Please, insert the rent amount for searching", 0);
        
        //Asks the user to select a type of comparison for the query
        String comparisonKey = getComparisonOptions("How would you like to filter the rent amount?", keyword.toString());
        
        //Builds the query using the keyword and the comparison
        String query = "SELECT PropertyNo as \"Property Code\", PropertyAddress as \"Property Address\", Monthly_rent as \"Monthly Rent\" FROM property WHERE Monthly_rent " + comparisonKey + " " + keyword + " ORDER BY Monthly_rent";
        
        //Calls the select and print command from the databaseManager, passing the query and the columns' names to be displayed
        databaseManager.ExecuteSelectCommandAndPrint(query, new String[]{"Property Code", "Property Address", "Monthly Rent"});        
    }
    
    //Lists all clients who pay a certain rent amount
    private void listClientsByRentAmount(){
        //Asks the user for a rent amount until it gets a valid input
        Double keyword = inputUtilities.askUserForDouble("Please, insert the rent amount for searching searching", 0);
        
        //Asks the user to select a type of comparison for the query
        String comparisonKey = getComparisonOptions("How would you like to filter the rent amount?", keyword.toString());
        
        //Builds the query using the keyword and the comparison
        String query = "SELECT c.Client_Name as \"Client Name\", p.PropertyAddress as \"Property Address\", p.Monthly_rent as \"Monthly Rent\"" + 
                "FROM Client as c JOIN ClientRental as cr ON c.Client_No = cr.Client_No "+
                "JOIN Property as p ON cr.PropertyNo = p.PropertyNo WHERE p.Monthly_rent " + comparisonKey + " "+ keyword + " ORDER BY Monthly_rent";
        
        //Calls the select and print command from the databaseManager, passing the query and the columns' names to be displayed
        databaseManager.ExecuteSelectCommandAndPrint(query, new String[]{"Client Name", "Property Address", "Monthly Rent"});        
    }
    
    //Lists all properties rented out by all clients whose name contains a keyword/letter
    private void listAllPropertiesRentedByClients(){
        //Asks the user for a text name/keyword until it gets a valid input
        String keyword = inputUtilities.askUserForText("Please, insert a client name or a keyword for the searching");
          
        //Builds the query using the keyword
        String query = "SELECT c.Client_Name as \"Client Name\", p.PropertyAddress as \"Property Address\", DATE_FORMAT(cr.Rent_start, '%d/%m/%Y') as \"Rent Start\", DATE_FORMAT(cr.Rent_finish, '%d/%m/%Y') as \"Rent Finish\" FROM Client as c " +
                  "JOIN ClientRental as cr ON c.Client_No = cr.Client_No JOIN Property as p ON cr.PropertyNo = p.PropertyNo " +
                  "WHERE c.Client_Name like '%" + keyword + "%' ORDER BY c.Client_Name";
          
        //Calls the select and print command from the databaseManager, passing the query and the columns' names to be displayed
        databaseManager.ExecuteSelectCommandAndPrint(query, new String[]{"Client Name", "Property Address", "Rent Start", "Rent Finish"});
      }
      
    //Lists all owners who have a certain amount of properties
    private void listOwnersByPropertyAmount(){
        //Asks the user for a number until it gets a valid input
        int propertyNumber = inputUtilities.askUserForInt("Please, insert the number of properties for searching", 0);
        
        //Asks the user to select a type of comparison for the query
        String comparisonKey = getComparisonOptions("How would you like to filter the number of properties?", Integer.toString(propertyNumber));
        
        //Builds the query using the keyword and the comparison
        String query = "SELECT  o.Owner_No  as \"Owner Code\",  o.Owner_Name  as \"Owner Name\",  COUNT(p.PropertyNo) as \"Number Of Properties\" FROM  Owner as o " + 
                "JOIN Property as p ON o.Owner_No = p.Owner_No GROUP BY  o.Owner_No "+
                "HAVING COUNT(p.PropertyNo) " + comparisonKey + " "+ propertyNumber + " ORDER BY p.PropertyNo";
        
        //Calls the select and print command from the databaseManager, passing the query and the columns' names to be displayed	
        databaseManager.ExecuteSelectCommandAndPrint(query, new String[]{"Owner Code", "Owner Name", "Number Of Properties"});        
    }
   
    //Lists all the properties for a specific owner
    private void listAllPropertiesWithSpecificOwner(){
        //Asks the user for a text name until it gets a valid input
        String keyword = inputUtilities.askUserForText("Please, insert the owner name for the searching");
          
        //Builds the query using the keyword
        String query = "SELECT p.PropertyNo as \"Property Code\", p.PropertyAddress as \"Property Address\" FROM Property as p " +
                  "JOIN Owner as o ON p.Owner_No = o.Owner_No WHERE o.Owner_Name = '" + keyword + "'" + " ORDER BY p.PropertyAddress";
         
        //Calls the select and print command from the databaseManager, passing the query and the columns' names to be displayed
        databaseManager.ExecuteSelectCommandAndPrint(query, new String[]{"Property Code", "Property Address"});
    }      
    
    //Lists all properties which the address contains a keyword/letter
    private void listAllPropertiesByAddress(){
        //Asks the user for an address/keyword until it gets a valid input
        String keyword = inputUtilities.askUserForText("Please, insert an address or a keyword for the searching");
          
        //Builds the query using the keyword
        String query = "SELECT PropertyNo as \"Property Code\", PropertyAddress as \"Property Address\" FROM Property " +
                  "WHERE PropertyAddress like '%" + keyword + "%' order by PropertyAddress";
        
        //Calls the select and print command from the databaseManager, passing the query and the columns' names to be displayed  
        databaseManager.ExecuteSelectCommandAndPrint(query, new String[]{"Property Code", "Property Address"});
    }
      
    //List all clients who have properties rented out for a specific duration
    private void listAllPropertiesBySpecificDuration(){
          //Asks the user for a the initial and final date until it gets a valid input
          Date initialDate = inputUtilities.askUserForDate("Please, insert the rent initial date");
          Date finalDate = inputUtilities.askUserForDate("Please, insert the rent final date", initialDate);
          
          //Builds the query using the keyword
          //Uses the SimpleDateFormat function to format the given data to the database data format
          String query = "SELECT c.Client_Name as \"Client Name\", p.PropertyAddress as \"Property Address\", DATE_FORMAT(cr.Rent_start, '%d/%m/%Y') as \"Rent Start\", DATE_FORMAT(cr.Rent_finish, '%d/%m/%Y') as \"Rent Finish\"" +
                  "FROM Client as c JOIN ClientRental as cr ON c.Client_No = cr.Client_No "+
                  "JOIN Property as p ON cr.PropertyNo = p.PropertyNo " +
                  "WHERE cr.Rent_start = '" + new SimpleDateFormat("yyyy-MM-dd").format(initialDate) + 
                  "' and cr.Rent_finish = '" + new SimpleDateFormat("yyyy-MM-dd").format(finalDate)+"' order by cr.Rent_start, cr.Rent_finish";
         
        //Calls the select and print command from the databaseManager, passing the query and the columns' names to be displayed
        databaseManager.ExecuteSelectCommandAndPrint(query, new String[]{"Client Name", "Property Address", "Rent Start", "Rent Finish"});
    }      
}


 
	







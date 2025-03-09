/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package inpututilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * HDIP Comp Sept 2024 cohort
 * An example of using methods that you have written yourself
 * 
 * To use these in another program, you need to:
 * 1) COPY the entire package 'inpututilities'
 * 2) Go to the project where you want to use the methods
 * 3) Select 'Source Packages' and paste. This will create a copy of the 'inpututilities' package in the project
 * 4) At the top of your code, you will need to import InputUtilities
 * 
 * @author kheal
 */
public class InputUtilities {

    
    
    /**
     * Ask user to enter some  text - if they enter non-text (like numbers)
     * then ask them again
     * @param prompt - the question or prompt to ask the user
     * @return a String containing whatever text the user entered
     */
    public String askUserForText(String prompt){
        
        Scanner myKB = new Scanner(System.in);
        String userInput ;
        
        do{
            System.out.println(prompt);
            System.out.println("You must enter text only.");
            
            userInput = myKB.nextLine();            
            
        }while (!userInput.matches("[a-zA-Z .!@?\"]+"));
        //userinput must be valid text
        
        return (userInput);
    }
    
        public String askUserForOneWordText(String prompt){
        
        Scanner myKB = new Scanner(System.in);
        String userInput ;
        
        do{
            System.out.println(prompt);
            System.out.println("You must enter one word text only.");
            
            userInput = myKB.nextLine();            
            
        }while (!userInput.matches("[a-zA-Z]+"));
        //userinput must be valid text
        
        return (userInput);
    }
    
    /**
     * Ask the user to enter any integer value (negatives are allowed)
     * if they do not enter an integer ask them again
     * @param prompt the question or prompt to ask the user
     * @return a valid int entered by the user
     */
    public int askUserForInt(String prompt){
        
        Scanner myKB = new Scanner(System.in);
        String userInput ;
        
        do{
            System.out.println(prompt);
            System.out.println("You must enter integer only.");
            
            userInput = myKB.nextLine();            
            
        }while (!userInput.matches("[0-9]+"));
        //userinput must be valid int
        
        return (Integer.parseInt(userInput));
    }
    
    /**
     * Ask user for an integer value bigger than a given minimum
     * if they do not enter a valid integer ask them again
     * @param prompt the question or prompt to ask user
     * @param minValue the lowest value allowed
     * @return a valid int bigger than minValue
     */
    public int askUserForInt(String prompt, int minValue){
        
        Scanner myKB = new Scanner(System.in);
        int userInput = minValue - 1;
           do{
            System.out.println(prompt);
            System.out.println("You must enter integer only bigger than " + minValue);
            try{
            userInput = myKB.nextInt();            
           }
            catch(Exception ex){
                System.out.println("That's what not an integer");
                //Used to avoid the infinity loop
                myKB.next();
            }
            
        }while (userInput < minValue);
        //userinput must be valid int
        
        return (userInput);
        
    }
    
    /**
     * Ask user for an integer value in a given range
     * if they do not enter a valid integer ask them again
     * @param prompt the question or prompt to ask user
     * @param minValue the lowest value allowed
     * @param maxValue the highest value allowed
     * @return a valid int within the given range
     */
    public int askUserForInt(String prompt, int minValue, int maxValue){
        
        Scanner myKB = new Scanner(System.in);
        int userInput= minValue - 1;
 
            do{
                System.out.println(prompt);
                System.out.println("You must enter integer between " + minValue + " and " + maxValue);
                try{
                    userInput = myKB.nextInt();            

                }
                catch(Exception ex){
                    System.out.println("That's what not an integer");
                    myKB.next();
                }
            
        }while (userInput < minValue || userInput > maxValue);
        //userinput must be valid int
        
        return (userInput);       
    }
     
    /**
     * Ask user for an double value in a given range
     * if they do not enter a valid double ask them again
     * @param prompt the question or prompt to ask user
     * @param minValue the lowest value allowed
     * @return a valid double within the given range
     */
    public Double askUserForDouble(String prompt, double minValue){
        
        Scanner myKB = new Scanner(System.in);
        Double userInput = 0.0;
            do{
                System.out.println(prompt);
                System.out.println("You must enter a decimal bigger than " + minValue + ".");
                try{
                    userInput = myKB.nextDouble();            
                }
                catch(Exception ex){
                    System.out.println("That's what not an decimal.");
                    myKB.next();
                }
            
        }while (userInput <= minValue);
        //userinput must be valid int
        
        return (userInput);       
    }
    
     /**
     * Ask user for a date value 
     * if they do not enter a valid date ask them again
     * @param prompt the question or prompt to ask user
     * @return a valid date 
     */
    public Date askUserForDate(String prompt){
            Scanner myKB = new Scanner(System.in);
            String userInput;
            boolean isDatevalid = true;
            //format the string to the given date format
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date inputDate = new Date();
            do{            
                System.out.println(prompt);
                System.out.println("You must enter a date (Eg. 20/01/2024)");
                
                userInput = myKB.nextLine();    
                try {
                    //try to parse the String to Date
                    inputDate = dateFormat.parse(userInput);
                    isDatevalid = true;
                } catch (ParseException e) {
                    isDatevalid =  false; 
                    myKB.next();
                }    
            }while (!isDatevalid);
            //userinput must be valid date
            return inputDate;
    } 
    
    /**
     * Ask user for a date value in a given range
     * if they do not enter a valid date ask them again
     * @param prompt the question or prompt to ask user
     * @param minDate the lowest value allowed
     * @return a valid date within the given range
     */    
    public Date askUserForDate(String prompt, Date minDate){
            Scanner myKB = new Scanner(System.in);
            String userInput;
            boolean isDatevalid = true;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date inputDate = new Date();
            do{            
                System.out.println(prompt);
                System.out.println("You must enter a date bigger than " + dateFormat.format(minDate) + " (Eg. 20/01/2024)");
                
                userInput = myKB.nextLine();    
                try {
                    inputDate = dateFormat.parse(userInput);
                    isDatevalid = true;
                            
                } catch (ParseException e) {
                    isDatevalid =  false; 
                    myKB.next();
                }
            //Keeps going until the data is invalid or the data is less than the min date                 
            }while (!isDatevalid || inputDate.before(minDate));
            //userinput must be valid date and bigger than minDate
            return inputDate;
    }     
}

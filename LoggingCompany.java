import java.io.*;
import java.util.*;

public class LoggingCompany
{
   public static void main(String[] args) 
   {        
      Scanner insert = new Scanner(System.in);
      
      //Import log/lumber data
      FileInput input = new FileInput();

      // Build the log matrix
      LogMatrix matrix = new LogMatrix((int)input.getDimensions(), input.getLumber());
      
      // Get lumber cuts for all logs
      ArrayList<Log> log = input.getLog();
      for(int i=0; i<log.size(); i++)
      {
         System.out.println("Log " + (i+1) + ":");
         System.out.println("------");
         ArrayList<Lumber> lumberPerLog = matrix.cutLog(log.get(i), input.getLumber());
         matrix.printInventory(lumberPerLog);
         matrix.printValue(lumberPerLog);
      }
      
      // Print inventory
      System.out.println("Summary:");
      System.out.println("--------");
      matrix.printBoth(matrix.getLumber());
      
      // Purchase lumber
      System.out.printf("Would you like to purchase lumber (Y/N): ");
      String response = insert.next();
      
      // Create new receipt
      LumberReceipt receipt = new LumberReceipt(input.getLumber(), matrix);
      
      while(response.equals("Y") || response.equals("y"))
      {
         // Prompt to buy lumber
         receipt.buyLumber();
         
         // Prompt to buy scrap
         receipt.buyScrap();
      
         // Prompt for address
         receipt.getAddress();
      
         // Print out receipt
         receipt.getSales();
         
         // Prompt for additional purchases
         System.out.printf("Would you like to make a separate purchase (Y/N): ");
         response = insert.next();
      }
      
      // Master inventory report
      System.out.printf("Would you like to see the master inventory report (Y/N): ");
      response = insert.next();
      if(response.equals("Y") || response.equals("y"))
         receipt.getMasterReport();
   } 
}
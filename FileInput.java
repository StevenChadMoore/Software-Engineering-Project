import java.io.*;
import java.util.*;
import java.math.*;

class FileInput
{
   private ArrayList<Log> log;
   private ArrayList<Lumber> lumber;
   private double dimensions;
   
   public FileInput()
   {
      log = new ArrayList<Log>();
      lumber = new ArrayList<Lumber>();
      dimensions = 0.0;
      inputLog();
      inputLumber();
   }
   
   public ArrayList<Log> getLog()
   {
      return log;
   }
   
   public ArrayList<Lumber> getLumber()
   {
      return lumber;
   }
   
   public double getDimensions()
   {
      return dimensions;
   }
   
   public void inputLog() 
   {  
      Scanner input = new Scanner(System.in);
      System.out.print("Please enter the log file: ");  
      String fileName = input.nextLine();
      //String fileName = "t3d_log.txt";       

      try
      {
         File file = new File(fileName);
         Scanner scan = new Scanner(file);
         
         while(scan.hasNext())
         {
            double height = scan.nextDouble();
            double width = scan.nextDouble();
            double length = scan.nextDouble();
            dimensions = Math.max(dimensions, Math.max(height, width));
            Log holder = new Log(height,width,length);
            log.add(holder);
            continue;
         }
      }

      catch(IOException e)
      {
         System.out.printf("There is an error with your file.");
      } 
   }
   
   public void inputLumber() 
   {  
      Scanner input = new Scanner(System.in);
      System.out.print("Please enter the lumber file: ");  
      String fileName = input.nextLine(); 
      //String fileName = "t3d_econ.txt";      

      try
      {
         File file = new File(fileName);
         Scanner scan = new Scanner(file);
         
         while(scan.hasNextLine())
         {
            if(scan.hasNext())
            {
               if(scan.hasNextDouble())
               {
                  double height = scan.nextDouble();
                  try
                  {
                     double width = scan.nextDouble();
                     double length = scan.nextDouble();
                     double value = scan.nextDouble();
                     Lumber holder = new Lumber(height,width,length,value);
                     lumber.add(holder);
                     continue;
                  }
                  catch(NoSuchElementException e) 
                  {
                     Lumber holder = new Lumber(1,1,1,(height/1728));
                     lumber.add(holder);
                  }
               }
            }
         }
      }

      catch(IOException e) 
      {
         System.out.printf("There is an error with your file.");
      } 
      Collections.sort(lumber);
   } 
}
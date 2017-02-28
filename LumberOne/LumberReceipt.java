import java.io.*;
import java.util.*;
import java.net.*;

class LumberReceipt
{
   ArrayList<Lumber> sales;
   LogMatrix matrix;
   String city;
   String state;
   
   public LumberReceipt(ArrayList<Lumber> lumber, LogMatrix matrix)
   {
      sales = new ArrayList<Lumber>(lumber.size());
      for (Lumber lum : lumber) {sales.add(new Lumber(lum));}
      this.matrix = matrix;
      city = "";
      state = "";
   }

   public void buyLumber()
   {
      Scanner insert = new Scanner(System.in);
      String c = "Y";
      
      while(c.equals("Y") || c.equals("y"))
      {  
         System.out.println("---------------------------------------------------------------------------");
         System.out.println("Current Inventory:");
         System.out.println("------------------");
         matrix.printInventory(matrix.getLumber());
         
         int cut = 0;
         while(cut < 1 || cut > 3)
         {
            System.out.printf("\nWhich lumber cut would you like (1-3): ");
            cut = insert.nextInt();
         }
         
         int amount = 0;
         while(amount < 1)
         {
            System.out.printf("How many of cut " + cut + " would you like: ");
            amount = insert.nextInt();
         }
         
         if(matrix.getLumber().get(cut-1).getQuantity() >= amount)
         {
            sales.get(cut-1).setQuantity(sales.get(cut-1).getQuantity() + amount);
            matrix.getLumber().get(cut-1).setQuantity(matrix.getLumber().get(cut-1).getQuantity() - amount);
         }
         else
            System.out.println("Insufficient lumber of that amount");
         
         System.out.printf("Would you like to purchase more lumber (Y/N): ");
         c = insert.next();
      }
   }

   public void buyScrap()
   {
      Scanner insert = new Scanner(System.in);
      System.out.printf("Would you like to purchase scrap (Y/N): ");
      String c = insert.next();
      
      while(c.equals("Y") || c.equals("y"))
      {  
         System.out.println("---------------------------------------------------------------------------");
         System.out.println("Current Inventory:");
         System.out.println("------------------");
         matrix.printInventory(matrix.getLumber());
         
         double amount = 0.0;
         while(amount < 1)
         {
            System.out.printf("How much cubic feet of scrap would you like (eg 1.52): ");
            amount = insert.nextDouble();
         }
         
         if(matrix.getLumber().get(sales.size()-1).getQuantity() >= amount)
         {
            sales.get(sales.size()-1).setQuantity(sales.get(sales.size()-1).getQuantity() + amount);
            matrix.getLumber().get(sales.size()-1).setQuantity(matrix.getLumber().get(sales.size()-1).getQuantity() - amount);
         }
         else
            System.out.println("Insufficient scrap of that amount");
         
         System.out.printf("Would you like to purchase more scrap (Y/N): ");
         c = insert.next();
      }
   }
   
   public void getAddress()
   {
      Scanner insert = new Scanner(System.in);
      System.out.printf("Shipping City: ");
      city = "" + insert.next();
      System.out.printf("Shipping State: ");
      state = "" + insert.next();
      
   }

   public void getSales()
   {
      String free[] = {"Quitman","Hahira","Adel","Lakeland","Statenville","Lake Park","Jasper","Madison"};
      System.out.println("---------------------------------------------------------------------------");
      System.out.println("Bill of Sale");
      System.out.println("------------");
      System.out.println("Date: December 8, 2016");
      System.out.println("Destination: " + city + ", " + state + "\n");
      System.out.println("Product Charges\n");

      double total = 0.0;
      double weight = 0.0;
      System.out.println("Size\tQty\tPrice\tTotal Price");
      for(int i=0; i<sales.size(); i++)
      {
         if(sales.get(i).getHeight() * sales.get(i).getWidth() * sales.get(i).getLength() != 1.0)
         {
            weight += sales.get(i).getHeight() * sales.get(i).getWidth() * sales.get(i).getLength() * sales.get(i).getQuantity();
            double value = sales.get(i).getQuantity() * sales.get(i).getValue();
            System.out.println(
               (int)sales.get(i).getHeight() + "x" + 
               (int)sales.get(i).getWidth() + "x" +
               (int)sales.get(i).getLength() + "\t" +
               (int)sales.get(i).getQuantity() + "\t$" + 
               String.format("%.2f", sales.get(i).getValue()) + "\t$" + String.format("%.2f", value));
            total += value;
         }
         else
         {
            weight += sales.get(i).getValue();
            double value = sales.get(i).getQuantity() * sales.get(i).getValue() * 1728;
            System.out.println(
               "Scrap" + "\t" + (int)sales.get(i).getQuantity() + "\t$" + 
               String.format("%.2f", sales.get(i).getValue()*1728) + "\t$" + String.format("%.2f", value));
            total += value;
         }
      }
      weight = weight/1728 * 38;
      System.out.println("Total: \t\t\t$" + String.format("%.2f", total));
      
      System.out.println("\nDelivery Charges\n");
      System.out.println("Total Weight: " + String.format("%.2f", weight) + " lbs");
      System.out.println("Number of trucks required: " + (int)Math.ceil(weight/10000));
      
      if(Arrays.asList(free).contains(city))
      {
         System.out.println("Distance: " + distance(city + state) + " miles");
         System.out.println("$/mile/truck: N/A (free shipping)");
         System.out.println("Total delivery charges: N/A (free shipping)" + "\n");
         System.out.println("Total Charges: $" + String.format("%.2f",total));
      }
      else
      {
         System.out.println("Distance: " + distance(city + state) + " miles");
         System.out.println("$/mile/truck: $1.00");
         System.out.println("Total delivery charges: $" + String.format("%.2f", Math.ceil(weight/10000)*1*distance(city + state)) + "\n");
         System.out.println("Total Charges: $"  + String.format("%.2f",total + Math.ceil(weight/10000)*1*distance(city + state)));
      }
      System.out.println("---------------------------------------------------------------------------");
   }
   
   public void getMasterReport()
   {
      System.out.println("---------------------------------------------------------------------------");
      System.out.println("Master Inventory Report");
      System.out.println("-----------------------");
      System.out.println("Size\tQty\tPrice\tTotal Price");
      double total = 0.0;
      for(int i=0; i<matrix.getLumber().size(); i++)
      {
         if(matrix.getLumber().get(i).getHeight() * matrix.getLumber().get(i).getWidth() * matrix.getLumber().get(i).getLength() != 1.0)
         {
            double value = matrix.getLumber().get(i).getQuantity() * matrix.getLumber().get(i).getValue();
            System.out.println(
               (int)matrix.getLumber().get(i).getHeight() + "x" + 
               (int)matrix.getLumber().get(i).getWidth() + "x" +
               (int)matrix.getLumber().get(i).getLength() + "\t" +
               (int)matrix.getLumber().get(i).getQuantity() + "\t$" + 
               String.format("%.2f", matrix.getLumber().get(i).getValue()) + "\t$" + String.format("%.2f", value));
            total += value;
         }
         else
         {
            double value = matrix.getLumber().get(i).getQuantity() * matrix.getLumber().get(i).getValue() * 1728;
            System.out.println(
               "Scrap" + "\t" +
               (int)matrix.getLumber().get(i).getQuantity() + "\t$" + 
               String.format("%.2f", matrix.getLumber().get(i).getValue()*1728) + "\t$" + String.format("%.2f", value));
            total += value;
         }
      }
      System.out.println("Total: \t\t\t$" + String.format("%.2f", total));
      System.out.println("---------------------------------------------------------------------------");
   }
   
   public double distance(String destination)
   {
      String response = "";
      
      try 
      {
         URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=Valdosta,Ga&destination="+destination);
         BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
         String strTemp = "";
      
         while (null != (strTemp = br.readLine())) 
         {
            response += strTemp;
         }
      } 
      
      catch (Exception ex) 
      {
         ex.printStackTrace();
      }

      String distance = response.substring(response.indexOf("distance")+15,response.indexOf("mi\","));
      distance = distance.substring(distance.indexOf(":")+3);
      return Double.parseDouble(distance);
   }
}

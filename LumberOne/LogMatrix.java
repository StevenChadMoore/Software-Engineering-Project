import java.util.*;

class LogMatrix
{
   private String[][] matrix;
   private ArrayList<Lumber> lumber;
   
   public LogMatrix(int a, ArrayList<Lumber> b)
   {
      matrix = new String[a+1][a+1]; // Dimensions are set to be one greater than largest log height/width dimension
      lumber = new ArrayList<Lumber>(b.size()); // New Lumber ArrayList
      for (Lumber lum: b) {lumber.add(new Lumber(lum));} // Deep clone of each cut of lumber
      buildMatrix(); // Run the algorithm
   }
   
   public void buildMatrix() 
   {
      // Populate our profit table with the values of each individual cut
      for(int i=0; i<lumber.size(); i++) 
      {
         matrix[(int)lumber.get(i).getWidth()][(int)lumber.get(i).getHeight()] = "" + lumber.get(i).getValue();
         matrix[(int)lumber.get(i).getHeight()][(int)lumber.get(i).getWidth()] = "" + lumber.get(i).getValue();
      }
      
      for(int i=1; i<matrix.length; i++) 
      {
         for (int j=i; j<matrix[i].length; j++) 
         {
            String bestCut = "-1";
            
            // For a piece of lumber size i by j, try every cut
            for(int k=0; k<lumber.size(); k++)
            {  
               // The profit we get from cutting a single board of this size
               double cutProfit = lumber.get(k).getValue();
               String leftover = "";
               String leftovers = "";
            
               // Make sure this size cut is possible
               if(i - (int)lumber.get(k).getHeight() >= 0 && j - (int)lumber.get(k).getWidth() >= 0)
               {  
                  // We can split up the leftovers in one of two ways
                  leftover = "" + matrix[i-(int)lumber.get(k).getHeight()][j] + " " + matrix[(int)lumber.get(k).getHeight()][j-(int)lumber.get(k).getWidth()];
                  leftovers = "" + matrix[i-(int)lumber.get(k).getHeight()][(int)lumber.get(k).getWidth()] + " " + matrix[i][j-(int)lumber.get(k).getWidth()];                  
                  
                  // Determine largest value of leftover cuts
                  String leftoverProfits = "";
                  if(count(leftover) > count(leftovers))
                     leftoverProfits = "" + leftover;
                  else
                     leftoverProfits = "" + leftovers;
                  
                  // Take the highest profit from all possible cuts
                  if(cutProfit + count(leftoverProfits) > count(bestCut))
                     bestCut = "" + cutProfit + " " + leftoverProfits;  
               }
               if(i - (int)lumber.get(k).getWidth() >= 0 && j - (int)lumber.get(k).getHeight() >= 0)
               {
                  // Rotate the lumber on its face and split again
                  leftover = "" + matrix[i-(int)lumber.get(k).getWidth()][j] + " " + matrix[(int)lumber.get(k).getWidth()][j-(int)lumber.get(k).getHeight()];
                  leftovers = "" + matrix[i-(int)lumber.get(k).getWidth()][(int)lumber.get(k).getHeight()] + " " + matrix[i][j-(int)lumber.get(k).getHeight()]; 
                  
                  // Determine largest value of leftover cuts
                  String leftoverProfits = "";
                  if(count(leftover) > count(leftovers))
                     leftoverProfits = "" + leftover;
                  else
                     leftoverProfits = "" + leftovers;
                  
                  // Take the highest profit from all possible cuts
                  if(cutProfit + count(leftoverProfits) > count(bestCut))
                     bestCut = "" + cutProfit + " " + leftoverProfits;                   
               }
            }

            // If no cuts are possible, we can only make 0 profit.
            if(count(bestCut) > count(matrix[i][j]) && count(bestCut) >=0)
               matrix[i][j] = "" + bestCut;
            if(matrix[i][j] == null)
               matrix[i][j] = "0.0";
            matrix[j][i] = matrix[i][j];
         }
      }
   }

   public double count(String str)
   {
      if(str == null)
         return -1.0;
   
      double count = 0.0;
      String[] parser = str.split(" ");
      for(int k=0; k<parser.length; k++)
         if(parser[k].matches("[a-zA-Z ]*\\d+.*"))
            count += Double.parseDouble(parser[k]);
      return count;
   }
   
   public void printMatrix()
   {
      System.out.print("\n");
      for(int i=1; i<matrix.length; i++)
      {
         for(int j=1; j<matrix.length; j++)
         {
            String[] parser = matrix[i][j].split(" ");
            for(int k=0; k<parser.length; k++)
               if(parser[k].matches("[a-zA-Z ]*\\d+.*"))
                  System.out.print(Double.parseDouble(parser[k]) + " ");
            System.out.print("|\t");
         }
      System.out.print("\n\n");
      }
   }
   
   public ArrayList<Lumber> cutLog(Log log, ArrayList<Lumber> lum)
   {
      ArrayList<Lumber> lumberPerLog = new ArrayList<Lumber>(lum.size());
      for (Lumber lumber : lum) {lumberPerLog.add(new Lumber(lumber));}
      double scrap = 0.0;
      
      while(log.getLength()>=lumber.get(0).getLength())
      {
         String[] parser = matrix[(int)log.getHeight()][(int)log.getWidth()].split(" ");
         for(int i=0; i<parser.length; i++)
         {
            if(parser[i].matches("[a-zA-Z ]*\\d+.*"))
            {
               for(int j=0; j<lumber.size()-1; j++)
               {
                  if(Double.parseDouble(parser[i])==lumber.get(j).getValue())
                  {
                     lumber.get(j).setQuantity(lumber.get(j).getQuantity() + 1); 
                     lumberPerLog.get(j).setQuantity(lumberPerLog.get(j).getQuantity() + 1);
                     scrap += lumber.get(j).getHeight() * lumber.get(j).getWidth() * lumber.get(j).getLength();
                  }
               }  
            }
         }
         double leftover = ((log.getWidth() * log.getHeight() * lumber.get(0).getLength()) - scrap)/1728;
         lumber.get(lumber.size()-1).setQuantity(lumber.get(lumber.size()-1).getQuantity() + leftover);
         lumberPerLog.get(lumberPerLog.size()-1).setQuantity(lumberPerLog.get(lumberPerLog.size()-1).getQuantity() + leftover);
         log.setLength(log.getLength()-lumber.get(0).getLength());
         scrap = 0.0;
      }
      
      if(log.getLength()>0)
      {
         double leftover = (log.getWidth() * log.getHeight() * log.getLength())/1728;
         lumber.get(lumber.size()-1).setQuantity(lumber.get(lumber.size()-1).getQuantity() + leftover);
         lumberPerLog.get(lumberPerLog.size()-1).setQuantity(lumberPerLog.get(lumberPerLog.size()-1).getQuantity() + leftover);
      }
      return lumberPerLog;
   }
   
   public ArrayList<Lumber> getLumber()
   {
      return lumber;
   }
   
   public void printInventory(ArrayList<Lumber> lum)
   {
      double scrap = 0.0;
      System.out.println("Qty\tHeight\tWidth\tLength\tValue");
      
      for(int i = 0; i<lum.size(); i++)
      {
         if(lum.get(i).getHeight() * lum.get(i).getWidth() * lum.get(i).getLength() != 1.0)
         {
            System.out.println(lum.get(i).toString());
         }
         else
         {
            scrap += lum.get(i).getQuantity();
         }
      }
      System.out.println("\nScrap: " + String.format("%.2f", scrap) + " cf");
   }
   
   public void printValue(ArrayList<Lumber> lum)
   {
      double lumberTotal = 0.0;
      double scrap = 0.0;
      double scrapTotal = 0.0;
      
      for(int i = 0; i<lum.size(); i++)
      {
         if(lum.get(i).getHeight() * lum.get(i).getWidth() * lum.get(i).getLength() != 1.0)
         {
            lumberTotal += lum.get(i).getValue() * lum.get(i).getQuantity();
         }
         else
         {
            scrapTotal += lum.get(i).getValue() * lum.get(i).getQuantity() * 1728;
         }
      }
      
      System.out.println("\n\tValue\t\tValue%");
      System.out.println("Lumber:" + "\t$" + String.format("%.2f", lumberTotal) + "   \t" + String.format("%.2f", (100 * lumberTotal/(lumberTotal + scrapTotal))) + "%");
      System.out.println("Scrap:" + "\t$" + String.format("%.2f", scrapTotal) + "   \t" + String.format("%.2f", (100 * scrapTotal/(lumberTotal + scrapTotal)))+ "%");
      System.out.println("Total:" + "\t$" + String.format("%.2f", (lumberTotal + scrapTotal)));
      System.out.println("---------------------------------------------------------------------------");
   }
   
   public void printBoth(ArrayList<Lumber> lum)
   {
      printInventory(lum);
      printValue(lum);
   } 
}

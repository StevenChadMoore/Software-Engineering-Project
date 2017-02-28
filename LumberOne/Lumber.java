import java.util.*;

public class Lumber implements Comparable<Lumber>                                                        
{
   private double height;                                                             
   private double width;
   private double length;
   private double quantity;
   private double value;
   
   public Lumber(double a, double b, double c, double d)                                  
   {
      height = Math.min(a,b);
      width = Math.max(a,b);
      length = c;
      quantity = 0;
      value = d;
   }
   
   public Lumber(Lumber a)
   {
      this.height = a.getHeight();
      this.width = a.getWidth();
      this.length = a.getLength();
      this.quantity = a.getQuantity();
      this.value = a.getValue();
   }
   
   @Override
   public int compareTo(Lumber a) 
   {
      return new Double(a.value/(a.height*a.width*a.length)).compareTo(value/(height*width*length));
   }
   
   public void setHeight(double a)                                                             
   {
      height = a;
   }
   
   public double getHeight()
   {
      return height;
   }
      
   public void setWidth(double a)                                                             
   {
      width = a;
   }
   
   public double getWidth()
   {
      return width;
   }
   
   public void setLength(double a)                                              
   {
      length = a;
   }
   
   public double getLength()
   {
      return length;
   }
   
   public void setQuantity(double a)                                                             
   {
      quantity = a;
   }
   
   public double getQuantity()
   {
      return quantity;
   }
   
   public void setValue(double a)
   {
      value = a;
   }
   
   public double getValue()
   {
      return value;
   }
   
   public String toString()
   {
      return String.format("%.0f", quantity) + "\t" +
             String.format("%.2f", height) + "\t" +
             String.format("%.2f", width) + "\t" +
             String.format("%.2f", length) + "\t$" +
             String.format("%.2f", quantity * value) 
             /*+ "\tWeighted Value: " + String.format("%.6f", value/(height*width*length))*/;
   }
}


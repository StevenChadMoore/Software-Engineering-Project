class Log                                                                      
{
   private double height;                                                                 
   private double width;
   private double length;
   
   public Log()                                                                
   {
      height = 0;
      width = 0;
      length = 0;
   }
   
   public Log(double a, double b, double c)                                            
   {
      height = a;
      width = b;
      length = c;
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
   
   public String toString()
   {
      return "Height: " + height + "\tWidth: " + width + "\tLength: " + length;
   }
}

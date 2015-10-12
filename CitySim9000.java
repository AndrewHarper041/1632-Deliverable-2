import java.util.*;

public class CitySim9000
{
    static LCG lcg;
    static Drivers drivers;
    
    public static void main(String args[])
    {
        if(args.length != 1)
            System.out.println("Please provide number for random number generator seed.");
            
        else
        {
            lcg = new LCG(Integer.parseInt(args[0]));
            drivers = new Drivers(lcg);
            System.out.println("End");
        }
    }
}
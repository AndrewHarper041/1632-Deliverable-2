import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.both;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.everyItem;
import static org.junit.matchers.JUnitMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.util.*;
import org.junit.Test;

public class Tests 
{
    //Initial test for checking build
    @Test
    public void tautologyTest() 
    {   
        org.junit.Assert.assertEquals(1,1);
    }
    
    //Test to verify mockito functionality. Code taken from mockito documentaion
    @Test
    public void mockitoTest()
    {
    
    }
    
    //Tests that RNG getNext returns ints
    @Test
    public void rngGetNextTest()
    {
        LCG lcg = new LCG(1); //arbitrary seed
        int rand = lcg.getNext();
        org.junit.Assert.assertEquals(rand, (int)rand);
    }
    
    //Tests that randDrive returns only 0 or 1
    @Test
    public void randDriveTest()
    {
        LCG lcg = new LCG(100); //arbitrary seed
        Drivers drivers = new Drivers(lcg);
        
        //Loop 100 times, marking var fail as 1 if any value besides 0 or 1 is found
        int fail = 0;
        int decision;
        for(int i=0; i<100; i++)
        {
            decision = drivers.randDrive();
            if(decision != 0 && decision != 1)
                fail = 1;
        }
        
        org.junit.Assert.assertEquals(0, fail);
    }
    
    //Tests that drivers are randomly assigned a location 0-4
    @Test
    public void initLocTest()
    {
        LCG lcg = new LCG(-500); //arbitrary seed
        Drivers drivers = new Drivers(lcg);
        
        //Loop 100 times, marking var fail if any value is 0-4
        int fail = 0;
        int decision;
        for(int i=0; i<100; i++)
        {
            decision = drivers.randLoc();
            
            if(decision < 0 || decision > 4)
                fail = 1;
        }
        
        org.junit.Assert.assertEquals(0, fail);
    }
    
    //Tests that drivers returns the correct specefic driver
    @Test
    public void getDriverTest()
    {
        LCG lcg = new LCG(400); //arbitrary seed
        Drivers drivers = new Drivers(lcg);
    }
    
    //Tests that list of five drivers with location of int value 0-4 only properly generated
    @Test
    public void genDriversTest()
    {
        LCG lcg = new LCG(400); //arbitrary seed
        Drivers drivers = new Drivers(lcg);
        drivers.genDrivers();
        
        //Generate drivers, then iterate over list looking for any improper values
        int fail = 0;
        int driverID;
        for(int i=0; i<5; i++)
        {
            if(drivers.getDriver(i) < 0 || drivers.getDriver(i) > 4)
                fail = 1;
        }
    }
} 

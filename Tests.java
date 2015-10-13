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
    public void randLocTest()
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
    
    //Tests that drivers returns the correct specefic driver, uses stubbing
    @Test
    public void getDriverLocTest()
    {
        //Setup drivers with mock LCG so that Drivers always assigned to 'Outside City'
        LCG mockLCG = mock(LCG.class); //stubbed lcg to control the return values
        when(mockLCG.getNext()).thenReturn(0).thenReturn(2).thenReturn(3); //make random number gen return 0 once and then only 2
        Drivers drivers = new Drivers(mockLCG);
        drivers.genDrivers();
        
        //Check that drivers generate with expect location
        org.junit.Assert.assertEquals("Outside City", drivers.getDriverLoc(0));
        org.junit.Assert.assertEquals("University", drivers.getDriverLoc(1));
        org.junit.Assert.assertEquals("Bookstore", drivers.getDriverLoc(2)); //subsequent Drivers will also start at Bookstore
    }
    
    //Tests that list of five drivers with location of one of the 5 areas, checking for "Error" value, uses stubbing
    @Test
    public void genDriversTest()
    {
        LCG mockLCG = mock(LCG.class); //stubbed lcg to control the return values 
        
        //Mock lcg so that it should generate one of every driver. Note this is setting LCG itself, not driver function that will parse out decision useing mod 5
        when(mockLCG.getNext()).thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4); //make random number gen iterate 0 to 4
       
        Drivers drivers = new Drivers(mockLCG);
        drivers.genDrivers();
        
        //Generate drivers, then iterate over list looking for return of error
        int fail = 0;
        int driverID;
        for(int i=0; i<5; i++)
        {
            if(drivers.getDriverLoc(i).equals("Error"))
                fail = 1;
        }
        
        org.junit.Assert.assertEquals(0, fail);
    }
    
    //Test the drivers drive() method which will randomly select new location based off where they are, uses stubbing
    @Test
    public void driveTest()
    {
        LCG mockLCG0 = mock(LCG.class); //stubbed lcg to control the return values
        LCG mockLCG1 = mock(LCG.class); //second copy for other case
        
        //Init drivers with two different stubbed LCG
        when(mockLCG0.getNext()).thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4).thenReturn(0); //return one of each for drivers
        Drivers drivers0 = new Drivers(mockLCG0);
        when(mockLCG1.getNext()).thenReturn(0).thenReturn(1).thenReturn(2).thenReturn(3).thenReturn(4).thenReturn(1); //refresh stub iteration
        Drivers drivers1 = new Drivers(mockLCG1); //second copy to test other available driving option
        
        //Both drivers objects should gen with starting location 0 to 4, then 0 should only return 0 and 1 should return only 1
        drivers0.genDrivers();
        drivers1.genDrivers();
                
        //Arrays list in order the locations that should be arrived for the driving option (0 or 1) being looked at
        //Driving option 0 is 'go striaght'and arbitrarly choose Mall as entry point for coming from outside
        //Driving option 1 is 'go to other street'and arbitrarly choose University as entry point for coming from outside
        String[] locations0 = {"Mall", "Bookstore", "Coffee Shop", "Outside City", "Outside City"}; 
        String[] locations1 = {"University", "Coffee Shop", "Bookstore", "University", "Mall"};

         
        //Iterate over every driver (forced starting location of 0,1,2,3,4) and see if drive() method places at proper destenation for 0, or continue
        for(int i=0; i<5; i++)
        {
            drivers0.drive(i);
            org.junit.Assert.assertEquals(locations0[i],drivers0.getDriverLoc(i));
        }
        
        //Iterate for 'change street' action
        for(int i=0; i<5; i++)
        {
            drivers1.drive(i);
            org.junit.Assert.assertEquals(locations1[i],drivers1.getDriverLoc(i));
        }
        
    }
    
    //Tests that drivers randDrive() function is properly called when drive() is used using a spy
    @Test
    public void randomnessOnDriveTest()
    {
        LCG mockLCG = mock(LCG.class); //mock LCG for spy
        Drivers drivers = new Drivers(mockLCG);
        Drivers spyDrivers = spy(drivers); //spy drivers to do method calling verification
        spyDrivers.genDrivers();
        spyDrivers.drive(0); //arbitrary 0
        
        verify(spyDrivers).randDrive();
    }
    
    //Test properly getting an intersection, represented by a string array
    @Test
    public void getIntersectionTest()
    {
        LCG mockLCG = mock(LCG.class);        
        Drivers drivers = new Drivers(mockLCG);
        
        String[] intersection = drivers.getIntersection(1); //testing from mall
        
        org.junit.Assert.assertEquals("Fourth Ave.",intersection[0]);
        org.junit.Assert.assertEquals("Meow St.",intersection[1]);
    }
} 

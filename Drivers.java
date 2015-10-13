public class Drivers
{
    //The 5 locations will be represented by an int value
    //0 = outside city, 1 = mall, 2 = university, 3 = bookstore, 4 = coffee
    int[] driverList = new int[5]; //5 drivers represented int array, index identifies driver and value tracks  location
    LCG lcg;
    
    public Drivers(LCG gen)
    {
        lcg = gen;
    }
    
    //Creates list of 5 drivers and generates random starting location 0-4 
    public void genDrivers()
    {
        for(int i=0; i<5; i++)
        {
            driverList[i] = randLoc();
        }
    }
    
    //Gets location as String for specefied driver
    public String getDriverLoc(int driverNum)
    {
        switch (driverList[driverNum])
        {
            case 0:
                return "Outside City"; 
            case 1:
                return "Mall"; 
            case 2:
                return "University"; 
            case 3:
                return "Bookstore";     
            case 4:
                return "Coffee Shop"; 
        }
        
        //Failed
        return "Error";
    }
    
    //Update specefied drivers location based of ranDrive() result
    //Run printing is performed here, which prevents having to look at inner working of drive in main, but creates excess output when testing
    public void drive(int driverNum)
    {
        int decision = randDrive();
        
        if(decision == 0) //drive 'down street' case
        {
            System.out.println("Driver " + driverNum + " heading from " + getDriverLoc(driverNum));
            switch (driverList[driverNum])
            {
                
                case 0:
                    driverList[driverNum] = 1; 
                    break;
                case 1:
                    driverList[driverNum] = 3;
                    break;
                case 2:
                    driverList[driverNum] = 4; 
                    break;
                case 3:
                    driverList[driverNum] = 0;   
                    break;
                case 4:
                    driverList[driverNum] = 0; 
                    break;
            }
        }
        
        else if(decision == 1) //drive 'change street' case
        {
            System.out.println("Driver " + driverNum + " heading from " + getDriverLoc(driverNum));
            switch (driverList[driverNum])
            {
                case 0:
                    driverList[driverNum] = 2; 
                    break;
                case 1:
                    driverList[driverNum] = 4; 
                    break;
                case 2:
                    driverList[driverNum] = 3; 
                    break;
                case 3:
                    driverList[driverNum] = 2; 
                    break;
                case 4:
                    driverList[driverNum] = 1;
                    break;
            }
        }
    }
    
    //Gets an intersection as a 2 length array of strings representing the two streets the location a driver is on
    //The 0 indexed string is the main street, then second is the cross street that would be taken if crossed
    //Outside city intersections have arbitrary 4th street in first, fifth in second matching precedent
    public String[] getIntersection(int driverNum)
    {
        String[] intersection = new String[2];
        
        if(driverList[driverNum] == 0) //outside city case
        {
            intersection[0] = "Fourth Ave.";
            intersection[1] = "Fifth Ave.";
        }
        
        if(driverList[driverNum] == 1 || driverList[driverNum] == 3) //find which main street
        {
            intersection[0] = "Fourth Ave.";
            if(driverList[driverNum] == 1)
                intersection[1] = "Meow St.";
            else
                intersection[1] = "Chirp St.";
        }
        
        if(driverList[driverNum] == 2 || driverList[driverNum] == 4)
        {
            intersection[0] = "Fifth Ave.";
            if(driverList[driverNum] == 4)
                intersection[1] = "Meow St.";
            else
                intersection[1] = "Chirp St.";
        }
        
        return intersection;
    }
    
    //Produce a choice returning 0 for 'down street' and 1 for 'change street' when in city
    //Location 'Mall' will be choosed for 0 and 'university' for 1 when outside city
    public int randDrive()
    {
        return lcg.getNext() % 2; //we assume that getNext produces significant randomness
    }
    
    //Produce a choice returning 0-4 for randomly assigning a starting location
    public int randLoc()
    {
        return lcg.getNext() % 5; //we assume that getNext produces significant randomness
    }
}
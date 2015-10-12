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
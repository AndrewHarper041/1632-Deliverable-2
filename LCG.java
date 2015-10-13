//Implementaion of a Linear Congruential Generator (lcg) that decides which of the possible path a driver chooses.
//Is seeded from user input when constructed
public class LCG
{
    private int randNum;

    //Constructor
    public LCG(int s)
    {
        randNum = Math.abs(s); //seed used as starting point for lcg, absolute value taken to ignore negative inputs
    }
    
    //Produce a random number
    public int getNext()
    {
        randNum = (214013 * randNum + 2531011) % (2^31); //constants from microsoft algorithm
        return randNum;
    }
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Airport {

    private final String name="XYZ Airport";
    private int numberFlight=0;
    private Flight[] flightsArray;

    /** variables for bankers algorithm **/

    private int numberProcess;

    // number of resources is limited to 5
    private final int numberResources =5;

    // the origin file of the airport
    private String pathToOriginFile;

    public Airport() {

    }

    public Airport(String pathToOriginFile) {
        this.pathToOriginFile = pathToOriginFile;
        this.makeAirportSystem();
    }

    private void makeAirportSystem() {
        try {
            File originFile=new File(this.pathToOriginFile);
            Scanner sc=new Scanner(originFile);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public int getNumberResources() {
        return numberResources;
    }

    public int getNumberProcess() {
        return numberProcess;
    }

    public void setNumberProcess(int numberProcess) {
        this.numberProcess = numberProcess;
    }

    public int getNumberFlight() {
        return numberFlight;
    }

    public void setNumberFlight(int numberFlight) {
        this.numberFlight = numberFlight;
    }

    public Flight[] getFlightsArray() {
        return flightsArray;
    }

    public void setFlightsArray(Flight[] flightsArray) {
        this.flightsArray = flightsArray;
    }

    public void addFlight(Flight flight) {
        this.flightsArray[numberFlight] = flight;
        numberFlight++;
    }
}

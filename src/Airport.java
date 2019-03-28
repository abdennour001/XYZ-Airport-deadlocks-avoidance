import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Airport {

    private final String name="XYZ Airport";
    private int numberFlight=0;
    private Flight[] flightsArray=new Flight[100];

    /** variables for bankers algorithm **/

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

            this.numberFlight = Integer.parseInt(sc.nextLine());
            String rBuffer=sc.nextLine();
            // read the resource occurrences
            for (int i=0; i<numberResources; i++) {
                Resource.setNumberOccurrences(i, Integer.parseInt(rBuffer.split(" ")[i].trim()));
            }

            sc.nextLine();
            // read the Needed table
            for (int i=0; i<numberFlight; i++) {
                String rBuffer2 = sc.nextLine();
                Flight flight=new Flight("flight"+i+1);
                for (int j=0; j<numberResources; j++) {
                    flight.setResourceNeedArray(j, Integer.parseInt(rBuffer2.split(" ")[j].trim()));
                }
                flightsArray[i] = flight;
            }

            sc.nextLine();
            // read the assigned table
            for (int i=0; i<numberFlight; i++) {
                String rBuffer3 = sc.nextLine();
                for (int j=0; j<numberResources; j++) {
                    flightsArray[i].setAssignedArray(j, Integer.parseInt(rBuffer3.split(" ")[j].trim()));
                }
            }
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

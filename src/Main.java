import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // our xyz airport
    private static Airport xyz;

    // bankers algorithm variable
    private static int numberProcess;
    private static int numberResource;

    // input arrays
    private static int[] existing=new int[100];
    private static int[][] maxNeeded=new int[100][100];
    private static int[][] needed=new int[100][100];
    private static int[][] assigned=new int[100][100];
    private static int[] available=new int[100];
    private static int[] utilization=new int[5];
    private static String safeSequence="";

    /** The main methods of the bankers algorithm **/

    public static boolean isSafe() {
        int[] job;
        boolean[] finish=new boolean[100];

        // initial all resource utilization to zero
        for (int i=0; i<numberResource; i++) {
            utilization[i] = 0;
        }

        job = available;
        // initial all the processes as not finished
        for (int i=0; i<numberProcess; i++) {
            finish[i]= false;
        }
        // look for an unfinished process and the available resources can fulfill it
        while (!allFinished(finish)) {
            boolean found=false;
            for (int i=0; i<numberProcess; i++) {
                if (!finish[i] && amountAvailable(i, job)) {
                    for (int j=0; j<numberResource; j++) {
                        job[j] = job[j] + assigned[i][j];
                        utilization[j] += assigned[i][j];
                    }
                    found=true;
                    safeSequence = safeSequence.concat("P" + i + " ");
                    xyz.getFlightsArray()[i].setRank(getNumberOfFinishedProcesses(finish));
                    finish[i] = true;
                    showAvailableResources(i);
                    break;
                }
            }
            if (!found) break;
        }
        for (int i=0; i<numberProcess; i++) {
            if (!finish[i]) return false;
        }
        return true;
    }

    private static int getNumberOfFinishedProcesses(boolean[] finish) {
        int count=1;
        for (int i=0; i<numberProcess; i++) {
            if (finish[i]) {
                count += maxNeeded[i][0];
            }
        }
        return count;
    }

    private static boolean allFinished(boolean[] finish) {
        for (int i=0; i<numberProcess; i++) {
            if (!finish[i]) {
                return false;
            }
        }
        return true;
    }


    /**
     * Method of checking that the process i can be fulfilled by the resources in job array
     *
     * @param pid number of the process
     * @param job the array of resources
     *
     * @return true if the amount is available
     *
     * */
    private static boolean amountAvailable(int pid, int[] job) {
        for (int j = 0; j<numberResource; j++) {
            if (needed[pid][j] > job[j]) {
                return false;
            }
        }
        return true;
    }


    public void banker(int pid, int[] request) {
        for (int i=0; i<numberProcess; i++) {
            for (int j=0; j<numberResource; j++) {
                needed[i][j] = existing[j] - assigned[i][j];
            }
        }

        if (requestedAmountViolation(pid, request)) {
            throw new RuntimeException("Request is greater than the original need.");
        } else {
            if (requestedAmountAvailable(request)) {
                // available = available - request
                for (int j=0; j<numberResource; j++) {
                    available[j] = available[j] - request[j];
                }
                // assigned = assigned + request
                for (int j=0; j<numberResource; j++) {
                    assigned[pid][j] = assigned[pid][j] + request[j];
                }
                // needed = needed - request
                for (int j=0; j<numberResource; j++) {
                    needed[pid][j] = needed[pid][j] - request[j];
                }
                if (isSafe()) {
                    System.out.println("Request granted");
                } else {
                    System.out.println("Request refused");
                    // reallocate the resources and restore the previous state of the system
                    // available = available + request
                    for (int j=0; j<numberResource; j++) {
                        available[j] = available[j] + request[j];
                    }
                    // assigned = assigned - request
                    for (int j=0; j<numberResource; j++) {
                        assigned[pid][j] = assigned[pid][j] - request[j];
                    }
                    // needed = needed + request
                    for (int j=0; j<numberResource; j++) {
                        needed[pid][j] =  needed[pid][j] + request[j];
                    }
                }
            }
        }
    }

    private boolean requestedAmountAvailable(int[] request) {
        for (int j=0; j<numberResource; j++) {
            if (request[j] > available[j]) return false;
        }
        return true;
    }

    private boolean requestedAmountViolation(int pid, int[] request) {
        for (int j = 0; j<numberResource; j++) {
            if (request[j] > needed[pid][j]) {
                return true;
            }
        }
        return false;
    }

    public static void initAirportArrays() {
        numberProcess = xyz.getNumberFlight();
        numberResource = xyz.getNumberResources();
        for (int i=0; i<numberResource; i++) {
            existing[i] = Resource.getNumberOccurrences(i);
            available[i] = Resource.getNumberOccurrences(i);
        }

        for (int i=0; i<numberProcess; i++) {
            for (int j=0; j<numberResource; j++) {
                needed[i][j] = xyz.getFlightsArray()[i].getResourceNeedArray(j);
                maxNeeded[i][j] = needed[i][j];
                assigned[i][j] = xyz.getFlightsArray()[i].getAssignedArray(j);
                available[j] = available[j] - assigned[i][j];
                needed[i][j] = maxNeeded[i][j] - assigned[i][j];
            }
        }
    }

    private static void showSafeSequence() {
        System.out.print("Safe Sequence: ");
        System.out.println(safeSequence);
    }

    public static void showAvailableResources(int t) {
        System.out.print("Time T (Process "+t+") Available: ");
        for (int i=0; i<numberResource; i++) {
            System.out.print(available[i] + " ");
        }
        System.out.println();
    }

    public static  void main(String[] args) {

        xyz=new Airport("src/origin/xyz_airport.txt");
        initAirportArrays();
        if (isSafe()) {
            showSafeSequence();
            showUtilizationArray();
            calculateWaiting();
            calculateAverage();
        } else {
            System.out.println("The system is unsafe!");
        }
    }

    private static void showUtilizationArray() {
        System.out.print("Utilization: ");
        for (int i=0; i<numberResource; i++) {
            System.out.print(utilization[i] + " ");
        }
        System.out.println();
    }

    private static void calculateAverage() {
        System.out.print("Average: ");
        int sum=0;
        for (int i=0; i<numberProcess; i++) {
            sum += xyz.getFlightsArray()[i].getRank();
        }
        System.out.println(sum/numberProcess);
    }

    private static void calculateWaiting() {
        System.out.print("Each Flight has wait (hours): ");
        for (int i=0; i<numberProcess; i++) {
            System.out.print(xyz.getFlightsArray()[i].getRank() + " ");
        }
        System.out.println();
    }
}

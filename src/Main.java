import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    // bankers algorithm variable
    private static int numberProcess;
    private static int numberResource;

    // input arrays
    private static int[] existing=new int[100];
    private static int[][] maxNeeded=new int[100][100];
    private static int[][] needed=new int[100][100];
    private static int[][] assigned=new int[100][100];
    private static int[] available=new int[100];

    /** The main methods of the bankers algorithm **/

    public static boolean isSafe() {
        int[] job;
        boolean[] finish=new boolean[100];

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
                    }
                    found=true;
                    finish[i] = true;
                    System.out.print("P" + i + " ");
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

    public static  void main(String[] args) {
        Scanner userInput=new Scanner(System.in);
        // Grab user inputs for number of processes
        System.out.print("Enter the number of processes: ");
        numberProcess = userInput.nextInt();
        // Grab user inputs for number of resources
        System.out.print("Enter the number of resources: ");
        numberResource = userInput.nextInt();
        // Grab user inputs for maximum number of each resource type
        for (int i=0; i<numberResource; i++) {
            System.out.printf("Enter the maximum number of resource type %d : ", i+1);
            int r = userInput.nextInt();
            existing[i] = r;
            available[i] = r;
        }

        // Grab user inputs for maximum number of each resource that can be claimed by each process
        for (int i=0; i<numberProcess; i++) {
            for (int j=0; j<numberResource; j++) {
                System.out.printf("Enter the maximum number of resource %d that can be claimed by process %d: ", j+1, i+1);
                int max=userInput.nextInt();
                maxNeeded[i][j] = max;
            }
        }

        // Grab user inputs for possession of each resource by each process at time Ti
        for (int i=0; i<numberProcess; i++) {
            for (int j=0; j<numberResource; j++) {
                System.out.printf("Enter the number of resources of type %d allocated to process %d at time T0: ", j+1, i+1);
                int alloc=userInput.nextInt();
                assigned[i][j] = alloc;
                available[j] = available[j] - alloc;
                needed[i][j] = maxNeeded[i][j] - alloc;
            }
        }

        System.out.println(isSafe());
    }
}

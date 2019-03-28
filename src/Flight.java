public class Flight extends Process {

    private String name;
    private int[] resourceNeedArray=new int[5];
    private int[] assignedArray=new int[5];
    private int rank;

    public Flight() {

    }

    public Flight(String s) {
        this.name = s;
    }


    public void setResourceNeedArray(int rid, int need) {
        this.resourceNeedArray[rid] = need;
    }

    public void setAssignedArray(int rid, int ass) {
        this.assignedArray[rid] = ass;
    }

    public int getResourceNeedArray(int rid) {
        return resourceNeedArray[rid];
    }

    public int getAssignedArray(int rid) {
        return assignedArray[rid];
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

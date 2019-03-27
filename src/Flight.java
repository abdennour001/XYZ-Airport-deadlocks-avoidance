public class Flight extends Process {

    private String name;
    private int[] resourceNeedArray=new int[5];

    public Flight() {

    }

    public void setResourceNeedArray(int rid, int need) {
        this.resourceNeedArray[rid] = need;
    }

    public int getResourceNeedArray(int rid) {
        return resourceNeedArray[rid];
    }
}

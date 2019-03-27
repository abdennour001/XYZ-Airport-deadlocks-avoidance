public class Resource {

    private final static int ENTRANCE_CHECKPOINT=1;
    private final static int CHECK_IN_COUNTER_GATE=2;
    private final static int PASSPORT_PERMIT=3;
    private final static int DEPARTURE_TERMINAL=4;
    private final static int PLANE_ENTRY_CHECK_POINT=5;

    private int[] ResourceOccurrences=new int[5];

    public void setNumberOccurrences(int rid, int num) {
        this.ResourceOccurrences[rid] = num;
    }

    public int getNumberOccurrences(int rid) {
        return this.ResourceOccurrences[rid];
    }
}

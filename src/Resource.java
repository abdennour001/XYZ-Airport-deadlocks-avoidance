public class Resource {

    public final static int ENTRANCE_CHECKPOINT=0;
    public final static int CHECK_IN_COUNTER_GATE=1;
    public final static int PASSPORT_PERMIT=2;
    public final static int DEPARTURE_TERMINAL=3;
    public final static int PLANE_ENTRY_CHECK_POINT=4;

    private static int[] ResourceOccurrences=new int[5];

    public static void setNumberOccurrences(int rid, int num) {
        ResourceOccurrences[rid] = num;
    }

    public static int getNumberOccurrences(int rid) {
        return ResourceOccurrences[rid];
    }
}

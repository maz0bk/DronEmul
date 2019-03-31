import java.util.HashMap;

public class DronStub {
    private final TrackData track;
    private final int dronNo;

    private static final HashMap<Integer, DronStub> drons = new HashMap(10);

    private DronStub(int dronNo) {
        this.dronNo = dronNo;
        track = new TrackData(dronNo);
    }

    public static DronData nextPoint(int dronNo) {
        if (!drons.containsKey(dronNo)) {
            DronStub d = new DronStub(dronNo);
            drons.put(dronNo, d);
        }
        return drons.get(dronNo).track.nextPoint();
    }

    public int getTracLen() {
        return track.getLstLen();
    }

    public DronData getTrackItem(int i) {
        return track.getLstItem(i);
    }
}

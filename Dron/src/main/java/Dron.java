public class Dron {

    private int id;
    private String uid;
    private boolean isFly;
    private DronData currentPosition;

    public boolean isFly() {
        return isFly;
    }

    public Dron(int id, String uid) {
        this.id = id;
        this.uid = uid;
        this.isFly = true;

    }

    public DronData getCurrentPosition(){
        currentPosition = DronStub.nextPoint(id);
        currentPosition.setIdUID(Integer.toString(id),uid);
        return currentPosition;

    }

    public String getUid() {
        return uid;
    }

    public int getId() {
        return id;
    }
}

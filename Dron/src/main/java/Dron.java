public class Dron {

    private int id;
    private String uid;
    private boolean isFly;

    public boolean isFly() {
        return isFly;
    }

    public Dron(int id, String uid) {
        this.id = id;
        this.uid = uid;
        this.isFly = true;

    }

    public DronData getCurrentPosition(){

    return new DronData();
    }

    public String getUid() {
        return uid;
    }

    public int getId() {
        return id;
    }
}

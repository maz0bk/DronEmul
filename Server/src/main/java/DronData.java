import java.io.Serializable;

public class DronData implements Serializable{
    private double lat, lon, h;
    long t;

    public DronData(){
        this.lat = 55.743812;
        this.lon = 37.872963;
        this.h = 156;
        this.t = System.currentTimeMillis();

    }
    public DronData(double lat, double lon, double h, long t) {
        this.lat = lat;
        this.lon = lon;
        this.h = h;
        this.t = t;
    }

    public double getH() {
        return h;
    }

    public long getT() {
        return t;
    }
    //    {
//            "dronID": "1",
//            "dronUID": "1234 5678 9000 0000",
//            "coordinates": {
//                "lat": "55.74381200",
//                "lon": "37.87296300",
//                "h": "156",
//                "t":"2355844586682"
//    }
}

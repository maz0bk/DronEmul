import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;

public class TrackData {
    private DronData lst[];
    private int dronNo;

    public DronData nextPoint() {
        if (lst.length < 2) {
            return null;
        }

        long tim = new Date().getTime();
        long tim2;
        DronData dd;
        for (int i = 0; i < lst.length; i++) {
            dd = lst[i];
            tim2 = dd.getT();
            if (tim2 >= tim) {
                while (tim2 >= new Date().getTime()) {
                    try {
                        Thread.sleep(tim2 - new Date().getTime() + 1);
                    } catch (InterruptedException e) {
                    }
                    return dd;
                }
            }
        }

        // дошли до конца массива, запускаем дрон в обратную сторону
        DronData lst2[] = new DronData[lst.length];
        tim = lst[lst.length - 1].getT() * 2;
        for (int i = 0; i < lst.length; i++) {
            lst[i].setT(tim - lst[i].getT());
            lst2[lst.length - i - 1] = lst[i];
        }
        lst = lst2;
        return nextPoint();
    }

    public TrackData(int dronNo) {
        this.dronNo = dronNo;
        String fname = "dron\\assets\\" + dronNo + ".plt";
        if (!loadFile(fname)) {
            lst = new DronData[0];
        }
    }

    private static double SPEED = 5;

    private boolean loadFile(String fname) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fname), StandardCharsets.UTF_8))) {
            String line;
            int skip = 6;
            ArrayList<DronData> dat = new ArrayList(100);
            String ss[];
            long tim;
            long tim0 = new Date().getTime() + 100;
            double tim1 = 0, t;
            boolean firstTime = true;

            while ((line = reader.readLine()) != null) {
                if (skip > 0) {
                    skip--;
                    continue;
                }

                ss = line.split(",");
                t = Double.parseDouble(ss[4]) - 25567;
                if (firstTime) {
                    tim1 = t;
                    firstTime = false;
                }
                t = (t - tim1) / SPEED;
                tim = (long) (t * 86400000);
                tim += tim0;

                dat.add(new DronData(Double.parseDouble(ss[0]),
                        Double.parseDouble(ss[1]),
                        Double.parseDouble(ss[3]) / 3.28,
                        tim));
            }

            int nn = dat.size();
            lst = new DronData[nn];

            for (int i = 0; i < nn; i++) {
                lst[i] = dat.get(i);
            }
            return true;
        } catch (IOException e) {
            System.err.println("error accesing file " + fname + ": " + e.toString());
            return false;
        }
    }

    public int getLstLen() {
        return lst.length;
    }

    public DronData getLstItem(int i) {
        return lst[i];
    }

    public int getDronNo() {
        return dronNo;
    }
}

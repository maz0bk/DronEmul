import java.io.*;
import java.net.Socket;

public class MainDron {
    private static Socket socket;
    private static DataInputStream in;
    private static ObjectOutputStream out;

    public static void main(String[] args) {
        System.out.println(new File(".").getAbsolutePath());
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            Thread clientListenerThread = new Thread(() -> {
                try {
                    while (true) {
                        String msg = in.readUTF();
                        if (msg.startsWith("/authok ")) {
//                            callOnAuthentificated.callback(msg.split("\\s")[1]);
                            break;
                        }
                    }
                    while (true) {
                        //тут должны можем получать управляющее воздейстиве
                        String msg = in.readUTF();
                        if (msg.equals("/end")) {
                            break;
                        }
//                        callOnMsgReceived.callback(msg);
                    }
                } catch (IOException e) {
//                    callOnException.callback("Соединение с сервером разорвано");
                } finally {
                    closeConnection();
                }
            });
            clientListenerThread.setDaemon(true);
            clientListenerThread.start();
            Dron dron = new Dron(2, "b");
            String authStr= "//auth "+dron.getId() +" "+dron.getUid();
            while(!sendAuth(authStr));
            while (dron.isFly()){
                DronData dt = dron.getCurrentPosition();

                System.out.println(dt.getJson());
//               sendMsg(dron.getCurrentPosition());

            //отправляем данные оператору или клиенту
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean sendAuth(String auth) {
        try {
            out.writeObject(auth);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean sendMsg(DronData dt) {
        try {
            out.writeObject(dt);
//            Thread.sleep(10000);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void closeConnection() {
//        callOnCloseConnection.callback();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

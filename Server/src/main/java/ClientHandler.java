import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private ObjectInputStream in;
    private DataOutputStream out;

    public ClientHandler(Server server, Socket socket, ExecutorService exService) throws IOException {

        this.server = server;
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        exService.execute(() -> {
            try {
                while (!checkAuth()) ;
                while (readMessage()) ;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                ClientHandler.this.disconnect();
            }
        });
    }

    private boolean readMessage() throws IOException {

        try {
            DronData dt = (DronData) in.readObject();
            System.out.println("h="+dt.getH()+" T="+dt.getT());

        } catch (ClassNotFoundException e) {
            return false;
        }

        return true;

    }

    public void disconnect() {
        server.unsubscribe(this);
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

    private boolean checkAuth() throws IOException {
        String msg = null;
        try {
            msg = (String)in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // /auth iddroid uid
        if (msg.startsWith("/auth ")) {
            String[] tokens = msg.split("\\s");
            return server.getAuthService().getDroidByIDAndUID(tokens[1], tokens[2]);

            }
        return false;
    }
}

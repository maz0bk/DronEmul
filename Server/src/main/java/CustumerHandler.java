import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class CustumerHandler {
    private Server server;
    private Socket socket;
    private ObjectInputStream in;
    private DataOutputStream out;

    public CustumerHandler(Server server, Socket socket, ExecutorService exService) throws IOException {

        this.server = server;
        this.socket = socket;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        exService.execute(() -> {
//            try {
//                while (!checkAuth()) ;
//                while (readMessage()) ;
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                CustumerHandler.this.disconnect();
//            }
        });
    }
}

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Vector <ClientHandler> droids;
    private Vector <OperatorHandler> operators;
    private ExecutorService exService;
    private AuthService authService;

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        this.droids = new Vector<>();

        if ( !SQLHandler.connect() ){
            throw new RuntimeException("Can't connect to database");
        }
        exService = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(8189)) {
//            System.out.println("Сервер запущен на порту 8189");
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket, exService);
//                System.out.println("Подключился новый клиент");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            System.out.println("Сервер завершил свою работу");
            SQLHandler.disconnect();
            exService.shutdown();
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        droids.add(clientHandler);
//        broadcastClientsList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        droids.remove(clientHandler);
//        broadcastClientsList();
    }
}

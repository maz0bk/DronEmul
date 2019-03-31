import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private Vector <ClientHandler> droids;
    private Vector <OperatorHandler> operators;
    private Vector <CustumerHandler> custumers;
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
        authService = new SQLAuth();
        exService = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(8189);
//        ServerSocket serverSoceketOper = new ServerSocket(8190);
//        ServerSocket serverSoceketCustumer = new ServerSocket(8191)
        ) {
//            System.out.println("Сервер запущен на порту 8189");
            while (true) {
                Socket socket = serverSocket.accept();
                subscribe(new ClientHandler(this, socket, exService));
//                System.out.println("Подключился новый клиент");
            }
//            new Thread(() -> {
//                try {
//                    while (true) {
//                        Socket socket = serverSocket.accept();
//                        subscribe(new ClientHandler(this, socket, exService));
////                System.out.println("Подключился новый клиент");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();

//            new Thread(() -> {
//                try {
//                    while (true) {
//                        Socket socket = serverSoceketOper.accept();
//                        subscribeOper(new OperatorHandler(this, socket, exService));
////                System.out.println("Подключился новый клиент");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//
//            new Thread(() -> {
//                try {
//                    while (true) {
//                        Socket socket = serverSoceketCustumer.accept();
//                        subscribeCustumer(new CustumerHandler(this, socket, exService));
////                System.out.println("Подключился новый клиент");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            System.out.println("Сервер завершил свою работу");
            SQLHandler.disconnect();
            exService.shutdown();
        }
    }

    private void subscribeCustumer(CustumerHandler custumerHandler) {
        custumers.add(custumerHandler);
    }

    public void subscribe(ClientHandler clientHandler) {
        droids.add(clientHandler);
    }

    public void subscribeOper(OperatorHandler operatorHandler) {
        operators.add(operatorHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        droids.remove(clientHandler);

    }
}

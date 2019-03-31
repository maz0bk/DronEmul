import java.sql.*;

public class SQLHandler {
    private static Connection connection;
    private static PreparedStatement psGetByIDadnUID;
    public static boolean connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/droiddelivery?serverTimezone=UTC", "root", "FlySky128#");
            psGetByIDadnUID = connection.prepareStatement("SELECT id FROM droiddelivery.droids where iddroid = ? and uid = ?;");
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getDroidByIDAndUID(String id, String uid) {
        try {

            psGetByIDadnUID.setString(1,id);
            psGetByIDadnUID.setString(2,uid);
            ResultSet rs = psGetByIDadnUID.executeQuery();
            if (rs.next()){
                return true;
            }
            rs.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public static void putTrackToBase(){

    }
}

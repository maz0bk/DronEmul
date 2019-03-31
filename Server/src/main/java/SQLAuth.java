public class SQLAuth implements AuthService {
    @Override
    public boolean getDroidByIDAndUID(String login, String password) {
        return SQLHandler.getDroidByIDAndUID(login,password);
    }
}

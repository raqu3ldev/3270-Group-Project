package Service;

import Model.User;

public interface Authenticable {
    User authenticateUser(String username, String password);
}


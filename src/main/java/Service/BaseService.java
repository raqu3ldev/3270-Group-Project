package Service;

public abstract class BaseService {

    // common helper for error handling
    protected RuntimeException handleException(String action, Exception e) {
        e.printStackTrace();
        return new RuntimeException("Error " + action + ": " + e.getMessage());
    }
}


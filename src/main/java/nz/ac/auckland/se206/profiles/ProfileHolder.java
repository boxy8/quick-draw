package nz.ac.auckland.se206.profiles;

public class ProfileHolder {
    private static ProfileHolder instance;

    public static ProfileHolder getInstance() {
        if (instance == null) {
            instance = new ProfileHolder();
        }
        return instance;
    }

    private String currentUsername;

    private ProfileHolder() {
    }

    public void setCurrentProfile(String username) {
        this.currentUsername = username;
    }

    public String getCurrentProfile() {
        return this.currentUsername;
    }
}

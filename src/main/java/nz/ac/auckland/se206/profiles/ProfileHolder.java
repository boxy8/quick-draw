package nz.ac.auckland.se206.profiles;

public class ProfileHolder {
  private static ProfileHolder instance;

  public static ProfileHolder getInstance() {
    if (instance == null) {
      instance = new ProfileHolder();
    }
    return instance;
  }

  private Profile currentProfile;

  private ProfileHolder() {}

  public void setCurrentProfile(Profile profile) {
    this.currentProfile = profile;
  }

  public Profile getCurrentProfile() {
    return this.currentProfile;
  }

  public void setInstance() {
    instance = null;
  }
}

package fact.it.user_service.model;

public class UserDTO {

    private int userID;
    private String name;
    private String email;
    private int avatarID;
    private int score;

    public UserDTO() {
    }

    public UserDTO(int userID, String name, String email, int avatarID, int score) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.avatarID = avatarID;
        this.score = score;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAvatarID() {
        return avatarID;
    }

    public void setAvatarID(int avatarID) {
        this.avatarID = avatarID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

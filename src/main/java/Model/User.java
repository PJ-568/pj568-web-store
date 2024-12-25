package Model;

public class User {
    private int id;
    private String username;
    private String nickname;
    private String password;
    private int role;

    // 构造函数
    public User(int id, String username, String nickname, String password, int role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
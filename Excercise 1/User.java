public class User {
    private String username;
    private String password;

    // constructor
    User(String uname, String pword){
        this.username = uname;
        this.password = pword;
    }

    // Get Method
    public String getUsername(){
        return this.username;
    }
}

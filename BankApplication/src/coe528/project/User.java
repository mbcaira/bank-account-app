package coe528.project;

/**
 *
 * @author mbcan
 */
public abstract class User {
    private final String username;
    private final String password;
    private final boolean isManager;
    
    /**
     *
     * @param username
     * @param password
     * @param isManager
     */
    public User(String username, String password, boolean isManager){
        this.username = username;
        this.password = password;
        this.isManager = isManager;
    }
    
    /**
     *
     * @return
     */
    public String getName(){
        return this.username;
    }
    
    /**
     *
     * @return
     */
    public String getPassword(){
        return this.password;
    }
}

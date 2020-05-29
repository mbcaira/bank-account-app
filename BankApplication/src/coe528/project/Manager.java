package coe528.project;

/**
 *
 * @author mbcan
 */
public class Manager extends User {
    private static Manager instance;

    /**
     *
     * @param name
     * @param password
     */
    public Manager(String name, String password){
        super(name,password, true);
    }
    
    /**
     *
     * @param name
     * @param password
     * @return
     */
    public static Manager getInstance(String name, String password){
        if (instance == null){
            instance = new Manager(name, password);
        }
        return instance;
    }
}

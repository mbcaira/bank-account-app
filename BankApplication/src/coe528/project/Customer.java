package coe528.project;

/**
 *
 * @author mbcan
 */
public class Customer extends User{
    private Level account;

    /**
     *
     * @param username
     * @param password
     * @param balance
     */
    public Customer(String username, String password, double balance) {
        super(username, password, false);
        this.account = new Silver(balance);
    }
    
    /**
     *
     * @return
     */
    public Level getLevel(){
        return this.account.getLevel();
    }
    
    /**
     *
     * @param l
     */
    public void setLevel(Level l){
        this.account = l;
    }
    
    /**
     *
     * @return
     */
    public double getBalance(){
        return this.account.getBalance();
    }
    
    /**
     *
     * @param amount
     */
    public void setBalance(double amount){
        this.account.setBalance(amount);
    }
    
    /**
     *
     */
    public void changeLevel(){
        this.account.changeLevel(this);
    }
    
    /**
     *
     * @param amount
     */
    public void deposit(double amount){
        this.account.deposit(amount);
        this.changeLevel();
    }
    
    /**
     *
     * @param amount
     * @return
     */
    public boolean withdraw(double amount){
        if (this.account.withdraw(amount)){
            System.out.println("Withdrawal completed.");
            this.changeLevel();
            return true;
        }
        else{
            System.out.println("Withdrawal denied, insufficient balance.");
            return false;
        }
    }
    
    /**
     *
     * @param amount
     * @return
     */
    public boolean onlineShop(double amount){
        if (this.account.onlineShop(amount)){
            System.out.println("Online shop completed.");
            this.changeLevel();
            return true;
        }
        else{
            System.out.println("Online shop failed.");
            return false;
        }
    }
    
    /**
     *
     * @return
     */
    public String levelName(){
        return this.account.levelName();
    }
}
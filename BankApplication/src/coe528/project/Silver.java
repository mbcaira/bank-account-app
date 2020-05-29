/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

/**
 *
 * @author mbcan
 */
/** Overview:         
    * A Silver level is a mutable sub-class of Level that contains specific
    * behaviour to a Silver account state. 
    *
    * The abstraction function is:
    * AF(c) = a silver level such that
    *      {p = balance &&
    *       c.getBalance() == p.balance}       
    *               
    *
    * The rep invariant is:
    * RI(c) = true when
    *           {0 <= c.balance < 10000}  
    *         false otherwise
    */
public class Silver extends Level {
    /**
     *
     * @param balance
     */
    public Silver(double balance) {
        super(balance);
    }

    /**
     *
     * @param c
     * MODIFIES: The level of "Customer c"
     * EFFECTS: Should the balance fall out of bounds of the Silver level (!(0 <= this.balance < 10000))
     *          the Customer c's level is changed to a different level depending on its balance.
     */
    @Override
    public void changeLevel(Customer c) {
        if (this.balance >= 10000 && this.balance < 20000){
            c.setLevel(new Gold(this.balance));
        }
        else if (this.balance >= 20000){
            c.setLevel(new Platinum(this.balance));
        }
    }

    /**
     *
     * @param amount
     * @return
     * REQUIRES:    A positive "amount" input
     * EFFECTS:     If the "amount" is greater than or equal to 50,the "amount"+20
     *              is deducted from the balance, and returns true, otherwise it
     *              returns false. If repOk() is no longer true, the Level is changed
     *              accordingly.
     */
    @Override
    public boolean onlineShop(double amount) {
        if (amount >= 50 && amount+20 <= this.balance){
            this.withdraw(amount+20);
            System.out.println("Made Silver online purchase.");
            return true;
        }
        return false;
    }
    
    /**
     *
     * @return
     * EFFECTS:     Returns a copy of the current level (Silver)    
     */
    @Override
    public Level getLevel(){
        Level l = new Silver(this.balance);
        return l;
    }

    /**
     *
     * @return
     * EFFECTS:     Returns the name of the level as a String
     */
    @Override
    public String levelName() {
        return "Silver";
    }
    
    /**
     *
     * @return
     * EFFECTS:     Checks if the rep invariant is satisfied and returns true
     *              if it does and false otherwise
     */
    public boolean repOk(){
        if (this.balance >=0 && this.balance < 10000){
            return true;
        }
        return false;
    }
    
    /**
     *
     * @return
     * EFFECTS:     Returns the name of the level and the balance as a String
     */
    @Override
    public String toString(){
        return ("Level name: Silver\nBalance: "+this.balance);
    }
}

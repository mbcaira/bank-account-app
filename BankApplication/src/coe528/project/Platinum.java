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
public class Platinum extends Level {

    /**
     *
     * @param balance
     */
    public Platinum(double balance) {
        super(balance);
    }

    /**
     *
     * @param c
     */
    @Override
    public void changeLevel(Customer c) {
        if (this.balance < 20000 && this.balance >= 10000){
            c.setLevel(new Gold(this.balance));
        }
        else if (this.balance < 10000){
            c.setLevel(new Silver(this.balance));
        }
    }

    /**
     *
     * @param amount
     * @return
     */
    @Override
    public boolean onlineShop(double amount) {
        if (amount >= 50){
            this.withdraw(amount);
            System.out.println("Made Platinum online purchase.");
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    @Override
    public Level getLevel() {
        Level l = new Platinum(this.balance);
        return l;
    }

    /**
     *
     * @return
     */
    @Override
    public String levelName() {
        return "Platinum";
    }

}

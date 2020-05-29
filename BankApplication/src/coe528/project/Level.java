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
public abstract class Level {

    /**
     *
     */
    protected double balance;
    
    /**
     *
     * @param c
     */
    public abstract void changeLevel(Customer c);

    /**
     *
     * @param amount
     * @return
     */
    public abstract boolean onlineShop(double amount);

    /**
     *
     * @return
     */
    public abstract Level getLevel();

    /**
     *
     * @return
     */
    public abstract String levelName();
    
    /**
     *
     * @param balance
     */
    public Level (double balance){
        this.balance = balance;
    }
    
    /**
     *
     * @param amount
     */
    public void deposit(double amount){
        this.balance+=amount;
    }
    
    /**
     *
     * @param amount
     * @return
     */
    public boolean withdraw(double amount){
        if (this.balance >= amount){
            this.balance -= amount;
            return true;
        }
        else{
            return false;
        }
    }
    
    /**
     *
     * @return
     */
    public double getBalance(){
        return this.balance;
    }
    
    /**
     *
     * @param amount
     */
    public void setBalance(double amount){
        this.balance = amount;
    }    
}

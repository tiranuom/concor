package com.tix.trx.mobilemoneytracker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallet")
public class Wallet {

    @Id
    private String msisdn;
    private long balance;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public static Wallet initWallet(String msisdn) {
        Wallet wallet = new Wallet();
        wallet.setMsisdn(msisdn);
        wallet.setBalance(0);
        return wallet;
    }
}

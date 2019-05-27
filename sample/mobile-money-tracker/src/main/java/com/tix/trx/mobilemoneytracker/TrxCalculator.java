package com.tix.trx.mobilemoneytracker;

import com.tix.trx.mobilemoneytracker.domain.Request;

public class TrxCalculator {

    public void apply(Request request) {
        long balance = request.getWallet().getBalance();
        long value = request.getValue();

        switch (request.getOperation()) {
            case "CREDIT":
                request.getWallet().setBalance(balance + value);
                request.setStatus("UPDATED: " + (balance + value));
                break;
            case "DEBIT":
                if (balance < value) {
                    request.setStatus("INCOMPLETE");
                } else {
                    request.getWallet().setBalance(balance - value);
                    request.setStatus("UPDATED: " + (balance - value));
                }
                break;
        }
    }
}

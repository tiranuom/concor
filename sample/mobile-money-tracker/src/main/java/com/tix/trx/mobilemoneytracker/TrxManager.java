package com.tix.trx.mobilemoneytracker;

import com.tix.trx.mobilemoneytracker.domain.Request;
import com.tix.trx.mobilemoneytracker.domain.Wallet;
import com.tix.trx.mobilemoneytracker.repo.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.rmi.Remote;
import java.util.Optional;

public class TrxManager {

    @Autowired
    private WalletRepo walletRepo;


    public Request read(Request request) {
        Optional<Wallet> wallet = walletRepo.findById(request.getMsisdn());
        if (!wallet.isPresent()) {
            walletRepo.save(Wallet.initWallet(request.getMsisdn()));
            wallet = walletRepo.findById(request.getMsisdn());
        }
        wallet.ifPresent(request::setWallet);
        return request;
    }

    public Request update(Request request) {
        walletRepo.save(request.getWallet());
        return request;
    }
}

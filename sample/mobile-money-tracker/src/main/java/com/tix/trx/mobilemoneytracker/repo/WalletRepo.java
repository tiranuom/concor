package com.tix.trx.mobilemoneytracker.repo;

import com.tix.trx.mobilemoneytracker.domain.Wallet;
import org.springframework.data.repository.CrudRepository;

public interface WalletRepo extends CrudRepository<Wallet, String> {
}

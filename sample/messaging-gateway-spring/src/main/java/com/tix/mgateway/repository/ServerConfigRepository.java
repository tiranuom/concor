package com.tix.mgateway.repository;

import com.tix.mgateway.repository.domain.RemoteInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ServerConfigRepository extends CrudRepository<RemoteInfo, Integer> {

    Optional<RemoteInfo> findByRemoteId(String remoteId);
}

package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SynchronizedRemoteTask;
import com.tix.mgateway.mo.domain.MOMessage;
import com.tix.mgateway.repository.ServerConfigRepository;
import com.tix.mgateway.repository.domain.RemoteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class EndPointResolver implements SynchronizedRemoteTask<MOMessage, MOMessage> {

    private static final Logger logger = LoggerFactory.getLogger(EndPointResolver.class);

    private Map<String, Optional<RemoteInfo>> remoteInfoCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
//        Executors.newSingleThreadScheduledExecutor()
//                .scheduleAtFixedRate(() -> remoteInfoCache.clear(), 1, 1, TimeUnit.SECONDS);
    }

    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {

        logger.debug("Resolving destination information");

        Optional<RemoteInfo> remoteInfo  = remoteInfoCache
                .computeIfAbsent(moMessage.getSession().getApplication(), serverConfigRepository::findByRemoteId);

//        Optional<RemoteInfo> remoteInfo = serverConfigRepository.findByRemoteId(moMessage.getSession().getApplication());

        remoteInfo.ifPresent(moMessage.getSession()::setApplicationInfo);

        logger.debug("Destination information resolved");
        return moMessage;
    }
}

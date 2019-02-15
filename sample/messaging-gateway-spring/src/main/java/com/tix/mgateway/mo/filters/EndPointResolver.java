package com.tix.mgateway.mo.filters;

import com.tix.concor.core.framework.task.SynchronizedRemoteTask;
import com.tix.mgateway.mo.domain.MOMessage;
import com.tix.mgateway.repository.ServerConfigRepository;
import com.tix.mgateway.repository.domain.RemoteInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class EndPointResolver implements SynchronizedRemoteTask<MOMessage, MOMessage> {


    @Autowired
    private ServerConfigRepository serverConfigRepository;

    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {
        Optional<RemoteInfo> remoteInfo = serverConfigRepository.findByRemoteId(moMessage.getSession().getApplication());
        remoteInfo.ifPresent(moMessage.getSession()::setApplicationInfo);
        return moMessage;
    }
}

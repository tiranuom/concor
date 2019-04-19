package com.tix.mgateway;

import com.tix.mgateway.mo.domain.Session;

public interface SessionManagerI {
    Session getSession(String sessionKey);
}

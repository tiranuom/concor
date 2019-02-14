package com.tix.mgateway;

import com.tix.mgateway.mo.domain.Session;

public class SessionManager {

    private Trie trie = new Branch();
    private long sessionTimeout = 60000;

    public SessionManager(long sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Session getSession(String sessionKey) {
        return trie.getSession(sessionKey, 0);
    }

    public void invalidate(String sessionKey) {
        trie.invalidate(sessionKey, 0);
    }

    private interface Trie {
        Session getSession(String sessionKey, int index);
        boolean invalidate(String sessionKey, int index);
    }

    private class Branch implements Trie {

        private Trie[] tries = new Trie[128];
        private int count = 0;

        @Override
        public Session getSession(String sessionKey, int index) {
            char ch = sessionKey.charAt(index);
            if (tries[ch] == null) {
                if (index == sessionKey.length() - 1) {
                    tries[ch] = new Leaf();
                } else {
                    tries[ch] = new Branch();
                }
            }
            count++;
            return tries[ch].getSession(sessionKey, ++index);
        }

        @Override
        public boolean invalidate(String sessionKey, int index) {
            char ch = sessionKey.charAt(index);
            if (tries[ch] != null) {
                boolean availableForInvalidate = tries[ch].invalidate(sessionKey, ++index);
                if (availableForInvalidate) {
                    if (count == 1) {
                        return true;
                    } else {
                        tries[ch] = null;
                        count--;
                    }
                }
            }
            return false;
        }
    }

    private class Leaf implements Trie {

        private Session session;

        @Override
        public Session getSession(String sessionKey, int index) {
            if (session == null || !session.isValid(sessionTimeout)) {
                session = new Session(sessionKey);
            } 
            return session;
        }

        @Override
        public boolean invalidate(String sessionKey, int index) {
            return true;
        }
    }

}

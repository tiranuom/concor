package com.tix.mgateway.mo.filters.router;

import com.tix.concor.core.framework.task.SimpleTask;
import com.tix.mgateway.mo.domain.MOMessage;
import com.tix.mgateway.mo.filters.router.domain.Route;

import java.util.List;

public class MORouter implements SimpleTask<MOMessage, MOMessage> {

    private Trie trie = new Trie();

    @Override
    public MOMessage apply(MOMessage moMessage) throws Throwable {
        Route route = trie.findRoute(moMessage.getKey());
        moMessage.getSession().setApplication(route.getDestination());
        return moMessage;
    }

    public void refresh(List<Route> routes) {
        this.trie = new Trie();
        populate(routes);
    }

    public void populate(List<Route> routes) {
        routes.forEach(trie::populate);
    }

    private static class Trie {

        private Trie[] tries = new Trie[127];
        private Route route = null;

        private Route findRoute(String key, int index, Route defaultRoute) {

            Route route = this.route == null ? defaultRoute : this.route;

            if (key.length() == index) {
                return route;
            }

            Trie trie = tries[key.charAt(index)];

            if (trie == null) {
                return route;
            } else {
                return trie.findRoute(key, ++index, route);
            }
        }

        private Route findRoute(String key) {
            return findRoute(key, 0, null);
        }

        private void populate(Route route, int index) {
            if (route.getPrefix().length() == index) {
                this.route = route;
            } else {
                char c = route.getPrefix().charAt(index);
                tries[c] = new Trie();
                tries[c].populate(route, ++index);
            }
        }

        private void populate(Route route) {
            populate(route, 0);
        }
    }
}
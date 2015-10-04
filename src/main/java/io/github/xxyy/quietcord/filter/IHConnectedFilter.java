package io.github.xxyy.quietcord.filter;

import net.md_5.bungee.config.Configuration;

import io.github.xxyy.quietcord.QuietCordPlugin;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A simple log filter that cancels messages stating that a client has connected to InitialHandler. These messages are
 * sent when a client sends a server list request packet (i.e. evey time somebody opens their server list) and when
 * a client starts its connection to the server.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 02/10/15
 */
public class IHConnectedFilter extends PropagatingFilter {
    private boolean filterPings;
    private boolean filterJoins;

    public IHConnectedFilter(QuietCordPlugin plugin) {
        super(plugin.getProxy().getLogger());
        loadConfig(plugin.getConfig());
    }

    IHConnectedFilter(Logger logger) { //for unit tests
        super(logger);
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean isLoggable(LogRecord record) {
        if ((!filterPings && !filterJoins) || //nothing to filter
                !"{0} has connected".equals(record.getMessage()) || //wrong message
                record.getParameters().length != 1 //that message always has exactly one argument
                ) {
            return true;
        }

        String param1 = String.valueOf(record.getParameters()[0]);
        if (param1.endsWith("<-> InitialHandler")) {
            if (filterPings && param1.contains("/")) { //ip addresses contain / in this impl
                return false;                         // e.g. /127.0.0.1:17835 (Java thing)
            }
            if (filterJoins && !param1.contains("/")) { //everything else connecting to
                return false;                          // InitialHandler must be a join
            }
        }

        return true;
    }

    /**
     * Loads the configuration options for this filter from the specified configuration.
     *
     * @param config the configuration file to read from
     */
    public void loadConfig(Configuration config) {
        filterPings = config.getBoolean("filter.initialhandler.ping", true);
        filterJoins = config.getBoolean("filter.initialhandler.join", false);
    }

    @Override
    public String toString() {
        return "IHConnectedFilter{" +
                "filterPings=" + filterPings +
                ", filterJoins=" + filterJoins +
                '}';
    }
}

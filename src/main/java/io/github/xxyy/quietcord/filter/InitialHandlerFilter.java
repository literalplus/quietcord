package io.github.xxyy.quietcord.filter;

import net.md_5.bungee.config.Configuration;

import io.github.xxyy.quietcord.QuietCordPlugin;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A simple log filter that cancels connection messages from InitialHandler, namely
 * these sent at server list ping.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 02/10/15
 */
public class InitialHandlerFilter extends PropagatingFilter {
    private boolean filterPings;
    private boolean filterJoins;

    public InitialHandlerFilter(QuietCordPlugin plugin) {
        super(plugin.getProxy().getLogger());
        loadConfig(plugin.getConfig());
    }

    InitialHandlerFilter(Logger logger) { //for unit tests
        super(logger);
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean isLoggable(LogRecord record) {
        if (!"{0} has connected".equals(record.getMessage()) || //wrong message
                record.getParameters().length != 1 || //that message always has exactly one argument
                (!filterPings && !filterJoins) //nothing to filter
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
        return "InitialHandlerFilter{" +
                "filterPings=" + filterPings +
                ", filterJoins=" + filterJoins +
                '}';
    }
}

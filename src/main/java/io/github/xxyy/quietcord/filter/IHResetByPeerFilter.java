package io.github.xxyy.quietcord.filter;

import net.md_5.bungee.config.Configuration;

import io.github.xxyy.quietcord.QuietCordPlugin;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A simple log filter that cancels messages stating that an InitialHandler connection has been reset due to an
 * IOException with the message "connection reset by peer" (or a localised equivalent specified by the user).
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 02/10/15
 */
public class IHResetByPeerFilter extends PropagatingFilter {
    public static final String DEFAULT_MESSAGE = "connection reset by peer";
    private boolean filterResetByPeer;
    private String resetByPeerMessage;

    public IHResetByPeerFilter(QuietCordPlugin plugin) {
        super(plugin.getProxy().getLogger());
        loadConfig(plugin.getConfig());
    }

    IHResetByPeerFilter(Logger logger) { //for unit tests
        super(logger);
    }

    @Override
    @SuppressWarnings("SimplifiableIfStatement")
    public boolean isLoggable(LogRecord record) {
        //BungeeCord source: https://github.com/SpigotMC/BungeeCord/blob/master/proxy/src/main/java/net/md_5/bungee/netty/HandlerBoss.java#L109
        return !filterResetByPeer || //we might not even be filtering
                !"{0} - IOException: {1}".equals(record.getMessage()) || //wrong message
                record.getParameters().length != 2 || //that message always has exactly two arguments
                !"InitialHandler".equals(String.valueOf(record.getParameters()[0])) || //first arg is handler
                !resetByPeerMessage.equals(String.valueOf(record.getParameters()[1])); //second arg is message
    }

    /**
     * Loads the configuration options for this filter from the specified configuration.
     *
     * @param config the configuration file to read from
     */
    public void loadConfig(Configuration config) {
        filterResetByPeer = config.getBoolean("filter.initialhandler.reset-by-peer", false);
        resetByPeerMessage = config.getString("filter.initialhandler.reset-by-peer-message", DEFAULT_MESSAGE);
    }

    @Override
    public String toString() {
        return "IHResetByPeerFilter{" +
                "message=" + resetByPeerMessage +
                '}';
    }
}

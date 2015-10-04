package io.github.xxyy.quietcord.filter;

import net.md_5.bungee.config.Configuration;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IHConnectedFilterTest {
    public static final String PING_PARAM = "[/127.0.0.1:2394] <-> InitialHandler";
    public static final String JOIN_PARAM = "[Literallie] <-> InitialHandler";
    public static final String ARBITRARY_MESSAGE = "Enabled plugin {0} version {1}";
    public static final String HAS_CONNECTED = "{0} has connected";

    @Test
    public void testIsLoggable__ping() throws Exception {
        IHConnectedFilter filter = setupFilter(true, false);

        assertThat("arbitrary log message wrongly matched with ping",
                filter.isLoggable(rec(ARBITRARY_MESSAGE)), is(true));

        assertThat("ping message not matched with ping",
                filter.isLoggable(rec(HAS_CONNECTED, PING_PARAM)), is(false));

        assertThat("join message wrongly matched with ping",
                filter.isLoggable(rec(HAS_CONNECTED, JOIN_PARAM)), is(true));
    }

    @Test
    public void testIsLoggable__join() throws Exception {
        IHConnectedFilter filter = setupFilter(false, true);

        assertThat("arbitrary log message wrongly matched with join",
                filter.isLoggable(rec(ARBITRARY_MESSAGE)), is(true));

        assertThat("ping message wrongly matched with join",
                filter.isLoggable(rec(HAS_CONNECTED, PING_PARAM)), is(true));

        assertThat("join message not matched with join",
                filter.isLoggable(rec(HAS_CONNECTED, JOIN_PARAM)), is(false));
    }

    @Test
    public void testIsLoggable__both() throws Exception {
        IHConnectedFilter filter = setupFilter(true, true);

        assertThat("arbitrary log message wrongly matched with both",
                filter.isLoggable(rec(ARBITRARY_MESSAGE)), is(true));

        assertThat("ping message not matched with both",
                filter.isLoggable(rec(HAS_CONNECTED, PING_PARAM)), is(false));

        assertThat("join message not matched with both",
                filter.isLoggable(rec(HAS_CONNECTED, JOIN_PARAM)), is(false));
    }

    @Test
    public void testIsLoggable__neither() throws Exception {
        IHConnectedFilter filter = setupFilter(false, false);

        assertThat("arbitrary log message wrongly matched with neither",
                filter.isLoggable(rec(ARBITRARY_MESSAGE)), is(true));

        assertThat("ping message not matched with neither",
                filter.isLoggable(rec(HAS_CONNECTED, PING_PARAM)), is(true));

        assertThat("join message not matched with neither",
                filter.isLoggable(rec(HAS_CONNECTED, JOIN_PARAM)), is(true));
    }

    private IHConnectedFilter setupFilter(boolean pings, boolean joins) {
        IHConnectedFilter filter = new IHConnectedFilter(Logger.getLogger(getClass().getName()));

        Configuration config = new Configuration();
        config.set("filter.initialhandler.ping", pings);
        config.set("filter.initialhandler.join", joins);
        filter.loadConfig(config);

        return filter;
    }

    private LogRecord rec(String message, String... params) {
        LogRecord record = new LogRecord(Level.INFO, message);
        record.setParameters(params);
        return record;
    }
}

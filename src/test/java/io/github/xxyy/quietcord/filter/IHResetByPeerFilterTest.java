package io.github.xxyy.quietcord.filter;

import net.md_5.bungee.config.Configuration;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IHResetByPeerFilterTest {
    public static final String INITIAL_HANDLER = "[/127.0.0.1:12345] <-> InitialHandler";
    public static final String ARBITRARY_MESSAGE = "Enabled plugin {0} version {1}";
    public static final String IO_EXCEPTION_MSG = "{0} - IOException: {1}";
    public static final String RESET_BY_PEER_EN = IHResetByPeerFilter.DEFAULT_MESSAGE;
    public static final String RESET_BY_PEER_DE = "Die Verbindung wurde vom Kommunikationspartner zurückgesetzt.";

    @Test
    public void testIsLoggable__on() throws Exception {
        IHResetByPeerFilter filter = setupFilter(true, RESET_BY_PEER_EN);

        assertThat("arbitrary log message wrongly matched when enabled with default message",
                filter.isLoggable(rec(ARBITRARY_MESSAGE)), is(true));

        assertThat("default message not matched when enabled with default message",
                filter.isLoggable(rec(IO_EXCEPTION_MSG, INITIAL_HANDLER, RESET_BY_PEER_EN)), is(false));
    }

    @Test
    public void testIsLoggable__on__defaults() throws Exception {
        IHResetByPeerFilter filter = setupFilter(); //default configuration should be off

        assertThat("arbitrary log message wrongly matched when disabled",
                filter.isLoggable(rec(ARBITRARY_MESSAGE)), is(true));

        assertThat("default message wrongly matched when disabled",
                filter.isLoggable(rec(IO_EXCEPTION_MSG, INITIAL_HANDLER, RESET_BY_PEER_EN)), is(true));
    }

    @Test
    public void testIsLoggable__on__german() throws Exception {
        IHResetByPeerFilter filter = setupFilter(true, RESET_BY_PEER_DE);

        assertThat("arbitrary log message wrongly matched when enabled with German message",
                filter.isLoggable(rec(ARBITRARY_MESSAGE)), is(true));

        assertThat("German message not matched when enabled with German message",
                filter.isLoggable(rec(IO_EXCEPTION_MSG, INITIAL_HANDLER, RESET_BY_PEER_DE)), is(false));

        assertThat("default message wrongly matched when enabled with German message",
                filter.isLoggable(rec(IO_EXCEPTION_MSG, INITIAL_HANDLER, RESET_BY_PEER_EN)), is(true));
    }

    private IHResetByPeerFilter setupFilter() {
        return new IHResetByPeerFilter(Logger.getLogger(getClass().getName()));
    }

    private IHResetByPeerFilter setupFilter(boolean enable, String message) {
        IHResetByPeerFilter filter = setupFilter();

        Configuration config = new Configuration();
        config.set("filter.initialhandler.reset-by-peer", enable);
        config.set("filter.initialhandler.reset-by-peer-message", message);
        filter.loadConfig(config);

        return filter;
    }

    private LogRecord rec(String message, String... params) {
        LogRecord record = new LogRecord(Level.INFO, message);
        record.setParameters(params);
        return record;
    }
}

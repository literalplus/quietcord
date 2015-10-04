package io.github.xxyy.quietcord.filter;

import org.junit.Test;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PropagatingFilterTest {

    public static final String ARBITRARY_TEXT = "arbitrary text";
    public static final String IGNORED_BY_A = "ignored by A";
    public static final String IGNORED_BY_B = "ignored by B";

    @Test
    public void testInject__alone() throws Exception {
        Logger logger = Logger.getLogger("testing");

        new PropagatingFilterA(logger).inject();

        Filter injected = logger.getFilter();

        assertThat("filter wrongly matched arbitrary message alone",
                injected.isLoggable(rec(ARBITRARY_TEXT)), is(true));

        assertThat("filter A didn't match ignored message alone",
                injected.isLoggable(rec(IGNORED_BY_A)), is(false));
    }

    @Test
    public void testInject__multiple() throws Exception {
        Logger logger = Logger.getLogger("testing");

        new PropagatingFilterA(logger).inject();
        new PropagatingFilterB(logger).inject();

        Filter injected = logger.getFilter();

        assertThat("filter wrongly matched arbitrary message multiple",
                injected.isLoggable(rec(ARBITRARY_TEXT)), is(true));

        assertThat("filter didn't match ignored message A multiple",
                injected.isLoggable(rec(IGNORED_BY_A)), is(false));

        assertThat("filter didn't match ignored message B multiple",
                injected.isLoggable(rec(IGNORED_BY_B)), is(false));
    }

    private LogRecord rec(String message) {
        return new LogRecord(Level.INFO, message);
    }

    private class PropagatingFilterA extends PropagatingFilter {
        protected PropagatingFilterA(Logger logger) {
            super(logger);
        }

        @Override
        public boolean isLoggable(LogRecord record) {
            return !record.getMessage().equals(IGNORED_BY_A);
        }
    }

    private class PropagatingFilterB extends PropagatingFilter {
        protected PropagatingFilterB(Logger logger) {
            super(logger);
        }

        @Override
        public boolean isLoggable(LogRecord record) {
            return !record.getMessage().equals(IGNORED_BY_B);
        }
    }
}

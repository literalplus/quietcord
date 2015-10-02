package io.github.xxyy.quietcord.filter;

import com.google.common.base.Preconditions;

import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides a common base implementation for injectable filters. This implementation does
 * not propagate calls to previous filters when injected, however subclasses may do that.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 02/10/15
 */
public abstract class AbstractInjectableFilter implements InjectableFilter {
    private final Logger logger;
    protected Filter previousFilter = null;

    protected AbstractInjectableFilter(Logger logger) {
        Preconditions.checkNotNull(logger, "logger");
        this.logger = logger;
    }

    @Override
    public boolean isInjected() {
        return logger.getFilter() == this;
    }

    @Override
    public Filter inject() {
        if (isInjected()) {
            return logger.getFilter();
        }

        previousFilter = logger.getFilter();
        logger.setFilter(this);
        logger.log(Level.INFO, "[QuietCord] This logger is now filtered by {0}", this);
        return logger.getFilter();
    }

    @Override
    public boolean reset() {
        if (!isInjected()) {
            logger.log(Level.WARNING,
                    "[QuietCord] Could not reset log filter {0} because replaced by {1} for logger {2}",
                    new Object[]{this, logger.getFilter(), logger.getName()});
            return false; //Maintain maximum compatibility
        } else {
            logger.setFilter(previousFilter);
            return true;
        }
    }

    @Override
    public Filter getPreviousFilter() {
        return previousFilter;
    }

    @Override
    public Logger getLogger() {
        return logger;
    }
}

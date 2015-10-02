package io.github.xxyy.quietcord.filter;

import java.util.logging.Filter;
import java.util.logging.Logger;

/**
 * A logger-specific log filter that can be injected into and removed from its logger.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 02/10/15
 */
public interface InjectableFilter extends Filter {
    /**
     * @return the logger associated with this injectable filter
     */
    Logger getLogger();

    /**
     * Gets the filter that was replaced or extended by this filter. A return value
     * of null may mean that either there was no previous filter or the filter is
     * not currently injected.
     *
     * @return the previous filter or null
     */
    Filter getPreviousFilter();

    /**
     * Checks whether this filter is currently injected into the logger. The filter is
     * not injected if the logger's filter has ben changed somewhere else.
     *
     * @return whether the filter is currently injected into its logger
     */
    boolean isInjected();

    /**
     * Attempts to inject this filter into its logger. Implementations may choose to
     * use a wrapper filter for compatibility with filters already added to the logger.
     *
     * @return the filter that was injected, need not be this filter
     */
    Filter inject();

    /**
     * Attempts to reset the logger's filter to its state before this filter was injected.
     * The behaviour of this method is undefined if that logger's current filter was not
     * injected by this instance's {@link #inject() inject method}, and entirely
     * implementation-dependent.
     *
     * @return whether the logger was reset to its previous state
     */
    boolean reset();
}

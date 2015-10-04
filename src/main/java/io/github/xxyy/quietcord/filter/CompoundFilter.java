package io.github.xxyy.quietcord.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * A log filter that only allows log entries that all child filters allow.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 02/10/15
 */
public class CompoundFilter extends AbstractInjectableFilter {
    private final List<Filter> filters;

    public CompoundFilter(Logger logger) {
        this(logger, new LinkedList<Filter>());
    }

    public CompoundFilter(Logger logger, List<Filter> filters) {
        super(logger);
        this.filters = new ArrayList<>(filters); //who knows what the caller might pass (e.g. Arrays.asList(...))
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        for (Filter filter : filters) {
            if (!filter.isLoggable(record)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a filter to this compound filter's filter list.
     *
     * @param filter the filter to add
     */
    public void addFilter(Filter filter) {
        filters.add(filter);
    }

    /**
     * Removes a filter from this compound filter's filter list.
     *
     * @param filter the filter to remove
     */
    public void removeFilter(Filter filter) {
        filters.remove(filter);
    }

    /**
     * Checks if this compound filter's filter list contains a specific filter.
     *
     * @param filter the filter to seek
     * @return whether given filter is in the filter list
     */
    public boolean hasFilter(Filter filter) {
        return filters.contains(filter);
    }

    /**
     * @return an unmodifiable view of this compound filter's filter list.
     */
    public List<Filter> getFilters() {
        return Collections.unmodifiableList(filters);
    }

    @Override
    public String toString() {
        return "CompoundFilter{" +
                "filters=" + filters +
                '}';
    }
}

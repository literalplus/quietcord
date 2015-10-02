package io.github.xxyy.quietcord.filter;

import java.util.Arrays;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * An abstract base class for injectable filters that also call previously installed log
 * filters, therefore providing more compatibility. This implementation does propagate
 * calls to previously installed filters.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 02/10/15
 */
public abstract class PropagatingFilter extends AbstractInjectableFilter {
    protected PropagatingFilter(Logger logger) {
        super(logger);
    }

    @Override
    public boolean isInjected() {
        return super.isInjected() || inCompound();
    }

    @Override
    public Filter inject() {
        //noinspection StatementWithEmptyBody
        if (isInjected()) { //we're injected or in the compound
            //no-op
        } else if (isCompound()) { //we have a compound, but we're not in it
            ((CompoundFilter) getLogger().getFilter()).addFilter(this);
        } else if (getLogger().getFilter() != null) { //uh-oh, previous filter!
            previousFilter = getLogger().getFilter();
            getLogger().setFilter(new CompoundFilter( //create compound
                    getLogger(),
                    Arrays.asList(this, previousFilter) //called in that order
            ));
        } else { //we're alone, let the superclass do the work
            return super.inject();
        }
        return getLogger().getFilter();
    }

    @Override
    public boolean reset() {
        if (inCompound()) { //we're in the compound
            CompoundFilter compound = (CompoundFilter) getLogger().getFilter();
            compound.removeFilter(this);
            if (compound.getFilters().size() == 1 && //if the only filter is our previous one
                    compound.getFilters().get(0) == previousFilter) {
                getLogger().setFilter(previousFilter); //reset filter to previous
            } else if (compound.getFilters().isEmpty()) {
                getLogger().setFilter(null); //remove empty compound
            }
            return !isCompound(); //if the filter is no longer a compound, we reset it
        } else if (isCompound()) { //there is a compound, but we're not invited to the party
            getLogger().log(Level.WARNING,
                    "[QuietCord] Could not reset filter {0} because no longer in compound!",
                    new Object[]{this, getLogger().getFilter(), getLogger().getName()});
            return false;
        } else { //we're the only filter
            return super.reset();
        }
    }

    /**
     * Checks if the logger has a compound filter installed that calls this filter.
     *
     * @return whether the logger has a compound filter calling this filter
     */
    protected boolean inCompound() {
        return isCompound() && ((CompoundFilter) getLogger().getFilter()).hasFilter(this);
    }

    /**
     * Checks if the logger's filter is a compound filter created by QuietCord.
     *
     * @return whether the logger's filter is a compound filter
     */
    protected boolean isCompound() {
        return getLogger().getFilter() instanceof CompoundFilter;
    }
}

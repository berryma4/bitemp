/*
 * (c) Copyright Ervacon 2007.
 * All Rights Reserved.
 */

package edu.msu.frib.olog.bitemp.entity.bitemporal;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * A trace of {@link Bitemporal} objects, bitemporally tracking some value (for
 * instance the name of a person). A bitemporal trace works on top of (wraps)
 * a collection of {@link Bitemporal} objects, representing the raw data to query
 * and manipulate.
 * 
 * <p>
 * 
 * Together with {@link Bitemporal}, {@link BitemporalTrace} provides a low-level API
 * for bitemporal data tracking and manipulation expressed in terms of {@link Bitemporal}
 * objects.
 * 
 * <p>
 * 
 * A bitemporal trace will be serializable if all {@link Bitemporal} objects it contains
 * are serializable.
 * 
 * <p>
 * 
 * A bitemporal trace is not thread-safe.
 * 
 * @see Bitemporal
 * 
 * @author Erwin Vervaet
 * @author Christophe Vanfleteren
 * @author igor.mihalik
 */
public class BitemporalTrace implements Serializable {

    private Collection<Bitemporal> data;

    /**
     * Create a new bitemporal trace working on top of given data collection.
     */
    public BitemporalTrace(Collection<Bitemporal> data) {
        if (data == null) {
            throw new IllegalArgumentException("The bitemporal data is required");
        }
        this.data = data;
    }

    /**
     * Returns the wrapped data collection.
     */
    public Collection<Bitemporal> getData() {
        return this.data;
    }

    /**
     * Returns {@link Bitemporal} objects valid on given date as known on
     * specified date.
     */
    public Collection<Bitemporal> get(DateTime validOn, DateTime knownOn) {
        Collection<Bitemporal> result = new LinkedList<Bitemporal>();
        for (Bitemporal bitemporal : data) {
            if (bitemporal.getValidityInterval().contains(validOn)
                    && bitemporal.getRecordInterval().contains(knownOn)) {
                result.add(bitemporal);
            }
        }
        return result;
    }

    /**
     * Returns the history of the tracked value, as known on specified time.
     * The history informs you about how the valid value changed over time.
     */
    public Collection<Bitemporal> getHistory(DateTime knownOn) {
        Collection<Bitemporal> history = new LinkedList<Bitemporal>();
        for (Bitemporal bitemporal : data) {
            if (bitemporal.getRecordInterval().contains(knownOn)) {
                history.add(bitemporal);
            }
        }
        return history;
    }

    /**
     * Returns the evolution of the tracked value for a specified validity date.
     * The evolution informs you about how knowledge about the value valid at a
     * certain date evolved.
     */
    public Collection<Bitemporal> getEvolution(DateTime validOn) {
        Collection<Bitemporal> evolution = new LinkedList<Bitemporal>();
        for (Bitemporal bitemporal : data) {
            if (bitemporal.getValidityInterval().contains(validOn)) {
                evolution.add(bitemporal);
            }
        }
        return evolution;
    }

    /**
     * Adds the given {@link Bitemporal} object to the trace, manipulating the trace
     * as necessary. This is essentially the basic bitemporal data manipulation
     * operation.
     */
    public void add(Bitemporal newValue) {
        Collection<Bitemporal> toEnd = getItemsThatNeedToBeEnded(newValue);

        Collection<Bitemporal> toAdd = new LinkedList<Bitemporal>();
        DateTime validityStartOfNewValue = newValue.getValidityInterval().getStart();
        for (Bitemporal validOnStartOfNewValue : get(validityStartOfNewValue, TimeUtils.now())) {
            if (validityStartOfNewValue.compareTo(validOnStartOfNewValue.getValidityInterval().getStart()) > 0) {
                Interval validityInterval = TimeUtils.interval(
                        validOnStartOfNewValue.getValidityInterval().getStart(),
                        validityStartOfNewValue);
                toAdd.add(validOnStartOfNewValue.copyWith(validityInterval));
            }
        }

        if (!(newValue.getValidityInterval().getEnd().getMillis() == TimeUtils.ACTUAL_END_OF_TIME)) {
            DateTime validityEndOfNewValue = newValue.getValidityInterval().getEnd();
            for (Bitemporal validOnEndOfNewValue : get(validityEndOfNewValue, TimeUtils.now())) {
                if (validityEndOfNewValue.compareTo(validOnEndOfNewValue.getValidityInterval().getStart()) > 0) {
                    Interval validityInterval = TimeUtils.interval(validityEndOfNewValue,
                            validOnEndOfNewValue.getValidityInterval().getEnd());
                    toAdd.add(validOnEndOfNewValue.copyWith(validityInterval));
                }
            }
        }

        for (Bitemporal needsToEnd : toEnd) {
            needsToEnd.end();
        }
        for (Bitemporal toBeAdded : toAdd) {
            data.add(toBeAdded);
        }

        data.add(newValue);
    }

    Collection<Bitemporal> getItemsThatNeedToBeEnded(Bitemporal newValue) {
        Collection<Bitemporal> toEnd = new HashSet<Bitemporal>();
        for (Bitemporal possibleOverlap : getHistory(TimeUtils.now())) {
            if (newValue.getValidityInterval().overlaps(possibleOverlap.getValidityInterval())) {
                toEnd.add(possibleOverlap);
            }
        }
        return toEnd;
    }

    @Override
    public String toString() {
        StringWriter buf = new StringWriter();
        PrintWriter bufWriter = new PrintWriter(buf);
        for (Bitemporal bitemporal : data) {
            bufWriter.println(bitemporal);
        }
        return buf.toString();
    }

}

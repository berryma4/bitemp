/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp.entity;

import edu.msu.frib.olog.bitemp.entity.bitemporal.WrappedBitemporalProperty;
import edu.msu.frib.olog.bitemp.entity.bitemporal.WrappedValueAccessor;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.joda.time.Interval;

/**
 *
 * @author berryman
 */
@Entity
@Table(name = "log")
@NamedQuery(name = Log.findAll, query = "SELECT r FROM entries r")
public class Log implements Serializable {

    public Log() {
    }
    @Id
    private Long id;
    
    private static final String PREFIX = "edu.msu.frib.olog.bitemp.entity.Log.";
    public static final String findAll = PREFIX + "all";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="entry")
    private Collection<BitempEntry> entries = new LinkedList<BitempEntry>();

    // use this method for accessing bitemporal trace of Entry values
    public WrappedBitemporalProperty<Entry , BitempEntry> getEntries() {
        return new WrappedBitemporalProperty<Entry, BitempEntry>(entries,
                new WrappedValueAccessor<Entry, BitempEntry>() {

            public BitempEntry wrapValue(Entry value, Interval validityInterval) {
                return new BitempEntry(value, validityInterval);
            }

        });
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id=id;
    }
}


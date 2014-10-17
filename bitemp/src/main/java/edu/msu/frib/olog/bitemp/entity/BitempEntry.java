/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp.entity;

import edu.msu.frib.olog.bitemp.entity.bitemporal.Bitemporal;
import edu.msu.frib.olog.bitemp.entity.bitemporal.BitemporalWrapper;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.joda.time.Interval;

@Entity
@Table(name = "bitemp_entry")
public class BitempEntry extends BitemporalWrapper<Entry> implements Serializable {
    
    @ManyToOne(targetEntity = Entry.class, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @Basic
    private Entry value;
    
    @Id
    @Basic
    private Long id;

    protected BitempEntry() {
        // default constructor required by Hibernate
    }

    public BitempEntry(Entry value, Interval validityInterval) {
        super(value, validityInterval);
    }

    @Override
    public Entry getValue() {
        return value;
    }

    @Override
    protected void setValue(Entry value) {
        this.value = value;
    }

    public Bitemporal copyWith(Interval validityInterval) {
        // BitemporalWrapper implements the Bitemporal interface
        return new BitempEntry(value, validityInterval);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}


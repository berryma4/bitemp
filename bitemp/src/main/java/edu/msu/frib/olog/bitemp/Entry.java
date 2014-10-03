/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author berryman
 * 
 * Entry is the value to be tracked bitemporally
 * BitempEntry is the temporal object (value plus additional time information)
 * Log is the root entity holding a trace of temporal objects
 */
@Entity
@Table(name = "entry_imp")
public class Entry implements Serializable {

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private Log log;
    @Id
    private Long id;
    
    
    
    @Column(name = "owner", nullable = false, length = 50, insertable = true, updatable = false)
    private String owner;
    
    @Column(name = "source", nullable = false, length = 50, insertable = true, updatable = false)
    private String source;
    
    @Enumerated(EnumType.STRING)
    private Level level;
    
    @Column(name = "description", nullable = false, insertable = true, updatable = false)
    private String description;

    protected Entry() {
        // default constructor required by Hibernate
    }
    
    public Entry(Date creationDate, Log log, String owner, String source, String description){
        this.owner = owner;
        this.source = source;
        this.description = description;
        this.log = log;
        setCreationDate(creationDate);
    }

    // Log should be "user-immutable" as well
    public Entry(Date creationDate, Log log) {
        this.log = log;
        setCreationDate(creationDate);
    }

    public Date getCreationDate() {
        // return a defensive copy of the mutable Date class
        return new Date(creationDate.getTime());
    }

    protected void setCreationDate(Date creationDate) {
        // create a defensive copy of the mutable Date class
        this.creationDate = new Date(creationDate.getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp.bitemporal;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Embeddable;
import org.joda.time.Interval;

/**
 *
 * @author berryman
 */
@Embeddable
public class SqlInterval implements Serializable {

    private java.sql.Date From;
    private java.sql.Date To;

    public SqlInterval() {
    }
    
    SqlInterval(Interval interval){
        this.From = new java.sql.Date(interval.getStart().getMillis());
        this.To = new java.sql.Date(interval.getEnd().getMillis());
    }

    public Date getFrom() {
        return From;
    }

    public Date getTo() {
        return To;
    }
 
}

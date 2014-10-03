/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.bitemp.bitemporal;

import java.util.logging.Logger;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.joda.time.Interval;

/**
 *
 * @author berryman
 */
@Converter
public class IntervalConverter implements AttributeConverter<Interval, SqlInterval> {

    //private static final Logger log;
    private static final long serialVersionUID = 1L;

    @Override
    public SqlInterval convertToDatabaseColumn(Interval attribute) {
        if (attribute == null) {
          //  log.info("convertObjectValueToDataValue returning null");
            return null;
        }
        if (attribute instanceof Interval) {
            return new SqlInterval((Interval)attribute);
        } else {
          //  log.info("convertObjectValueToDataValue returning null");
            return null;
        }
    }

    @Override
    public Interval convertToEntityAttribute(SqlInterval dbData) {
         if (dbData == null) {
          //  log.info("convertDataValueToObjectValue returning null");
            return null;
        }
        if (dbData instanceof SqlInterval) {
            long start = ((SqlInterval) dbData).getFrom().getTime();
            long end = ((SqlInterval) dbData).getTo().getTime();
            return new Interval(start, end);
        } else {
          //  log.info("convertDataValueToObjectValue returning null");
            return null;
        }
    }
}

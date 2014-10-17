/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.tracing.boundry;

import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author berryman
 */
public class LoggerInjectionSupport {
    @Inject
    Logger logger;

    public Logger getLogger() {
        return logger;
    }
}

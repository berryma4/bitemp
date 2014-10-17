/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.msu.frib.olog.tracing.boundry;

import java.util.logging.Logger;
import javax.inject.Inject;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author berryman
 */
@RunWith(Arquillian.class)
public class LoggerExposerIT {
    @Inject
    LoggerInjectionSupport cut;

    @Deployment
    public static WebArchive create() {
        return ShrinkWrap.create(WebArchive.class).
                addClasses(LoggerExposer.class, LoggerInjectionSupport.class).
                addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void loggerProperlyConfigured() {
        Logger logger = this.cut.getLogger();
        String expected = this.cut.getClass().getName();
        String actual = logger.getName();
        assertThat(actual, is(expected));
    }
}

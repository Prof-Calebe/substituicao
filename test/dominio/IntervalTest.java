/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.joda.time.Interval;
import org.junit.Assert;
import org.joda.time.Instant;

/**
 *
 * @author 31010441
 */
public class IntervalTest {
    
    public IntervalTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testandoIntervalos() {
        
        DateTime start1 = new DateTime(2004, 12, 25, 0, 0, 0, 0);
        DateTime end1 = new DateTime(2005, 1, 1, 0, 0, 0, 0);
        
        DateTime start2 = new DateTime(2004, 12, 25, 0, 0, 0, 0);
        DateTime end2 = new DateTime(2007, 1, 1, 0, 0, 0, 0);        
        
        
        Interval interval1 = new Interval(start1, end1);
        Interval interval2 = new Interval(start2, end2);
        
        Assert.assertNotNull(interval1.overlap(interval1));
        
        DateTime start3 = new DateTime(2010, 12, 25, 0, 0, 0, 0);
        DateTime end3 = new DateTime(2013, 1, 1, 0, 0, 0, 0);  
        
        
        Interval interval3 = new Interval(start3, end3);
        
        Assert.assertNull(interval3.overlap(interval1));
        Assert.assertNull(interval3.overlap(interval2));
        
        DateTime start5 = new DateTime(2013, 05, 28, 0, 0);
        
        Assert.assertEquals(DateTimeConstants.TUESDAY, start5.getDayOfWeek());

    }
    
}

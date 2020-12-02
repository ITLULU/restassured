package utils;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

public class DataUtilTest {

    Logger logger =Logger.getLogger(DataUtilTest.class);
    @Test
    public void testGetDay() {
        int date=DataUtil.getDay(new Date());
        logger.info("date:"+date);
    }

    @Test
    public void testAddYears() {
         Date date = DataUtil.addYears(new Date(),1);
        logger.info("date:"+date);
    }

    @Test
    public void testAddDates() {
        Date date= DataUtil.addDates(new Date(),-1);
        logger.info("date:"+DataUtil.DateToString(date,"YYYY-MM-dd"));
    }

    @Test
    public void testStringtoUtilDate() {
        Date date= DataUtil.StringtoUtilDate("2020-01-20","yyyy-MM-dd");
        logger.info("date:"+date);
    }

    @Test
    public void testDateToString() {
       String date= DataUtil.DateToString(new Date(),"yyyy-MM-dd");
        logger.info("date:"+date);
    }
}
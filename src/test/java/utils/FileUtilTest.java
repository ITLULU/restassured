package utils;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FileUtilTest {
    static Logger logger = Logger.getLogger(FileUtilTest.class);
    @Test
    public void testWriteIntoText() {
        FileUtil.writeIntoText(FileUtil.readFile("src/main/resources/json/login.json"),"src/main/resources/dir/result.txt");
    }

    @Test
    public void testReadFile() {
        logger.info(FileUtil.readFile("src/main/resources/json/login.json"));
    }

}
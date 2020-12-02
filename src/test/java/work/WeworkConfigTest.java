package work;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

public class WeworkConfigTest {

    static Logger logger=Logger.getLogger(WeworkConfigTest.class);
    @Test
    public void TestLoad(){
           logger.info(WeworkConfig.load("/conf/weworkConfig.yaml"));
          logger.info(WeworkConfig.load("/conf/weworkConfig.yaml").env);
    }
    @Test
    void getInstance(){
        logger.info(WeworkConfig.getInStance());
    }
}
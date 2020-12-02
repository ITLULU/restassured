package parser;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TestParse {
    /**
     * 局部使用
     */
    @Test
    public void testDefaultParser() {
        given().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/02/").then().using().defaultParser(Parser.XML);
    }

    @Test
    public void TestUsing(){
            RestAssured.registerParser("application/vnd.uoml+xml", Parser.XML);
//            RestAssured.unregisterParser("application/vnd.uoml+xml");
        given().get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/02/").then().using().parser("application/vnd.uoml+xml", Parser.XML);
    }
}

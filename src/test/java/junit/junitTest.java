package junit;

import org.junit.BeforeClass;
import  org.junit.*;

public class junitTest {

    Calum calum ;
    @BeforeClass
    public static void before() {
        System.out.println("Before class, Onln Once");
    }

    @AfterClass
    public static void after() {
        System.out.println("After class, only once");
    }

    @Before
    public void setup() {
        System.out.println("Before Method");
        calum = new Calum();
    }

    @After
    public void tearDown() {
        System.out.println("After Method");
    }
    @Test
    public  void testadd(){
        System.out.println("add:"+calum.add(22.22,333.33));
    }
    @Test
    public  void testmul(){
        System.out.println("multi:"+calum.multi(22.22,333.33));
    }

    @Test
    public  void div(){
        System.out.println("div:"+calum.div(22.22,11));
    }


}

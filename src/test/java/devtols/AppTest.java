package devtols;


import org.testng.annotations.Test;

public class AppTest {
    @Test
    public void listApp() {
        App app=new App();
        app.listApp().then().statusCode(200);
    }
}
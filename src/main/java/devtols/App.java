package devtols;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import work.Wework;
import workweixin.Api;

public class App extends Api {
    @Override
    public RequestSpecification getDefaultRequestSpecification() {
        RequestSpecification requestSpecification=super.getDefaultRequestSpecification();
        requestSpecification.queryParam("access_token", Wework.getToken())
                .contentType(ContentType.JSON);

        requestSpecification.filter( (req, res, ctx)->{
            //todo: 对请求 响应做封装
            return ctx.next(req, res);
        });
        return requestSpecification;
    }

    public Response listApp(){
        return getResponseFromHar("/api/app.har.json", ".*tid=67.*", null);
    }
}
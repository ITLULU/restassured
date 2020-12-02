package workweixin;


import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import work.Wework;

public class Contact extends Api {
    String random=String.valueOf(System.currentTimeMillis());
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



   /* @Override
    public RequestSpecification getDefaultRequestSpecification() {
        RequestSpecification requestSpecification=super.getDefaultRequestSpecification();
        requestSpecification.queryParam("access_token", Wework.getToken())
                .contentType(ContentType.JSON);
        requestSpecification.filter( (req, res, ctx)->{
            //todo: 对请求 响应做封装
            return ctx.next(req, res);
        });
        return requestSpecification;
    }*/
}
package Listener;

import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MyRetryListener implements IAnnotationTransformer {

    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {

        IRetryAnalyzer myRetry = iTestAnnotation.getRetryAnalyzer();
        if (myRetry == null) {
            iTestAnnotation.setRetryAnalyzer(MyRetry.class);
        }
    }

}

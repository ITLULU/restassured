package junit;

import java.math.BigDecimal;

public class Calum {
    public  BigDecimal add(double va,double vb){
        return  BigDecimal.valueOf(va).add(BigDecimal.valueOf(vb));
    }
    public  BigDecimal sub(double va,double vb){
        return  BigDecimal.valueOf(va).subtract(BigDecimal.valueOf(vb));
    }
    public  BigDecimal multi(double va,double vb){
        return  BigDecimal.valueOf(va).multiply(BigDecimal.valueOf(vb));
    }
    public  BigDecimal div(double va,double vb){
        return  BigDecimal.valueOf(va).divide(BigDecimal.valueOf(vb));
    }
}

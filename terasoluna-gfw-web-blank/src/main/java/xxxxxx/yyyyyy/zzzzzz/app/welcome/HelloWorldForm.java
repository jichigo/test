package xxxxxx.yyyyyy.zzzzzz.app.welcome;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class HelloWorldForm {

    @DateTimeFormat(style = "-M")
    private Date date1;

    @DateTimeFormat(style = "MM")
    private Date date2;

    @DateTimeFormat(style = "LL")
    private Date date3;

    @DateTimeFormat(style = "FF")
    private Date date4;

    @DateTimeFormat(style = "M-")
    private Date date5;

    @NumberFormat(style = Style.NUMBER)
    private Long number1;

    @NumberFormat(style = Style.CURRENCY)
    private Long number2;

    @NumberFormat(style = Style.PERCENT)
    private Long number3;

    public Long getNumber1() {
        return number1;
    }

    public void setNumber1(Long number1) {
        this.number1 = number1;
    }

    public Long getNumber2() {
        return number2;
    }

    public void setNumber2(Long number2) {
        this.number2 = number2;
    }

    public Long getNumber3() {
        return number3;
    }

    public void setNumber3(Long number3) {
        this.number3 = number3;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public Date getDate4() {
        return date4;
    }

    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public Date getDate5() {
        return date5;
    }

    public void setDate5(Date date5) {
        this.date5 = date5;
    }

}

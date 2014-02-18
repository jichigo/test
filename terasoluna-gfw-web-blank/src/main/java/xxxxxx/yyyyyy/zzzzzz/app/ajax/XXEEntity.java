package xxxxxx.yyyyyy.zzzzzz.app.ajax;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "XXEEntity")
public class XXEEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stringField;

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

}

package xxxxxx.yyyyyy.zzzzzz;

import org.springframework.beans.factory.annotation.Value;

public class Hoge {

    @Value("${aaa.bb}")
    private String hoge;

    public String getHoge() {
        return hoge;
    }

    public void setHoge(String hoge) {
        this.hoge = hoge;
    }

}

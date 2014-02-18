package xxxxxx.yyyyyy.zzzzzz.app.ajax;

import java.io.Serializable;
import java.util.List;

public class SearchResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Object> list;

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

}

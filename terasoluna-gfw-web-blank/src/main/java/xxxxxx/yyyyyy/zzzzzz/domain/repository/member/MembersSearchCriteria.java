package xxxxxx.yyyyyy.zzzzzz.domain.repository.member;

import org.hibernate.validator.constraints.NotEmpty;

public class MembersSearchCriteria {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

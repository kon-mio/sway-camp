package com.zxy.swaycamp.common.enums;

import lombok.Data;

/**
 * @author XinYuan Zhao
 * @since 2023/2/12
 */
public enum Gender {

    GENDER_OTHER(0),
    GENDER_MALE(1),
    GENDER_FEMALE(2);
    private final int gender;
    Gender(int gender){
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }
}

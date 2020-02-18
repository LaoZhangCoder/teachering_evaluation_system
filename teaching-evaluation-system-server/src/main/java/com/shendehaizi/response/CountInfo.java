package com.shendehaizi.response;

import lombok.Data;

@Data
public class CountInfo {
    private Long studentCount;
    private Long classCount;
    private Long teacherCount;
    private Long courseCount;
}

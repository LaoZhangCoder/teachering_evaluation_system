package com.shendehaizi.response;

import lombok.Data;

@Data
public class TeacherScore {
    private Long id;
    private String teacherName;
    private Long count;
    private String classNames;
    private String courseNames;

}

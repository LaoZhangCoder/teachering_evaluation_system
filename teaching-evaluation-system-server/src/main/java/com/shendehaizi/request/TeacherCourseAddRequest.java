package com.shendehaizi.request;

import lombok.Data;

@Data
public class TeacherCourseAddRequest {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 院系id
     */
    private Long departmentId;
    /**
     * 专业id
     */
    private Long majorId;
    /**
     * 班级id
     */
    private  Long classId;
    /**
     * 课程id
     */
    private Long courseId;
}

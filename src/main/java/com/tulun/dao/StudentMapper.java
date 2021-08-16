package com.tulun.dao;

import com.tulun.pojo.Student;

import java.util.List;

/**
 * @author QiangQin
 * * @date 2021/8/16
 */
public interface StudentMapper {
    int  insertStudent(Student student);

    List<String>  findGraduates(Integer year);//拿到当年毕业的学生的邮箱
}

package com.tulun.service;

import com.tulun.dao.StudentMapper;
import com.tulun.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author QiangQin
 * * @date 2021/8/16
 */
@Service
public class StudentService {
    @Autowired
    StudentMapper studentMapper;

    public int addStudents(List<Student> students){
        for (Student s:students ) {
            studentMapper.insertStudent(s);
        }
        return 1;
    }


    public List<String> findGraduates(){
       // 拿到当年年份
        Date date = new Date();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy");
        Integer year=Integer.valueOf(dateFormat.format(date));//字符串 转化为 数字

        return studentMapper.findGraduates(year);
    }
}

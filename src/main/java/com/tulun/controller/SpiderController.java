package com.tulun.controller;

import com.tulun.pojo.Student;
import com.tulun.service.MailService;
import com.tulun.service.MessageService;
import com.tulun.service.StudentService;
import com.tulun.utils.ExcelUtil;
import com.tulun.webmagic.SXSPipeline;
import com.tulun.webmagic.SXSProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import us.codecraft.webmagic.Spider;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author QiangQin
 * * @date 2021/8/13
 */
@Controller
public class SpiderController {

    @Autowired
    private SXSProcessor sxsProcessor;
    @Autowired
    private SXSPipeline sxsPipeline;
    @Autowired
    private MailService mailService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private StudentService studentService;

    /**
     * 通过URL 手动 触发爬虫爬取数据
     */
    @RequestMapping("/spider")
    @ResponseBody //以json的格式 返回不同类型的对象
    public String spider() {

        System.out.println("爬虫开始爬取数据...");
//        Spider.create(sxsProcessor)
//                .addPipeline(sxsPipeline)// 添加pipeline
//                .addUrl(SXSProcessor.getURL())
//                .run();
        messageService.scheduleSpider();

        return "success";
    }

    @RequestMapping("/mail")  //手动触发
    @ResponseBody //以json的格式 返回不同类型的对象
    public String mail() throws IOException {
//        mailService.sendMail();
        messageService.notifyByMail();
        return "success";
    }


    //springMVC 大文件 上传问题（比如：图片 音频  视频  文本，需要通过web页面往后端传输）
//    浏览器通过URL来访问是get操作，而我们需要post操作
    @RequestMapping("/uploadExcel")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request) {
        MultipartHttpServletRequest request1 = (MultipartHttpServletRequest) request;

        MultipartFile file = request1.getFile("excelFile");// 前 端页面 参数名称
        if (file.isEmpty()) {
            System.out.println("传输数据存在问题");
            return "fail";
        }

        try {
            //从前端 以流的形式来 读取到文件
            InputStream inputStream = file.getInputStream();
            //通过utils/ExcelUtil里面的Excel工具来解析读取到的文件
            List<List<Object>> list = ExcelUtil.getCourseListByExcel(inputStream, file.getOriginalFilename());
            inputStream.close();

           /*
           获取数据并存储
              [[序号, 1：你的姓名, 2：毕业年份, 3：邮箱, 4：你所在的高校名称, 5：图论班级（例：理161）],
               [1.0, 张三, 1.0, 18729366236@163.com, 西安理工大学, 理1314],
               [2.0, 李四, 2.0, 2901422401@qq.com, 西安工业大学, 工1319] ]
           * */
            List<Student> students = new ArrayList<>();

            //将返回数据的第一行为备注信息，进行删除
            // 序号, 1、您的姓名：, 2、毕业年份, 3、邮箱：, 4、您所在高校名称：, 5、图论班级：例：工1802 财1823 理1314
            if (list.size() == 1) {
                System.out.println("没有新的数据");
                return "success";
            }
            list.remove(0);

            Iterator<List<Object>> iterator = list.iterator();
            while (iterator.hasNext()) {
                List<Object> objects = iterator.next();
                Student student = new Student();

                String name = objects.get(1).toString(); //姓名
                student.setName(name);


//               年份    poi中取出的数据默认是double，即excel中单元格存储1，取出是1.0
                Double aDouble = Double.valueOf(objects.get(2).toString());
                Integer year = null;
                if (aDouble == 1.0) {  //对应问卷星上的5个可选项
                    year = 2020;
                } else if (aDouble == 2.0) {
                    year = 2021;
                } else if (aDouble == 3.0) {
                    year = 2022;
                } else if (aDouble == 4.0) {
                    year = 2024;
                } else if (aDouble == 5.0) {
                    year = 2025;
                }
                student.setGraduate_time((Integer) year);

                String email = objects.get(3).toString();
                student.setEmail(email);

                String school = objects.get(4).toString(); //学校
                student.setSchool(school);

                String tlClass = objects.get(5).toString();   //班级
                student.setTlclass(tlClass);

                student.setCreate_time(new Date());

                students.add(student);

            }

            //将数据插入到数据库
            studentService.addStudents(students);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "success";
    }

    // 上传页面
    @RequestMapping("/upload")
    public String upload(){
        return "uploadExcel"; //跳转到web/uploadExcel.html处
    }

}
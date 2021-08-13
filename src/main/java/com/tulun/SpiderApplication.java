package com.tulun;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.tulun.dao") //运行过程中,给接口自动生成实现类
public class SpiderApplication
{
    public static void main( String[] args )
    {
        System.out.println("启动爬虫项目");
        SpringApplication.run(SpiderApplication.class,args);
    }
}

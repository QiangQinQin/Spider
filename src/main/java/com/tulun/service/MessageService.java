package com.tulun.service;

import com.tulun.pojo.CompanyXZ;
import com.tulun.pojo.MailInfo;
import com.tulun.webmagic.SXSPipeline;
import com.tulun.webmagic.SXSProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author QiangQin
 * * @date 2021/8/16
 */

@Service
public class MessageService {
    // 注入依赖
    @Autowired
    CompanyService companyService;

    @Autowired
    MailService mailService;

    @Autowired
    StudentService studentService;

    @Autowired
    SXSProcessor sxsProcessor;
    @Autowired
    SXSPipeline sxsPipeline;


    @Scheduled(cron ="00 00 00 * * *")//每晚12点整 自动 爬取
    public void scheduleSpider(){
        Spider.create(sxsProcessor)
                .addPipeline(sxsPipeline)// 添加pipeline
                .addUrl(SXSProcessor.getURL())
                .run();
    }

////      启动成功过后，5秒开始执行，每次执行完 过5秒再次执行
//    @Scheduled(initialDelay = 1000,fixedRate = 5000)
//   public  void execTask(){
//       System.out.println("定时执行任务"+System.currentTimeMillis());
//   }


    @Scheduled(cron="00 10 07 * * *")  //https://www.cnblogs.com/dyppp/p/7498457.html   每天早上7点10分 自动 推送到邮箱
    public void notifyByMail() throws IOException {
       /*
        1.先获取所有新数据
        2.封装邮件内容
        3.对所有的合适用户进行发送（发送的邮件）
        4.变更数据的状态（state   防止数据重复发送）
       * */
        List<CompanyXZ> companies = companyService.selectNewCompanies();

        //判断是否有新数据
        if(companies.size() < 1){
            System.out.println("没有新增的招聘信息！！" );
            return;
        }

        //        获取合适用户邮箱
        List<String> emails = studentService.findGraduates();
        if(emails.size()<1){
            System.out.println("没有符合条件的用户，不进行邮件发送");
        }

        //封装邮件正文
        String content=null;
        content=buildSXContent(companies);

        for (String e:emails) {
            MailInfo mailInfo = new MailInfo();
            mailInfo.setTo(e);
            mailInfo.setTitle("图论提醒你，有新招聘信息啦！");
            mailInfo.setContent(content);
            mailService.sendHtmlMail(mailInfo);
        }

//        //封装邮件正文
//        String content=null;
//        content=buildSXContent(companies);
//        //给指定用户进行发送
//        MailInfo mailInfo = new MailInfo();
//        mailInfo.setTo("18729366236@163.com");
//        mailInfo.setTitle("图论提醒你，有新招聘信息啦！");
//        mailInfo.setContent(content);
//        mailService.sendHtmlMail(mailInfo);




        //变更数据状态
         // map是截取子流   collect是转化格式
        List<Integer> ids = companies.stream().map(item -> item.getId()).collect(Collectors.toList());//获取所有信息有更新的 公司id
        companyService.batchUpdateState(ids); // update sxs  set state=1 where id in (1,2,3,4,...);
        System.out.println("数据变更完成");
    }


    //封装邮件文本内容
    private String buildSXContent(List<CompanyXZ> companyXZS) throws IOException {

        //加载邮件html模板
        String fileName = "template/XiaoZhao.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            System.out.println("读取文件失败，fileName:"+fileName+ e);
        } finally {
            inputStream.close();
            fileReader.close();
        }


        String contentText = "以下是新增招聘公司,共"+companyXZS.size()+"家公司信息.敬请查看";

        //邮件表格header
        String header = "<td>公司</td><td>工作岗位</td><td>薪资范围</td><td>工作地点</td><td>公司简介</td><td>立即申请</td>";

        StringBuilder linesBuffer = new StringBuilder();
        int i = 0;
        //table   <td>XX</td> <td>XXX</td>
        for (CompanyXZ company:companyXZS) { // 可能有多家公司，一家公司写一行<tr> <tr/>
            linesBuffer.append("<tr><td>");
            //公司名
            linesBuffer.append(company.getName());
            linesBuffer.append("</td><td>");
            //工作岗位
            linesBuffer.append(company.getJob_name());
            linesBuffer.append("</td><td>");
            linesBuffer.append(company.getPrice());

            linesBuffer.append("</td><td>");
            linesBuffer.append(company.getWork_city());

            linesBuffer.append("</td><td>");
            linesBuffer.append(company.getCompany_info());

            linesBuffer.append("</td><td>");
            linesBuffer.append(" <a href=\"");
            linesBuffer.append(company.getJob_url()); // 网申URL
            linesBuffer.append("\"> 我要投 </a>");
            linesBuffer.append(" </td></tr>");
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = format.format(new Date());

        //完成resources/template/XiaoZhao.html中的{1}...{4}四个参数的替换   {tmp}无意义是0号参数
        String htmlText = MessageFormat.format(buffer.toString(), "tmp", contentText,  date,  header,  linesBuffer.toString());

        //改变表格样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
//        System.out.println(htmlText);
        return htmlText;
    }
}

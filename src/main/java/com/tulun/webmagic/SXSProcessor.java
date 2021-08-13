package com.tulun.webmagic;

/**
 * @author QiangQin
 * * @date 2021/8/13
 */

import com.tulun.pojo.CompanyXZ;
import com.tulun.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 实习僧校招页面抓取
 */
@Component("sxsProcessor")
public class SXSProcessor implements PageProcessor {
    @Autowired
    CompanyService companyService;

    //校招URL
    static  String URL= "https://resume.shixiseng.com/interns?page=1&type=school&keyword=%E6%8A%80%E6%9C%AF";

    //获取url
    public static String getURL(){
        return URL;
    }

    @Override
    public void process(Page page) {
        System.out.println("下载页面");
//        System.out.println(page.getHtml());

        //解析页面
        List<Selectable> nodes = page.getHtml().xpath("//div[@class='intern-wrap intern-item']").nodes();//选择html文档下 所有的class为..的div元素
        Iterator<Selectable> iterator = nodes.iterator();
        System.out.println("总数据："+nodes.size());

        if(nodes.size()< 1){
            return;
        }

        // 封装数据库列表
        ArrayList<CompanyXZ> companyXZS = new ArrayList<CompanyXZ>();
        //获取数据库中存在的数据
        List<String> allMd5 = companyService.allMd5();

        while (iterator.hasNext()) {   //一个URL 放满是20条公司数据
            Selectable selectable = iterator.next();
            System.out.println("------------------------------");
            StringBuffer stringBuffer = new StringBuffer(); //方便打印日志，进行查看
            //岗位名称
            String jobName = selectable.xpath("//div[@class='f-l intern-detail__job']/p/a/text()").get();// text()得到标签a里的纯文本内容
            stringBuffer.append("工作岗位：" + jobName + ",");
            //网申URL
            String jobURL = selectable.xpath("//div[@class='f-l intern-detail__job']/p/a/@href").get();// @href 表示拿href属性上的值
            stringBuffer.append("网申URL：" + jobURL + ",");
            //工资信息
            String price = selectable.xpath("//span[@class='day font']/text()").get();
            stringBuffer.append("工资信息：" + price + ",");
            //城市信息
            String city = selectable.xpath("//span[@class='city ellipsis']/text()").get();
            stringBuffer.append("工作城市：" + city + ",");
            //公司信息
            String company = selectable.xpath("//div[@class='f-r intern-detail__company']/p/a/text()").get();
            stringBuffer.append("公司名称：" + company + ",");
            //公司优势
            String companyInfo = selectable.xpath("//span[@class='company-label']/text()").get();
            stringBuffer.append("公司信息：" + companyInfo + ",");

            System.out.println(stringBuffer.toString());
//            System.out.println("jobName"+jobName);
            String key=company+jobName+city;
            //如果key存在，表示数据已经重复了，不再进行后续的处理
            if (allMd5.contains(key)) {
                System.out.println("数据重复："+key);
                //将数据提交给pipeline  (因为 有可能 当前迭代到的元素 之前几条  是新的，所以不set此条，直接提交，然后退出 )
                page.putField("companies",companyXZS);
                return;
            }


            CompanyXZ companyXZ = new CompanyXZ();
            companyXZ.setName(company);
            companyXZ.setJob_name(jobName);
            companyXZ.setJob_url(jobURL);
            companyXZ.setPrice(price);
            companyXZ.setWork_city(city);
            companyXZ.setCompany_info(companyInfo);

            //封装基础信息
            companyXZ.setState(0);
            companyXZ.setCreate_time(new Date()); //表示该记录未推送
            companyXZ.setMd5_key(company+jobName+city);//方便去重

            // 将该公司添加到列表中
            companyXZS.add(companyXZ);
        }

        //将 该URL对应所有公司数据 打包 提交给pipeline  （webmagic/SXSPipeline）
        page.putField("companies",companyXZS);


        if(nodes.size()<20){
            return;
        }

        // 翻页操作
        String url = page.getUrl().get(); //https://resume.shixiseng.com/interns?page=1&type=school&keyword=%E6%8A%80%E6%9C%AF
        Integer num = Integer.valueOf( url.substring(url.indexOf("page=")+5, url.indexOf("&"))  );//url.indexOf("page=")指的是找字符串url中page=的首次出现位置序号
        System.out.println("当前页面"+num);
        num = num+1;

        //  替换URL中的page
        url = url.replace( url.substring(url.indexOf("page=") ,url.indexOf("&")),  "page="+num  );//例如:将[page=1&)  替换为page=2

        //跳转到指定页面（会再次调用当前 process(Page page)方法进行处理)
        page.addTargetRequest(url);
    }

    @Override
    public Site getSite() {
        return  Site.me();
    }


//    // 临时测试，将SpiderController里的run粘贴过来
//    public static void main(String[] args) {
//        Spider.create(new SXSProcessor())
//                .addUrl(SXSProcessor.getURL())
//                .run();
//    }

}
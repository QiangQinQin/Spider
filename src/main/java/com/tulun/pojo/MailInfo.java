package com.tulun.pojo;

/**
 * @author QiangQin
 * * @date 2021/8/16
 */
public class MailInfo {
    //接收方邮箱
    private String to;

    //邮件标题
    private String title;

    //邮件正文
    private String content;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MailInfo{" +
                "to='" + to + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

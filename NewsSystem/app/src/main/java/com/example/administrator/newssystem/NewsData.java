package com.example.administrator.newssystem;

/**
 * Created by ChairmanZh on 2020/5/17.
 */


public class NewsData {
    private String newsTitle;//新闻标题
    private String newsDate; //新闻发布时间
//     private String newsImgUrl; // 新闻图片Url地址
    private String newsUrl; //新闻详情Url地址

//    public String getNewsImgUrl() {
//        return newsImgUrl;
//    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

//    public void setNewsImgUrl(String newsImgUrl) {
//        this.newsImgUrl = newsImgUrl;
//    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }
}

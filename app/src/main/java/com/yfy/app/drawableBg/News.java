package com.yfy.app.drawableBg;


import java.io.Serializable;

/**
 *
 */
public class News implements Serializable{
    private long id;
    private String news_img;
    private String title;
    private String content;
    private String page_view;
    private String add_time;
    private String content_http;

    public String getContent_http() {
        return content_http;
    }

    public void setContent_http(String content_http) {
        this.content_http = content_http;
    }

    public News() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNews_img() {
        return news_img;
    }

    public void setNews_img(String news_img) {
        this.news_img = news_img;
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

    public String getPage_view() {
        return page_view;
    }

    public void setPage_view(String page_view) {
        this.page_view = page_view;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}

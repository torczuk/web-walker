package com.github.torczuk.webwalker.adapters.common;

public class RequestedWebPage {
    private String url;
    private  int depth;

    public RequestedWebPage() {
    }

    public RequestedWebPage(String url, int depth) {
        this.url = url;
        this.depth = depth;
    }

    public String getUrl() {
        return url;
    }

    public int getDepth() {
        return depth;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}

package com.github.torczuk.webwalker.application.representation;

import java.util.List;

public class WebPageRepresentation {
    private String url;
    private String hash;
    private Integer size;
    private List<String> pages;

    public WebPageRepresentation() {
    }

    public WebPageRepresentation(String url, String hash, Integer size, List<String> pages) {
        this.url = url;
        this.hash = hash;
        this.size = size;
        this.pages = pages;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public List<String> getPages() {
        return pages;
    }

    public void setPages(List<String> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "\n{\n" +
                "url: '" + url + '\''  +
                ", \nhash: '" + hash + '\'' +
                ", \nsize: " + size +
                ", \npages: " + pages +
                "\n}";
    }
}

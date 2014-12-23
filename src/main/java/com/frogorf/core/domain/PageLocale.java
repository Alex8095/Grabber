package com.frogorf.core.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

/**
 * @author Tsurkin Alex
 */
@Embeddable
@MappedSuperclass
public class PageLocale {

    @Column(name = "web_title")
    protected String webTitle;
    @Column(name = "web_description")
    protected String webDescription;
    @Column(name = "web_keywords")
    protected String webKeywords;
    @Column
    protected String menu;
    @Column
    protected String title;
    @Column
    protected String description;
    @Column
    protected String content;

    /**
     * @return the webTitle
     */
    public String getWebTitle() {
        return webTitle;
    }

    /**
     * @param webTitle the webTitle to set
     */
    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    /**
     * @return the webDescription
     */
    public String getWebDescription() {
        return webDescription;
    }

    /**
     * @param webDescription the webDescription to set
     */
    public void setWebDescription(String webDescription) {
        this.webDescription = webDescription;
    }

    /**
     * @return the webKeywords
     */
    public String getWebKeywords() {
        return webKeywords;
    }

    /**
     * @param webKeywords the webKeywords to set
     */
    public void setWebKeywords(String webKeywords) {
        this.webKeywords = webKeywords;
    }

    /**
     * @return the menu
     */
    public String getMenu() {
        return menu;
    }

    /**
     * @param menu the menu to set
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    public PageLocale() {
    }

    public PageLocale(String menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "PageLocale [webTitle=" + webTitle + ", webDescription=" + webDescription + ", webKeywords=" + webKeywords + ", menu=" + menu + ", title=" + title + ", description=" + description
                + ", content=" + content + "]";
    }

}

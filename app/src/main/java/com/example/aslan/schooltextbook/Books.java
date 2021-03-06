package com.example.aslan.schooltextbook;

import java.util.Date;

/**
 * Created by sst on 6/23/16.
 */
public class Books {

    private String name;
    private String authors;
    private String cover_image;
    private String objectId;
    private String publisher;
    private int published_at;
    private String ISBN;
    private String contingent;
    private String language;
    private int publisher_price, publisher_quantity, id;
    private Date created;

    public int getPublisher_price() {
        return publisher_price;
    }

    public void setPublisher_price(int publisher_price) {
        this.publisher_price = publisher_price;
    }

    public int getPublisher_quantity() {
        return publisher_quantity;
    }

    public void setPublisher_quantity(int publisher_quantity) {
        this.publisher_quantity = publisher_quantity;
    }

    public String getCover_image() {
        return cover_image;
    }

    public void setCover_image(String cover_image) {
        this.cover_image = cover_image;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublished_at() {
        return published_at;
    }

    public void setPublished_at( int published_at ) {
        this.published_at = published_at;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getContingent() {
        return contingent;
    }

    public void setContingent(String contingent) {
        this.contingent = contingent;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


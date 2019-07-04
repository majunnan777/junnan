package com.bytedance.android.lesson.restapi.solution.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author Xavier.S
 * @date 2019.01.17 18:08
 */
public class Cat {

    @SerializedName("id")
    String id;

    @SerializedName("url")
    String url;

    @SerializedName("width")
    String width;

    @SerializedName("height")
    String height;

    @SerializedName("breeds")
    String breeds;

    @SerializedName("categories")
    String categories;

    public Cat(String url){
        this.url = url;
    }

    public String getCategories(){
        return categories;
    }

    public  void setCategories(String categories){
        this.categories = categories;
    }

    public  String getBreeds(){
        return breeds;
    }

    public void setBreeds(String breeds){
        this.breeds = breeds;
    }

    public String getHeight(){
        return height;
    }

    public  void setHeight(String height){
        this.height= height;
    }

    public  String getWidth(){
        return  width;
    }

    public  void setWidth(String width){
        this.width = width;
    }

    public  String getUrl(){
        return url;
    }

    public  void setUrl(String url){
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    // TODO-C1 (1) Implement your Cat Bean here according to the response json
}

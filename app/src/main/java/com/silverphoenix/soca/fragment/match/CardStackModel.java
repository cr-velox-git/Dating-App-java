package com.silverphoenix.soca.fragment.match;

public class CardStackModel {

    private String image,name, age,city;

    public CardStackModel(String image, String name, String age, String city) {
        this.image = image;
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

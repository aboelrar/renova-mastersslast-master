package com.example.lenovo.renova.list;

public class doctorlist {
    String name, fees, location, speclist,lat,lng;
    String profileImage;
    Boolean isFav = false;
    int id,favnum;
    float numStars;


    public doctorlist(Boolean isFav) {
        this.isFav = isFav;
    }

    public doctorlist(String name, String fees, String location, String speclist, String profileImage, int id,float numStars,int favnum,String lat,String lng) {
        this.name = name;
        this.fees = fees;
        this.location = location;
        this.speclist = speclist;
        this.profileImage = profileImage;
        this.id = id;
        this.numStars=numStars;
        this.favnum=favnum;
        this.lat=lat;
        this.lng=lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSpeclist() {
        return speclist;
    }

    public void setSpeclist(String speclist) {
        this.speclist = speclist;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean getFav() {
        return isFav;
    }

    public void setFav(Boolean fav) {
        isFav = fav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getNumStars() {
        return numStars;
    }

    public void setNumStars(float numStars) {
        this.numStars = numStars;
    }

    public int getFavnum() {
        return favnum;
    }

    public void setFavnum(int favnum) {
        this.favnum = favnum;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}

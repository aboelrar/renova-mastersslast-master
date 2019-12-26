package com.example.lenovo.renova.list;

public class appoientmentList  {
    String docName,spechlist,price,address,from,to,date,lat,lng,image;
    int id;
    float starNums;


    public appoientmentList(String docName, String spechlist, String price, String address, String from, String to,String date,int id,float starNums,String lat,String lng,String image) {
        this.docName = docName;
        this.spechlist = spechlist;
        this.price = price;
        this.address = address;
        this.from = from;
        this.to = to;
        this.date=date;
        this.lat=lat;
        this.lng=lng;
        this.image=image;
        this.id=id;
        this.starNums=starNums;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getSpechlist() {
        return spechlist;
    }

    public void setSpechlist(String spechlist) {
        this.spechlist = spechlist;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getStarNums() {
        return starNums;
    }

    public void setStarNums(float starNums) {
        this.starNums = starNums;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

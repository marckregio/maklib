package com.marckregio.firebase.model;

/**
 * Created by eCopy on 9/29/2017.
 */

public class SampleModel {

    public final static String USERID = "userid";
    public final static String NAME = "name";
    public final static String NUMBER = "number";

    private String userid;
    private String name;
    private String number;

    public SampleModel(){
        //Declare Default Constructor for Firebase Reference
    }

    public SampleModel(String userid, String name, String number){
        this.userid = userid;
        this.name = name;
        this.number = number;
    }

    public String getUserid(){
        return userid;
    }

    public String getName(){
        return name;
    }

    public String getNumber(){
        return number;
    }
}

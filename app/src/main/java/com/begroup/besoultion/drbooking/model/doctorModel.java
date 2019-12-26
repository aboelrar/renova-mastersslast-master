package com.begroup.besoultion.drbooking.model;

import com.begroup.besoultion.drbooking.list.doctorlist;
import com.begroup.besoultion.drbooking.list.tasneefList;
import com.begroup.besoultion.drbooking.interfacee.searchInterface;

import java.util.ArrayList;

public class doctorModel implements searchInterface.interfaces.Model {
    @Override
    public ArrayList getdata() {
        ArrayList<doctorlist>arrayList=new ArrayList<doctorlist>();
        return arrayList; }

    @Override
    public ArrayList getTasneefData() {
        ArrayList<tasneefList>arrayList=new ArrayList<tasneefList>();
        arrayList.add(new tasneefList("تصنيف"));
        arrayList.add(new tasneefList("الاعلى سعرا"));
        arrayList.add(new tasneefList("الاقل سعرا"));
        arrayList.add(new tasneefList("الاكثر تقييما"));
        return arrayList;
    }
}

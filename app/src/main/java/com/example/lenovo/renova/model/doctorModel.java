package com.example.lenovo.renova.model;

import com.example.lenovo.renova.R;
import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.interfacee.searchInterface;
import com.example.lenovo.renova.list.doctorlist;
import com.example.lenovo.renova.list.tasneefList;

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

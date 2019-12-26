package com.example.lenovo.renova.model;

import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.list.reserveList;

import java.util.ArrayList;

public class reservationModel implements MVP.interfaces.Model {
    @Override
    public ArrayList getdata() {
        ArrayList<reserveList>arrayList=new ArrayList<reserveList>();

         return arrayList;
    }
}

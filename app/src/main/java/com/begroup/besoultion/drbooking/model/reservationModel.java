package com.begroup.besoultion.drbooking.model;

import com.begroup.besoultion.drbooking.list.reserveList;
import com.begroup.besoultion.drbooking.interfacee.MVP;

import java.util.ArrayList;

public class reservationModel implements MVP.interfaces.Model {
    @Override
    public ArrayList getdata() {
        ArrayList<reserveList>arrayList=new ArrayList<reserveList>();

         return arrayList;
    }
}

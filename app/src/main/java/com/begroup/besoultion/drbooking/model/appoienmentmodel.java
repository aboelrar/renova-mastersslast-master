package com.begroup.besoultion.drbooking.model;

import com.begroup.besoultion.drbooking.list.productList;
import com.begroup.besoultion.drbooking.interfacee.MVP;

import java.util.ArrayList;

public class appoienmentmodel  implements MVP.interfaces.Model {
    @Override
    public ArrayList getdata() {
        ArrayList<productList>arrayList=new ArrayList<productList>();

        return arrayList;
    }


}
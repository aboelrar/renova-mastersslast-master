package com.example.lenovo.renova.model;

import com.example.lenovo.renova.R;
import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.interfacee.searchInterface;
import com.example.lenovo.renova.list.productList;

import java.util.ArrayList;

public class productModel implements MVP.interfaces.Model {
    @Override
    public ArrayList getdata() {
        ArrayList<productList>arrayList=new ArrayList<productList>();

        return arrayList;
    }


}

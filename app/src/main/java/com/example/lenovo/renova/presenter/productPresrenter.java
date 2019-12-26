package com.example.lenovo.renova.presenter;

import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.model.productModel;
import com.example.lenovo.renova.model.reservationModel;

import java.util.ArrayList;

public class productPresrenter implements MVP.interfaces.presenter {
    private MVP.interfaces.Model model;
    private MVP.interfaces.View views;


    public productPresrenter(MVP.interfaces.View view) {
        this.views = view;
        initPresenter();
    }
    private void initPresenter() {
        model = new productModel();
        views.element();
    }

    @Override
    public void getData() {
        ArrayList data=model.getdata();
        views.setviewdata(data);
    }
}
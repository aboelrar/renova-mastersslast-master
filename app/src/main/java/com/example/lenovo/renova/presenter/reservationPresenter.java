package com.example.lenovo.renova.presenter;

import com.example.lenovo.renova.interfacee.MVP;
import com.example.lenovo.renova.model.reservationModel;

import java.util.ArrayList;

public class reservationPresenter implements MVP.interfaces.presenter {
private MVP.interfaces.Model model;
private MVP.interfaces.View views;


public reservationPresenter(MVP.interfaces.View view) {
        this.views = view;
        initPresenter();
        }
private void initPresenter() {
        model = new reservationModel();
        views.element();
        }

@Override
public void getData() {
        ArrayList data=model.getdata();
        views.setviewdata(data);
        }
        }
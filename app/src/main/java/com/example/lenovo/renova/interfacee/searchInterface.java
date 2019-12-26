package com.example.lenovo.renova.interfacee;

import java.util.ArrayList;

public interface searchInterface {

    public interface interfaces {
        interface  View
        {
            void element();
            void setviewdata(ArrayList data);
            void setViewtasneefData(ArrayList data);
        }
        interface Model{
            ArrayList getdata();
            ArrayList getTasneefData();
        }
        interface presenter{
            void getData();
            void getTasneefData();
        }

    }
}


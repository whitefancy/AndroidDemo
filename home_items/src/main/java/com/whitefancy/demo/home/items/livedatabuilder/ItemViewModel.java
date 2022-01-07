package com.whitefancy.demo.home.items.livedatabuilder;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ItemViewModel extends ViewModel {
    private MutableLiveData<List> alltypes;

    public MutableLiveData<List> getAlltypes() {
        if (alltypes == null) {
            alltypes = new MutableLiveData<List>();
        }
        return alltypes;
    }
}

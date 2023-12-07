package com.example.easyhealthy.ui.dinhDuong;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DinhDuongViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DinhDuongViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
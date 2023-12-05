package com.example.easyhealthy.ui.hoso;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HoSoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HoSoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is hồ sơ fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
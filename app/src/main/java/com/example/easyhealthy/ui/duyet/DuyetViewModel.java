package com.example.easyhealthy.ui.duyet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DuyetViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DuyetViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is duyệt fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
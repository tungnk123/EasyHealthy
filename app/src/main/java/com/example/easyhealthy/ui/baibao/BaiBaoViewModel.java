package com.example.easyhealthy.ui.baibao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaiBaoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BaiBaoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is bài báo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.manusunny.pinlock;
public interface PinListener {

    int SUCCESS = 0;
    int CANCELLED = 1;
    int INVALID = 3;
    int FORGOT = 4;
    void onCompleted(String pin);
    void onPinValueChange(int length);
    void onForgotPin();
}

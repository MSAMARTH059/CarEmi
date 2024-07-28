package com.smart.caremi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class CarService extends Service {
    public CarService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    ICarService.Stub stub = new ICarService.Stub() {
        @Override
        public float carcal(float pa, float dp, float ir, int lt) throws RemoteException {
            float emiamount;
            float principalamt = pa - dp;
            float Interest = ((ir / 12) / 100);
            emiamount = (float) ((float) (principalamt * Interest) *
                    (Math.pow((1 + Interest), lt)) / ((Math.pow((1 + Interest), lt)) - 1));
            return emiamount;
        }
    };
}

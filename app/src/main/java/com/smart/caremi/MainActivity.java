package com.smart.caremi;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button calbtn;
    TextView displayres;
    EditText pamt, downp, intrate, loanterm;
    ICarService cService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        pamt = findViewById(R.id.editTextText);
        downp = findViewById(R.id.editTextText2);
        intrate = findViewById(R.id.editTextText3);
        loanterm = findViewById(R.id.editTextText4);
        calbtn = findViewById(R.id.button);
        displayres = findViewById(R.id.textView6);

        calbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float principalamount = Float.valueOf(pamt.getText().toString());
                Float downpayment = Float.valueOf(downp.getText().toString());
                Float interestrate = Float.valueOf(intrate.getText().toString());
                int lnterm = Integer.parseInt(loanterm.getText().toString());

                try {
                    Float res = cService.carcal(principalamount, downpayment, interestrate, lnterm);
                    displayres.setText("Monthly EMI in Rs is :" + res);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        Intent it = new Intent(this, CarService.class);
        bindService(it, sconnection, BIND_AUTO_CREATE);
    }

    ServiceConnection sconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getBaseContext(), "Service Connected", Toast.LENGTH_LONG).show();
            cService = ICarService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
package com.example.utilizador.arduinomotion;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class Main3Activity extends AppCompatActivity {

    private static final int AskingEnable = 1;

    private static final int AskingConnect = 2;


    boolean Connection = false;

    private static String MAC = null;

    UUID My_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothAdapter MyBTAdapter = null;
    BluetoothDevice MyDevice = null;
    BluetoothSocket MySocket = null;

    Button BTConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        BTConnect= (Button)findViewById(R.id.button6);


        MyBTAdapter = BluetoothAdapter.getDefaultAdapter();

        if (MyBTAdapter == null) {
            Toast.makeText(getApplicationContext(), "Your device doesn't have Bluetooth", Toast.LENGTH_LONG).show();
        } else if (MyBTAdapter.isEnabled()) {
            Intent enableBtintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtintent, AskingEnable);
        }

        BTConnect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (Connection){
                    try{
                        MySocket.close();
                        Connection =false;
                        BTConnect.setText("Connect");
                        Toast.makeText(getApplicationContext(), "Bluetooth Disabled", Toast.LENGTH_LONG).show();
                    }catch (IOException error){
                        Toast.makeText(getApplicationContext(), "An error as Ocurred" + error , Toast.LENGTH_LONG).show();

                    }

                }else{
                    Intent openList = new Intent(Main3Activity.this,DeviceList.class);
                    startActivityForResult(openList,AskingConnect);
                }

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case AskingEnable:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Enabled", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getApplicationContext(), "Bluetooth couldn't Enabled", Toast.LENGTH_LONG).show();
                finish();
        }
        break;

            case AskingConnect:
                if (resultCode == RESULT_OK) {
                    MAC = data.getExtras().getString(DeviceList.MAC_Adress);
                    Toast.makeText(getApplicationContext(), "MAC Obtained:", Toast.LENGTH_LONG).show();
                    MyDevice = MyBTAdapter.getRemoteDevice(MAC);

                    try {
                        MySocket = MyDevice.createInsecureRfcommSocketToServiceRecord(My_UUID);

                        MySocket.connect();

                        Connection = true;

                        BTConnect.setText("Disconnect");

                        Toast.makeText(getApplicationContext(), "You have paired with:" + MAC, Toast.LENGTH_LONG).show();

                    } catch (IOException error){

                        Connection = false;
                        Toast.makeText(getApplicationContext(), "An error as ocurred" + error, Toast.LENGTH_LONG).show();

                    }


                }else{
                    Toast.makeText(getApplicationContext(), "Error obtaining MAC Adress", Toast.LENGTH_LONG).show();
                }



        }
    }
}

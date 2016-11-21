package com.example.utilizador.arduinomotion;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

/**
 * Created by Utilizador on 02/11/2016.
 */

public class DeviceList extends ListActivity {

    private BluetoothAdapter BTAdapter2 = null;



    static String MAC_Adress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        BTAdapter2 = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> PairedDevices = BTAdapter2.getBondedDevices();

        if (PairedDevices.size() > 0) {
            for (BluetoothDevice Device : PairedDevices) {
                String nameBT = Device.getName();
                String macBT = Device.getAddress();
                ArrayBluetooth.add(nameBT + "\n" + macBT);
            }
        }
        setListAdapter(ArrayBluetooth);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String GeneralInfo = ((TextView) v).getText().toString();

        Toast.makeText(getApplicationContext(),"Info" + GeneralInfo, Toast.LENGTH_LONG).show();

        String macAdress = GeneralInfo.substring(GeneralInfo.length() -17);

        Toast.makeText(getApplicationContext(),"mac" + macAdress, Toast.LENGTH_LONG).show();

        Intent macReturn = new Intent();
        macReturn.putExtra(MAC_Adress, macAdress);
        setResult(RESULT_OK,macReturn);
        finish();
    }
}

package vn.uits.bllutooth_android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothActivity extends AppCompatActivity {

    private static final String TAG = BluetoothActivity.class.getSimpleName();

    private ToggleButton mToggleButton;
    private BluetoothAdapter mBlueTooth;

    private LinearLayoutManager mLinearLayout;
    private List<String> mArrDevice = new ArrayList<>();
    private RecycleViewAdapter mAdapter;
    private RecyclerView mRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToggleButton = findViewById(R.id.mToggleButton);
        mRecycleView = findViewById(R.id.mRecycleView);

        mLinearLayout = new LinearLayoutManager(this);
        mBlueTooth = BluetoothAdapter.getDefaultAdapter();

        mToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mToggleButton.isChecked()) {
                    try {
                        Intent bluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                        bluetooth.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                        startActivity(bluetooth);
                    } catch (Exception e) {
                        Log.e(TAG, "onClick: " + e.getMessage());
                    }
                } else {
                    mBlueTooth.disable();
                }
            }
        });

        mAdapter = new RecycleViewAdapter(mArrDevice);
        mRecycleView.setLayoutManager(mLinearLayout);
        mRecycleView.setAdapter(mAdapter);

    }

    public void onSearch(View view) {
        searchDevices();
    }

    /**
     * search Bluetooth
     */
    private void searchDevices() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> listDevices = adapter.getBondedDevices();

        List<String> stringList = new ArrayList<>();
        for (BluetoothDevice devices : listDevices) {
            stringList.add(devices.getName());
        }
        mArrDevice.addAll(stringList);
        mAdapter.notifyDataSetChanged();
    }
}

package com.barafael.dodecaControl;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.barafael.dodecaControl.databinding.ActivityManualModeBinding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ManualModeActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityManualModeBinding binding;

    private Button goButton, goBackButton;

    private SeekBar r, g, b;

    private EditText stripEntry, indexEntry;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManualModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        goButton = findViewById(R.id.go_manual);
        goBackButton = findViewById(R.id.back_to_communicate);

        r = findViewById(R.id.red_slide);
        g = findViewById(R.id.green_slide);
        b = findViewById(R.id.blue_slide);

        r.setMax(255);
        g.setMax(255);
        b.setMax(255);

        stripEntry = findViewById(R.id.strip_entry);
        indexEntry = findViewById(R.id.index_entry);

        goButton.setOnClickListener(v -> onDataActivation());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onDataActivation() {
        byte[] array = {(byte)'o', 0, 4, 0, 0, 0};

        array[1] = (byte)(Integer.parseInt(stripEntry.getText().toString()));
        array[2] = (byte)(Integer.parseInt(indexEntry.getText().toString()));

        array[3] = (byte)(r.getProgress());
        array[4] = (byte)(g.getProgress());
        array[5] = (byte)(b.getProgress());

        OutputStream os = BluetoothSingleton.getInstance().getBluetoothDeviceInterface().getDevice().getOutputStream();

        try {
            os.write(array);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
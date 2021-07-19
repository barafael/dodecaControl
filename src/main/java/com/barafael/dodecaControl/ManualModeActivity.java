package com.barafael.dodecaControl;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.barafael.dodecaControl.databinding.ActivityManualModeBinding;

import java.io.IOException;
import java.io.OutputStream;

public class ManualModeActivity extends AppCompatActivity {

    private ActivityManualModeBinding binding;

    private Button goButton;

    private SeekBar r, g, b;

    private EditText stripEntry, indexEntry;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityManualModeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        goButton = findViewById(R.id.go_manual);

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
        byte[] array = {(byte) 'o', 0, 0, 0, 0, 0};

        try {
            array[1] = (byte) (Integer.parseInt(stripEntry.getText().toString()));
            array[2] = (byte) (Integer.parseInt(indexEntry.getText().toString()));
        } catch (NumberFormatException n) {
            n.printStackTrace();
            System.err.println("Using (0, 0) as fallback in manual mode");
        }

        array[3] = (byte) (r.getProgress());
        array[4] = (byte) (g.getProgress());
        array[5] = (byte) (b.getProgress());

        OutputStream os = BluetoothSingleton.getInstance().getBluetoothDeviceInterface().getDevice().getOutputStream();

        try {
            os.write(array);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
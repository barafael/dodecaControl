package com.barafael.dodecaControl;

import com.harrysoft.androidbluetoothserial.SimpleBluetoothDeviceInterface;

public class BluetoothSingleton {
    private BluetoothSingleton() {}

    public static BluetoothSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BluetoothSingleton();
        }
        return INSTANCE;
    }

    public void setInterface(SimpleBluetoothDeviceInterface deviceInterface) {
        this.bluetoothDeviceInterface = deviceInterface;
    }

    public SimpleBluetoothDeviceInterface getBluetoothDeviceInterface() {
        return bluetoothDeviceInterface;
    }

    private static BluetoothSingleton INSTANCE = null;

    private SimpleBluetoothDeviceInterface bluetoothDeviceInterface;
}

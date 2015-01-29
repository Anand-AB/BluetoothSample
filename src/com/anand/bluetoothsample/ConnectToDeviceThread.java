package com.anand.bluetoothsample;

import java.util.UUID;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public class ConnectToDeviceThread extends Thread {
	private final BluetoothDevice mmDevice;
	String msg;

	public ConnectToDeviceThread(BluetoothDevice device, String message) {
		mmDevice = device;
		msg=message;
		System.out.println("Connecting to Device..");
		System.out.println(mmDevice.getName()+"\t"+mmDevice.getAddress());
	}

	@SuppressLint("NewApi")
	public void run() {
		BluetoothAdapter myBluetooth=BluetoothAdapter.getDefaultAdapter();
		myBluetooth.cancelDiscovery();

		BluetoothSocket tmp = null;

		try {
			tmp = mmDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			tmp.connect();
			tmp.getOutputStream().write(msg.getBytes());
			tmp.close();	

		} catch (Exception e) { System.out.println("ERROR"); }

	}



}
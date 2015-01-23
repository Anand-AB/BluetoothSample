package com.anand.bluetoothsample;

import java.io.InputStream;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

public class ConnectionAcceptThread extends Thread {
	private BluetoothServerSocket mServerSocket=null;
	public ConnectionAcceptThread(BluetoothAdapter mBluetoothAdapter) {
		try {
			mServerSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(mBluetoothAdapter.getName(), UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			System.out.println("Server Socket Running succesfully");
		} catch (Exception e) { 
			System.out.println("ERROR ACCEPT Method constructor");

			System.out.println(e.getMessage());
		}
	}

	public void run() {
		BluetoothSocket socket = null;
		while (true) {
			try {
				System.out.println("Waiting for Connection Accept");
				socket = mServerSocket.accept();
				System.out.println("Connected");

				if (socket != null) {
					
					byte buffer[]=new byte[512];
					String message="";
					try {
						InputStream ins=socket.getInputStream();
						while(ins.read(buffer) > 0){
							message=""+new String(buffer);
						}

						System.out.println("=======================================\nConected inside Accept Thread");
						System.out.println("Address - "+socket.getRemoteDevice().getAddress());
						System.out.println("Name - "+socket.getRemoteDevice().getName());		
						System.out.println("Message - "+message+""
								+ "\n=======================================\n");

						socket.close();                                                    
						ins.close();
					} catch (Exception e) {
						System.out.println("ERROR ACCEPT Method manageConnectedSocket");
						e.printStackTrace();
					}
					break;
				}
			} catch (Exception e) {
				System.out.println("ERROR ACCEPT Method run");

				System.out.println(e.getMessage());
				break;
			}
		}
	}

	public void cancel() {
		try {
			mServerSocket.close();
		} catch (Exception e) {
			System.out.println("ERROR ACCEPT Method cancel");

			System.out.println(e.getMessage());
		}
	}
}

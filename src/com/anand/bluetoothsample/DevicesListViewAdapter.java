package com.anand.bluetoothsample;

import java.util.ArrayList;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DevicesListViewAdapter extends ArrayAdapter<BluetoothDevice> {
	public static String adrr;
	ArrayList<BluetoothDevice> scannedDevices;
	Context myContext;

	public DevicesListViewAdapter(Context context, ArrayList<BluetoothDevice> scannedDevices) {
		super(context,R.layout.list_item, scannedDevices);
		this.myContext=context;
		this.scannedDevices=scannedDevices;
	}

	@Override
	public View getDropDownView(final int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}


	private View getCustomView(final int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			LayoutInflater mLayoutInflater = LayoutInflater.from(myContext);
			convertView = mLayoutInflater.inflate(R.layout.list_item, parent, false);
		}

		TextView nameTextView = (TextView) convertView.findViewById(R.id.txtview_name);
		TextView btAddressTextView = (TextView) convertView.findViewById(R.id.txtview_btaddress);
		//		ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView_photo);

		nameTextView.setText(scannedDevices.get(position).getName());
		btAddressTextView.setText(scannedDevices.get(position).getAddress());
		adrr=scannedDevices.get(position).getAddress();
		
		convertView.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) {

				Toast.makeText(getContext(),"Clicked "+scannedDevices.get(position).getName(), Toast.LENGTH_LONG).show();
				new ConnectToDeviceThread(scannedDevices.get(position)).start();
			}
		});

		return convertView;
	}
}

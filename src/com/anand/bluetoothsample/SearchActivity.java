package com.anand.bluetoothsample;

import java.util.ArrayList;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class SearchActivity extends ActionBarActivity {

	public final int REQUEST_ENABLE_BT_CONST=1;
	BroadcastReceiver mReceiver ;
	BluetoothAdapter mBluetoothAdapter;
	boolean bluetoothEnabled=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()){
			bluetoothEnabled=true;
			new ConnectionAcceptThread(mBluetoothAdapter).start();

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch ( item.getItemId()) {
		case R.id.action_list_pired_devices:
			Toast.makeText(getApplicationContext(), "You pressed List Paired Devices",Toast.LENGTH_SHORT).show();
			listPairedDevices();
			return true;

		case R.id.action_scan_devices:
			Toast.makeText(getApplicationContext(), "You pressed Scan",Toast.LENGTH_SHORT).show();
			//scanBluetoothDevices();
			doDiscovery();
			return true;

		case R.id.action_turn_on:
			Toast.makeText(getApplicationContext(), "You pressed Turn On/Off",Toast.LENGTH_SHORT).show();
			checkBluetoothStatus();
			return true;

		default:
			break;
		}
		return true;
	}

	private void checkBluetoothStatus(){
		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			Toast.makeText(getApplicationContext(), "No Bluetooth found",Toast.LENGTH_SHORT).show();
			bluetoothEnabled=false;
		}
		else if (!mBluetoothAdapter.isEnabled()) {
			startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), REQUEST_ENABLE_BT_CONST);
			Toast.makeText(getApplicationContext(), "Please Turn on Bluetooth", Toast.LENGTH_LONG).show();

		}
		else if(mBluetoothAdapter!=null && mBluetoothAdapter.isEnabled()){
			Toast.makeText(getApplicationContext(), "Turning off Bluetooth", Toast.LENGTH_LONG).show();
			mBluetoothAdapter.disable();
		}
	}

	private void listPairedDevices() {
		
		
		//Bluetooth should be available and turned on
	
		if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled() && bluetoothEnabled){
			//scanning for previously paired devices
			ArrayList<BluetoothDevice> listOfDevices = new ArrayList<BluetoothDevice>(mBluetoothAdapter.getBondedDevices());
			if (listOfDevices.size() > 0) {
				DevicesListViewAdapter mArrayAdapter=new DevicesListViewAdapter(getApplicationContext(), listOfDevices);
				ListView myListview = (ListView)findViewById(R.id.activity_main_paired_listView);
				myListview .setAdapter(mArrayAdapter);
			}
		}
	}

	public void doDiscovery() {
		
		ArrayList<BluetoothDevice> listOfDevices = new ArrayList<BluetoothDevice>();
		final DevicesListViewAdapter mArrayAdapter=new DevicesListViewAdapter(getApplicationContext(), listOfDevices);
		ListView myListview = (ListView)findViewById(R.id.activity_main_new_listView);
		myListview .setAdapter(mArrayAdapter);
		
		System.out.println("doDiscovery()"); 

		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle(R.string.scanning);


		// If we're already discovering, stop it
		if (mBluetoothAdapter.isDiscovering()) {
			mBluetoothAdapter.cancelDiscovery();
		}

		// Request discover from BluetoothAdapter
		mBluetoothAdapter.startDiscovery();
		
		// Create a BroadcastReceiver for ACTION_FOUND
					if(mReceiver==null){
						mReceiver = new BroadcastReceiver() {
							@Override
							public void onReceive(Context context, Intent intent) {
								String action = intent.getAction();
								// When discovery finds a device
								if (BluetoothDevice.ACTION_FOUND.equals(action)) {
									// Get the BluetoothDevice object from the Intent
									BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
									// Add the name and address to an array adapter to show in a ListView
									mArrayAdapter.add(device);
									mArrayAdapter.notifyDataSetChanged();
								}
							}
						};
						// Register the BroadcastReceiver
						IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
						registerReceiver(mReceiver, filter);
					}
	}

	private void scanBluetoothDevices() {
		if(mBluetoothAdapter != null && mBluetoothAdapter.isEnabled() && bluetoothEnabled){

			ArrayList<BluetoothDevice> listOfDevices = new ArrayList<BluetoothDevice>();
			final DevicesListViewAdapter mArrayAdapter=new DevicesListViewAdapter(getApplicationContext(), listOfDevices);
			ListView myListview = (ListView)findViewById(R.id.activity_main_new_listView);
			myListview .setAdapter(mArrayAdapter);

			//	 Make sure we're not doing discovery anymore
			//			if (mBluetoothAdapter!= null) {
			//				mBluetoothAdapter.cancelDiscovery();
			//			}
			mBluetoothAdapter.startDiscovery();

			// Create a BroadcastReceiver for ACTION_FOUND
			if(mReceiver==null){
				mReceiver = new BroadcastReceiver() {
					@Override
					public void onReceive(Context context, Intent intent) {
						String action = intent.getAction();
						// When discovery finds a device
						if (BluetoothDevice.ACTION_FOUND.equals(action)) {
							// Get the BluetoothDevice object from the Intent
							BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
							// Add the name and address to an array adapter to show in a ListView
							mArrayAdapter.add(device);
							mArrayAdapter.notifyDataSetChanged();
						}
					}
				};
				// Register the BroadcastReceiver
				IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
				registerReceiver(mReceiver, filter);
			}
		}
	}
}



//		String prodts=(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())).getString("productlist", "");
//		StringTokenizer stk=new StringTokenizer(prodts, "|");
//		StringTokenizer stk2=null;
//		int no_of_prdts = stk.countTokens();
//		String[] ids = new String[no_of_prdts];
//		String[] pnames = new String[no_of_prdts];
//		String[] qtys = new String[no_of_prdts];
//		String[] prices = new String[no_of_prdts];
//		for (int i = 0; i < no_of_prdts; i++) {
//			stk2=new StringTokenizer(stk.nextToken(), ",");
//			ids[i]=((stk2.nextToken()));
//
//
//			pnames[i]=(stk2.nextToken());
//			qtys[i]=((stk2.nextToken()));
//			prices[i]=((stk2.nextToken()));




//				phone=(stk2.nextToken());
//				photo=(stk2.nextToken());
//				if(usename.equalsIgnoreCase(username_entered) && passw.equals(password_entered)){
//					loginStatus=true;
//					Editor e=(PreferenceManager.getDefaultSharedPreferences(getApplicationContext())).edit();
//					e.putString("username", username_entered);
//					e.putString("password", password_entered);
//					e.putString("name", stk2.nextToken());
//					e.putString("email", stk2.nextToken());
//					e.putLong("phone",Long.parseLong(stk2.nextToken()));
//					e.commit();
//					break;
package com.anand.bluetoothsample;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

public class ChatActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sample_content_fragment);

		if (savedInstanceState == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			BluetoothChatFragment fragment = new BluetoothChatFragment();
			transaction.replace(R.id.sample_content_fragment, fragment);
			transaction.commit();
		}



	}

	//	public void sendMessage(String myMessage){
	//
	//		if(myMessage!=null){
	//			new ConnectToDeviceThread(currentDevice, myMessage).start();
	//		}else{
	//			Toast.makeText(getApplicationContext(), "Please Enter a Messsage to send", Toast.LENGTH_SHORT).show();
	//		}
	//
	//
	//	}

}


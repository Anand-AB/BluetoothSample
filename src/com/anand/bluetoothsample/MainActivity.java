package com.anand.bluetoothsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



		((ListView) findViewById(R.id.my_main_listview)).setAdapter((new UserAdapter(getApplicationContext(), createDummyUserModels())));
	}

	public void search(View v)
	{
		Toast.makeText(getApplicationContext(), "Plus touched", Toast.LENGTH_SHORT).show();
		startActivity(new Intent(this,SearchActivity.class));

	}
	public UserModel[] createDummyUserModels(){


		String[] usern={"cap","watch","tv","mobile","bags", "shoes","books","spray","computer", "ac"};
		String[] msg={"yoyo","watch","tv","mobile","bags", "shoes","books","spray","computer", "ac"};
		String[] time={"1.0","5.00","4.00","6.00","8.00", "10.00","2.00","11.00","12.00", "5.05"};
		int[] count={1,10,4,3,1, 2,1,6,1,5};

		UserModel[] userlist=new UserModel[count.length];
		for (int i = 0; i < count.length; i++) {
			userlist[i]=new UserModel((usern[i]),msg[i],(time[i]),(count[i]));
		}
		return userlist;

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
package com.timore.appegroup;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;


public class NotifMsgDialog extends Dialog implements View.OnClickListener{

	Activity activity;
	Button btnOk;
	private TextView text;
	private ImageView image;

	public NotifMsgDialog(Activity context,String message,String image) {
		super(context);
		this.activity = context;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_notif_message);


		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		Window window = this.getWindow();
		lp.copyFrom(window.getAttributes());
		//This makes the dialog take up the full width
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);
		initilize(message,image);
	}
	private void initilize(String s, String img) {
		image = (ImageView)findViewById(R.id.dialog_notifMsg_image);
		if(img!=null){
			new AQuery(activity).id(image).image(img);
		}
		text = (TextView)findViewById(R.id.dialog_notifMsg_text);
		text.setText(s);
		btnOk = (Button)findViewById(R.id.dialog_notifMsg_ok);
		btnOk.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.dialog_notifMsg_ok) {
			dismiss();


		} else {
		}
	}

}

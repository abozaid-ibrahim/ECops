package co.ecops.platform.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import co.ecops.platform.R;
import co.ecops.platform.utils.SuperActivity;
import co.ecops.platform.utils.Utils;


/**
 * A placeholder fragment containing a simple view.
 */
public class EmailLoginActivity extends SuperActivity implements View.OnClickListener {


    private EditText mobileEt;
    private EditText passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        super.initToolbar(findViewById(R.id.toolbar));
        findViewById();
    }


    private void findViewById() {
        findViewById(R.id.loginact_btn_signin).setOnClickListener(this);
        findViewById(R.id.loginact_btn_signup).setOnClickListener(this);
        findViewById(R.id.loginact_btn_skip).setOnClickListener(this);
        mobileEt = (EditText) findViewById(R.id.login_et_mobile);
        passwordEt = (EditText) findViewById(R.id.login_et_pw);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginact_btn_signin:
                asyncSignup();
                break;
            case R.id.loginact_btn_signup:
                break;
            case R.id.loginact_btn_skip:
                Intent main = new Intent(getBaseContext(), MainActivity.class);
                Utils.startActivity(this, main);
                break;
        }
    }


    private void asyncSignup() {
        Map<String, Object> params = new HashMap<String, Object>();


        params.put("action", "login");
        params.put("phid", "17");
        params.put("mobile", mobileEt.getText().toString());
        params.put("pass", passwordEt.getText().toString());


        String url = "action=login&mobile=123&pass=asd&phid=1";

    }
}

package co.ecops.platform.views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import co.ecops.platform.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class SignupFragment extends Fragment implements View.OnClickListener {

    View layout;
    private Button signupBtn;
    private Button signinBtn;
    private EditText mobileEt;
    private EditText passwordEt;
    private EditText addressEt;
    private EditText emailEt;
    private EditText nameEt;
    private TextView zoneLbl;
    private RadioButton maleRadioBtn;

    public SignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.layout_signup, container, false);
        findViewById();
        return layout;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        signupBtn.setOnClickListener(this);
        signinBtn.setOnClickListener(this);


        asynCities();

    }

    private void findViewById() {
        signupBtn = (Button) layout.findViewById(R.id.signup_btn_signup);
        signinBtn = (Button) layout.findViewById(R.id.signup_btn_signin);
        addressEt = (EditText) layout.findViewById(R.id.signup_et_address);
        emailEt = (EditText) layout.findViewById(R.id.signup_et_email);
        nameEt = (EditText) layout.findViewById(R.id.signup_et_name);


        mobileEt = (EditText) layout.findViewById(R.id.login_et_mobile);
        passwordEt = (EditText) layout.findViewById(R.id.login_et_pw);

        maleRadioBtn = (RadioButton) layout.findViewById(R.id.signup_rb_male);


    }


    private void asynCities() {
        //"action=cities&countid=1&"
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_btn_signup:
                async_signup();
                break;
            case R.id.signup_btn_signin:

                getActivity().onBackPressed();
                break;
        }
    }


    private void async_signup() {


        Map<String, Object> params = new HashMap<String, Object>();

        params.put("action", "signup");
        params.put("phid", "17");
        params.put("name", nameEt.getText().toString());
        params.put("mail", emailEt.getText().toString());
        params.put("mobile", mobileEt.getText().toString());
        params.put("pass", passwordEt.getText().toString());
        params.put("address", addressEt.getText().toString());
        params.put("cityid", "1");


        params.put("zoneid", "1");
        String gender = maleRadioBtn.isChecked() ? "0" : "1";
        params.put("gender", gender);
        sendSignup(params);

    }

    private void sendSignup(final Map<String, Object> params) {


    }

}

package com.magical;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.firstName)
    EditText nameEditText;

    @BindView(R.id.emailEditText)
    EditText emailEditText;

    @BindView(R.id.mobileNumberEditText)
    EditText mobileNumberEditText;

    @BindView(R.id.passwordEditText)
    EditText passwordEditText;

    @BindView(R.id.confirPasswordEditText)
    EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        if (sharedPreferences.contains("Email") && sharedPreferences.contains("MobileNumber")) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
    }

    @OnClick(R.id.createAccountButton)
    void onClick() {
        if (checkContent(nameEditText)) {
            nameEditText.setError(getString(R.string.nameErrorMessage));
        } else if (checkContent(emailEditText)) {
            emailEditText.setError(getString(R.string.emailErrorMessage));
        } else if (checkContent(mobileNumberEditText)) {
            mobileNumberEditText.setError(getString(R.string.mobileErrorMessage));
        } else if (checkContent(passwordEditText)) {
            passwordEditText.setError(getString(R.string.passwordErrorMessage));
        } else if (checkContent(confirmPasswordEditText)) {
            confirmPasswordEditText.setError(getString(R.string.confirmPasswordError));
        } else if (!checkPassword()) {
            Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Name", nameEditText.getText().toString());
            editor.putString("Email", emailEditText.getText().toString());
            editor.putString("MobileNumber", mobileNumberEditText.getText().toString());
            editor.putString("Password", passwordEditText.getText().toString());
            editor.apply();
            Toast.makeText(this, "Account created successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            finish();
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private boolean checkContent(EditText editText) {
        if (TextUtils.isEmpty(editText.getText().toString())) {
            return true;
        }
        return false;
    }

    private boolean checkPassword() {
        if (TextUtils.equals(passwordEditText.getText().toString(), confirmPasswordEditText.getText().toString())) {
            return true;
        }
        return false;
    }
}

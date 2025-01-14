package dev.mobprog.techhub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.mobprog.techhub.models.User;


public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPass;
    private Button loginBtn;
    private TextView tvRegis;

    private HelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        db = new HelperClass(this);

        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        loginBtn = findViewById(R.id.et_loginBtn);
        tvRegis = findViewById(R.id.tv_register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                String password = etPass.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Password Tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(db.login(email, password)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    User user = new User(etEmail.getText().toString(), etPass.getText().toString());
                    
                    Session.getInstance().setUser(user);
                }else{
                    Toast.makeText(LoginActivity.this, "Email tidak ada", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        tvRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

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
public class RegisterActivity extends AppCompatActivity {
    private EditText etEmail, etPass, etConfPass;
    private Button regisBtn;
    private TextView tvLogin;
    private User user;

    private HelperClass db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        db = new HelperClass(this);

        etEmail = findViewById(R.id.et_email);
        etPass = findViewById(R.id.et_password);
        etConfPass = findViewById(R.id.et_ConfirmPass);
        regisBtn = findViewById(R.id.et_regisBtn);
        tvLogin = findViewById(R.id.tv_login);

        regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString().trim();
                String password = etPass.getText().toString().trim();
                String confirmPass = etConfPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Format email tidak valid", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.isEmailExists(email)) {
                    Toast.makeText(RegisterActivity.this, "Email sudah terdaftar", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password harus minimal 6 karakter", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confirmPass)) {
                    Toast.makeText(RegisterActivity.this, "Konfirmasi password harus diisi", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.equals(password, confirmPass)) {
                    Toast.makeText(RegisterActivity.this, "Konfirmasi password harus sama", Toast.LENGTH_SHORT).show();
                    return;
                }

                user = new User(email, confirmPass);

                if (db.register(user)) {
                    Toast.makeText(RegisterActivity.this, "Berhasil buat akun", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Gagal membuat akun, coba lagi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Metode untuk memvalidasi format email
     *
     * @param email Email yang akan diperiksa
     * @return true jika email valid, false jika tidak
     */
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}

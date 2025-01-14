package dev.mobprog.techhub;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dev.mobprog.techhub.models.Language;

public class MainActivity extends AppCompatActivity {
    ImageView flagBtn,homeBtn, bookmarkBtn;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Session.getInstance().setLanguage(new Language("English", "en"));
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ArticleFragment af = new ArticleFragment();
        fragmentTransaction.replace(R.id.fragmentContainerView, af);
        fragmentTransaction.commit();
        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(e->{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, af).commit();
        });
        flagBtn = findViewById(R.id.flagBtn);
        flagBtn.setOnClickListener(e->{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new CountryFragment()).commit();

        });
        bookmarkBtn = findViewById(R.id.bookmarkBtn);
        bookmarkBtn.setOnClickListener(e->{
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new BookmarkFragment()).commit();
        });

    }

}

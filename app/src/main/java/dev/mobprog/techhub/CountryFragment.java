package dev.mobprog.techhub;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.mobprog.techhub.models.Language;
import dev.mobprog.techhub.recycleAdapter.CountryAdapter;

public class CountryFragment extends Fragment {
    RecyclerView countryRv;
    ArrayList<Language> languages = new ArrayList<>();

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_country, container, false);
        // Inflate the layout for this fragment
        languages.add(new Language("English", "en"));
        languages.add(new Language("Arabic", "ar"));
        languages.add(new Language("German", "de"));
        languages.add(new Language("Espanol", "es"));
        languages.add(new Language("France", "fr"));
        languages.add(new Language("Italian", "it"));
        languages.add(new Language("Deutsch", "nl"));
        languages.add(new Language("Norwegian", "no"));
        languages.add(new Language("Portuguese", "pt"));
        languages.add(new Language("Russian", "ru"));
        languages.add(new Language("Swedish", "sv"));
        languages.add(new Language("Urdu", "ud"));
        languages.add(new Language("Chinese", "zh"));


        countryRv = rootView.findViewById(R.id.countryRv);
        countryRv.setLayoutManager(new LinearLayoutManager(getContext()));
        countryRv.setAdapter(new CountryAdapter(languages));
        return rootView;
    }
}
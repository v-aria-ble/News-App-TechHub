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
import java.util.Objects;

import dev.mobprog.techhub.recycleAdapter.BookmarkAdapter;

public class BookmarkFragment extends Fragment {
    RecyclerView bookmarkRV;

    HelperClass helperClass;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        helperClass = new HelperClass(this.getContext().getApplicationContext());
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        bookmarkRV = view.findViewById(R.id.bookmarkRV);

        bookmarkRV.setLayoutManager(new LinearLayoutManager(getContext()));
        BookmarkAdapter bookmarkAdapter = new BookmarkAdapter(helperClass.getArticles(Session.getInstance().getUser().getEmail()));
        bookmarkRV.setAdapter(bookmarkAdapter);
        return view;
    }
}
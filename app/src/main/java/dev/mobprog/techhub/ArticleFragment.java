package dev.mobprog.techhub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import dev.mobprog.techhub.models.Article;
import dev.mobprog.techhub.models.request.EverythingRequest;
import dev.mobprog.techhub.models.request.TopHeadlinesRequest;
import dev.mobprog.techhub.models.response.ArticleResponse;
import dev.mobprog.techhub.recycleAdapter.ArticleAdapter;
import dev.mobprog.techhub.recycleAdapter.ArticleEVAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends Fragment {
    private final String APIKEY = "";
    private RecyclerView recyclerView, recyclerView2;
    private ArticleAdapter articleAdapter;
    private ArticleEVAdapter articleEVAdapter;
    private ArrayList<Article> articleTHList = new ArrayList<>();
    private ArrayList<Article> articleEVList = new ArrayList<>();
    private Button generalBtn, aiBtn, searchBtn;
    private EditText searchTF;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        fetchArticles("tech");

        // Initialize RecyclerViews
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        articleAdapter = new ArticleAdapter(articleTHList);
        recyclerView.setAdapter(articleAdapter);
        aiBtn = rootView.findViewById(R.id.aiButton);
        aiBtn.setOnClickListener(e-> fetchArticles("AI"));
        generalBtn = rootView.findViewById(R.id.generalButton);
        searchTF = rootView.findViewById(R.id.searchTF);
        generalBtn.setOnClickListener(e->{
            fetchArticles("tech");
        });
        searchBtn = rootView.findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(e->{
            fetchArticles(String.valueOf(searchTF.getText()));
        });

        recyclerView2 = rootView.findViewById(R.id.recyclerViewEV);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        articleEVAdapter = new ArticleEVAdapter(articleEVList);
        recyclerView2.setAdapter(articleEVAdapter);

        return rootView;
    }

    private void fetchArticles(String query) {
        NewsApiClient newsApiClient = new NewsApiClient(APIKEY);

        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .q(query)
                        //.country("us")
                        .category("technology")
                        .language(Session.getInstance().getLanguage().getComputerLanguage())
                        .build(),

                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        if (response.getArticles() != null && !response.getArticles().isEmpty()) {
                            articleTHList.clear();
                            articleTHList.addAll(response.getArticles());
                            articleTHList.removeIf(article -> (article.getTitle().equals("[Removed]")));
                            if(articleTHList.isEmpty()){
                                articleTHList.add(new Article(null, "Something went wrong, please try again later...", "Sorry, currently no top headlines available!", "", "", "", "", ""));
                            }
                            articleAdapter.notifyDataSetChanged();

                        } else {
                            articleTHList.clear();
                            articleTHList.add(new Article(null, "Something went wrong, please try again later...", "Sorry, currently no top headlines available!", "", "", "", "", ""));
                            articleAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "No articles found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getContext(), "Failed to fetch articles: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        System.out.println(Session.getInstance().getLanguage());
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .language(Session.getInstance().getLanguage().getComputerLanguage())
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        if (response.getArticles() != null && !response.getArticles().isEmpty()) {
                            articleEVList.clear();
                            articleEVList.addAll(response.getArticles());
                            articleEVList.removeIf(article -> (article.getTitle().equals("[Removed]")));
                            articleEVAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "No articles found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Toast.makeText(getContext(), "Failed to fetch articles: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}
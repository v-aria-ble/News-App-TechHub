package dev.mobprog.techhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Locale;

import dev.mobprog.techhub.models.Article;
public class ArticleDetails extends AppCompatActivity {
    ShapeableImageView image; // Correct declaration for the ImageView
    ImageView bookmarkBookBtn;
    TextView articleName, articleAuthor, articleDescription, articlePublisher, articleContent;
    Article article;
    Button readBtn;
    HelperClass helperClass;
    TextToSpeech textToSpeech;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        helperClass = new HelperClass(getApplicationContext());
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {

                // if No error is found then only it will run
                if(i!=TextToSpeech.ERROR){
                    // To Choose language of speech
                    textToSpeech.setLanguage(Locale.UK);
                }
            }
        });
        // Retrieve Article object from Intent
        article = (Article) getIntent().getSerializableExtra("ARTICLE");

        // Find views
        image = findViewById(R.id.articleDetailImage);
        articleName = findViewById(R.id.articleDetailTitle);
        articleAuthor = findViewById(R.id.articleDetailAuthor);
        articleDescription = findViewById(R.id.ArticleDetailDescription);
        articlePublisher = findViewById(R.id.articleDetailPublisher);
        articleContent = findViewById(R.id.articleDetailContent);
        readBtn = findViewById(R.id.readBtn);
        readBtn.setOnClickListener(e->{
            String articleUrl = article.getUrl();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(articleUrl));
            startActivity(browserIntent);
        });
//        readBtn.setOnClickListener(e->{
//            textToSpeech.speak(articleDescription.getText().toString()+articleContent.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
//        });


        // Load the image using Glide
        if (article.getUrlToImage() != null && !article.getUrlToImage().isEmpty()) {
            Glide.with(this)
                    .load(article.getUrlToImage())
                    .placeholder(R.drawable.default_news) // Placeholder if the image URL is loading
                    .error(R.drawable.default_news) // Fallback if the image fails to load
                    .into(image); // Load directly into the ShapeableImageView
        } else {
            image.setImageResource(R.drawable.default_news); // Fallback if URL is null or empty
        }

        // Set article details
        articleName.setText(article.getTitle());
        articleAuthor.setText(article.getAuthor());
        String cleanDescription = "Description: "+Html.fromHtml(article.getDescription(), Html.FROM_HTML_MODE_LEGACY).toString();
        articleDescription.setText("    "+cleanDescription);
        articlePublisher.setText("Publisher: "+article.getSource().getName());
        String cleanContent = "Content: "+Html.fromHtml(article.getContent(), Html.FROM_HTML_MODE_LEGACY);
        articleContent.setText("    "+cleanContent);
        bookmarkBookBtn = findViewById(R.id.bookmarkBookBtn);
        bookmarkBookBtn.setOnClickListener(e->{
            boolean insertValid = helperClass.insertBookmark(
                    article.getSource().getId(),
                    article.getSource().getName(),
                    article.getAuthor(),
                    article.getTitle(),
                    article.getDescription(),
                    article.getUrl(),
                    article.getUrlToImage(),
                    article.getPublishedAt(),
                    article.getContent());
            if (!insertValid){
                helperClass.deleteBookmark(article.getUrl());
                Toast.makeText(this, "Article Unbookmarked!", Toast.LENGTH_SHORT ).show();
            } else {
                Toast.makeText(this, "Article Bookmarked!", Toast.LENGTH_SHORT ).show();
            }

        });

    }
}

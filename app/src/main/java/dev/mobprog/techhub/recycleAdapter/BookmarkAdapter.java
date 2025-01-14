package dev.mobprog.techhub.recycleAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import dev.mobprog.techhub.ArticleDetails;
import dev.mobprog.techhub.R;
import dev.mobprog.techhub.models.Article;
import dev.mobprog.techhub.models.BookmarkArticle;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {
    ArrayList<Article> bookmarkArticles = new ArrayList<>();

    public BookmarkAdapter(ArrayList<Article> bookmarkArticles) {
        this.bookmarkArticles = bookmarkArticles;
    }

    @NonNull
    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_everythingarticle,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarkAdapter.ViewHolder holder, int position) {
        Article article = bookmarkArticles.get(position);
        holder.article = article;
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        if (article.getUrlToImage() == null || article.getUrlToImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.default_news) // Your default image resource
                    .into(holder.image);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(article.getUrlToImage()) // Load the actual image URL
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return bookmarkArticles.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, author;
        ImageView image;
        Article article;

        public void setArticle(Article article) {
            this.article = article;
        }
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            author = itemView.findViewById(R.id.articleAuthor);
            image = itemView.findViewById(R.id.articleImage2);
            itemView.setOnClickListener(e->{
                Intent intent = new Intent(itemView.getContext(), ArticleDetails.class);
                intent.putExtra("ARTICLE", article);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}

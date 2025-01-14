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

import java.util.List;

import dev.mobprog.techhub.ArticleDetails;
import dev.mobprog.techhub.R;
import dev.mobprog.techhub.models.Article;

public class ArticleEVAdapter extends RecyclerView.Adapter<ArticleEVAdapter.ArticleViewHolder> {

    private List<Article> articleList;

    public ArticleEVAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_everythingarticle, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articleList.get(position);
        holder.title.setText(article.getTitle());
        holder.author.setText(article.getAuthor());
        holder.setArticle(article);
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
        return articleList.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title, author;
        ImageView image;
        Article article;

        public void setArticle(Article article) {
            this.article = article;
        }

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articleTitle);
            author = itemView.findViewById(R.id.articleAuthor);
            image = itemView.findViewById(R.id.articleImage2);
            itemView.setOnClickListener(e->{
                Intent intent = new Intent(itemView.getContext(), ArticleDetails.class);
                intent.putExtra("ARTICLE", article);
                itemView.getContext().startActivity(intent);
            });
            //image = itemView.findViewById(R.id.articleImage);
        }
    }
}

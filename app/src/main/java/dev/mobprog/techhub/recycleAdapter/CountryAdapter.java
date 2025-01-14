package dev.mobprog.techhub.recycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.mobprog.techhub.R;
import dev.mobprog.techhub.Session;
import dev.mobprog.techhub.models.Language;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    ArrayList<Language> languages = new ArrayList<>();

    public CountryAdapter(ArrayList<Language> languages) {
        this.languages = languages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_country, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.countryTv.setText(languages.get(position).getLanguage());
        holder.setCountry(languages.get(position));
    }

    @Override
    public int getItemCount() {
        return languages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Language language;
        TextView countryTv;

        public void setCountry(Language language) {
            this.language = language;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countryTv = itemView.findViewById(R.id.country);
            itemView.setOnClickListener(e->{
                Session.getInstance().setLanguage(language);
//                System.out.println(country);
            });

        }
    }
}

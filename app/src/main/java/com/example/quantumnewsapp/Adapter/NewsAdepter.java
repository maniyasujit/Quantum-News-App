package com.example.quantumnewsapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quantumnewsapp.Model.NewsModel;
import com.example.quantumnewsapp.databinding.NewsCardLayoutBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewsAdepter extends RecyclerView.Adapter<NewsAdepter.NewsViewHolder> {

    private ArrayList<NewsModel> newsList;
    private Context context;

    public NewsAdepter(ArrayList<NewsModel> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }

    public void setFilteredList(ArrayList<NewsModel> filteredNewsList) {
        this.newsList = filteredNewsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsCardLayoutBinding newsCardLayoutBinding =  NewsCardLayoutBinding
                .inflate(LayoutInflater.from(context), parent, false);
        return new NewsViewHolder(newsCardLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel newsModel = newsList.get(position);
        if (newsModel.getTitle() != null) {
            holder.binding.textNewsTitle.setText(newsModel.getTitle());
        } else {
            holder.binding.textNewsTime.setText("Title is't available!");
        }
        if (newsModel.getDescription() != null) {
            holder.binding.textDescription.setText(newsModel.getDescription());
        } else {
            holder.binding.textDescription.setText("Description is't available! Click here for more details.");
        }
        if (newsModel.getPublishedAt() != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date newsDate = format.parse(newsModel.getPublishedAt().toString());
                Date currentDate = new Date();
                if (newsDate != null) {
                    long different = currentDate.getTime() - newsDate.getTime();

                    long secondsInMilli = 1000;
                    long minutesInMilli = secondsInMilli * 60;
                    long hoursInMilli = minutesInMilli * 60;
                    long daysInMilli = hoursInMilli * 24;

                    long elapsedDays = different / daysInMilli;
                    different = different % daysInMilli;

                    long elapsedHours = different / hoursInMilli;

                    if (elapsedDays < 1) {
                        String time = elapsedHours + " hour ago";
                        holder.binding.textNewsTime.setText(time);
                    }   else {
                        holder.binding.textNewsTime.setText(newsModel.getPublishedAt());
                    }
                } else {
                    holder.binding.textNewsTime.setText(newsModel.getPublishedAt());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (newsModel.getSource() != null && newsModel.getSource().getName() != null) {
            holder.binding.textNewsChanelName.setText(newsModel.getSource().getName());
        }
        if (newsModel.getUrlToImage() != null) {
            Glide.with(context).load(Uri.parse(newsModel.getUrlToImage())).into(holder.binding.imageNews);
        }

        holder.binding.cardViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(newsModel.getUrl()));
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        NewsCardLayoutBinding binding;
        public NewsViewHolder(NewsCardLayoutBinding newsCardLayoutBinding) {
            super(newsCardLayoutBinding.getRoot());
            this.binding = newsCardLayoutBinding;
        }
    }
}

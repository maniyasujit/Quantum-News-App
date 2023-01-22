package com.example.quantumnewsapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quantumnewsapp.Adapter.NewsAdepter;
import com.example.quantumnewsapp.ApiUtilities.NewsApiUtilities;
import com.example.quantumnewsapp.Model.NewsModel;
import com.example.quantumnewsapp.Model.ParentNewsModel;
import com.example.quantumnewsapp.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String API_KEY = "63ca98993ecd4fab93af21f4065dab3c";
    private String country = "in";
    private int pageSize = 30;
    private int page = 1;
    private LinearLayoutManager manager;
    private ArrayList<NewsModel> newsList;
    private NewsAdepter newsAdepter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        newsList = new ArrayList<>();
        newsAdepter = new NewsAdepter(newsList, getApplicationContext());
        manager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerViewNews.setAdapter(newsAdepter);

        fetchNewsDataFromAPI();

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterList(s.toString());
            }
        });

        binding.editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
                return false;
            }
        });

        binding.imageLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Log out of SocialX?");
                builder.setCancelable(false);
                builder.setPositiveButton("Logout", (DialogInterface.OnClickListener) (dialog, which) -> {
                    signOut();
                });
                builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                    dialog.cancel();
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void filterList(String newsTitleForFilter) {
        ArrayList<NewsModel> filteredNewsList = new ArrayList<>();
        for (NewsModel newsModel : newsList) {
            if (newsModel.getTitle().toLowerCase().contains(newsTitleForFilter.toLowerCase())) {
                filteredNewsList.add(newsModel);
            }
        }
        if (filteredNewsList.size() == 0) {
            setViewVisibility(false);
        } else {
            newsAdepter.setFilteredList(filteredNewsList);
            setViewVisibility(true);
        }
    }

    private void fetchNewsDataFromAPI() {
        NewsApiUtilities.getApiInterface()
                .getNews(country, pageSize, API_KEY, page)
                .enqueue(new Callback<ParentNewsModel>() {
                    @Override
                    public void onResponse(@NonNull Call<ParentNewsModel> call, @NonNull Response<ParentNewsModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            newsList.addAll(response.body().getArticles());
                            newsAdepter.notifyDataSetChanged();
                            setViewVisibility(true);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ParentNewsModel> call, @NonNull Throwable t) {
                        setViewVisibility(false);
                        Toast.makeText(MainActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setViewVisibility(boolean dataFound) {
        if (dataFound) {
            binding.recyclerViewNews.setVisibility(View.VISIBLE);
            binding.textErrorMessage.setVisibility(View.GONE);
        } else {
            binding.textErrorMessage.setVisibility(View.VISIBLE);
            binding.recyclerViewNews.setVisibility(View.GONE);
        }
        binding.progressbar.setVisibility(View.GONE);
    }

    private void signOut() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            navigateToHomeActivity();
            return;
        }
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), googleSignInOptions);
        googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                navigateToHomeActivity();
            }
        });
    }

    private void navigateToHomeActivity() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

}
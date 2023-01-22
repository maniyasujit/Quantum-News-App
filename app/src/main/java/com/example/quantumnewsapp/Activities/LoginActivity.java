package com.example.quantumnewsapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.example.quantumnewsapp.Adapter.LogInViewPagerAdapter;
import com.example.quantumnewsapp.databinding.ActivityLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LogInViewPagerAdapter logInViewPagerAdapter;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getApplicationContext(), gso);

        binding.buttonTabLayout.addTab(binding.buttonTabLayout.newTab().setText("LOGIN"));
        binding.buttonTabLayout.addTab(binding.buttonTabLayout.newTab().setText("SIGN UP"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        logInViewPagerAdapter = new LogInViewPagerAdapter(fragmentManager, getLifecycle());
        binding.viewPagerMainContent.setAdapter(logInViewPagerAdapter);

        binding.buttonTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPagerMainContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPagerMainContent.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.buttonTabLayout.selectTab(binding.buttonTabLayout.getTabAt(position));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            navigateToHomeActivity();
            return;
        }

        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            navigateToHomeActivity();
        }
    }

    private void navigateToHomeActivity() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }
}
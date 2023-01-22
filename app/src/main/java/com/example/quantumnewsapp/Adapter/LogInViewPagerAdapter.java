package com.example.quantumnewsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quantumnewsapp.Fragment.LoginFragment;
import com.example.quantumnewsapp.Fragment.SignUpFragment;

public class LogInViewPagerAdapter extends FragmentStateAdapter {

    public LogInViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 1) {
            return new SignUpFragment();
        }
        return new LoginFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

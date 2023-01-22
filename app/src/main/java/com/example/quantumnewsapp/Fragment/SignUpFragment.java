package com.example.quantumnewsapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quantumnewsapp.Activities.MainActivity;
import com.example.quantumnewsapp.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {

    private FragmentSignUpBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(getLayoutInflater(), container, false);

        auth = FirebaseAuth.getInstance();

        binding.buttonMainRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidUserDetails()) {
                    auth.createUserWithEmailAndPassword(binding.editTextEmail.getText().toString(),
                            binding.editTextPassword.getText().toString())
                            .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    task.getResult();
                                    if(task.isSuccessful()) {
                                        FirebaseUser user = auth.getCurrentUser();
                                        navigateToHomeActivity();
                                    } else {
                                        showMessage("Authentication failed!");
                                    }
                                }
                            });
                }
            }
        });

        return binding.getRoot();
    }

    private void navigateToHomeActivity() {
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    private Boolean isValidUserDetails() {
        if (binding.editTextName.getText().toString().trim().isEmpty()) {
            showMessage(("Enter name!"));
            return false;
        } else if (binding.editTextEmail.getText().toString().trim().isEmpty()) {
            showMessage("Enter email!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.getText().toString().trim()).matches()) {
            showMessage("Enter correct email!");
            return false;
        } else if (binding.editTextContactNumber.getText().toString().trim().isEmpty() &&
                binding.editTextContactNumber.getText().toString().length() != 10) {
            showMessage("Enter correct mobile number!");
            return false;
        } else if (binding.editTextPassword.getText().toString().trim().isEmpty()) {
            showMessage("Enter password!");
            return false;
        } else if (!binding.checkBoxTermAndCondition.isChecked()) {
            showMessage("Please accept Term & Condition");
        }
        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
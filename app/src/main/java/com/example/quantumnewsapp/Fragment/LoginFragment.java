package com.example.quantumnewsapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.quantumnewsapp.Activities.MainActivity;
import com.example.quantumnewsapp.databinding.FragmentLoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    private GoogleSignInClient googleSignInClient;
    private FragmentLoginBinding binding;
    private FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(getLayoutInflater(), container, false);

        auth = FirebaseAuth.getInstance();
        createRequest();

        binding.ImageGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }
        });

        binding.buttonMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidUserDetails()) {
                    auth.signInWithEmailAndPassword(binding.editTextEmail.getText().toString(),
                            binding.editTextPassword.getText().toString())
                            .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
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

    private void createRequest() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(getContext(), googleSignInOptions);
    }

    private void googleSignIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        googleLoginLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> googleLoginLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (result.getData() != null) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        handleSignInResult(task);
                    }
                }
            }
    );

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            completedTask.getResult(ApiException.class);
            navigateToHomeActivity();
        } catch (ApiException e) {
            showMessage("Something went Wrong!");
        }
    }

    private void navigateToHomeActivity() {
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

    private Boolean isValidUserDetails() {
        if (binding.editTextEmail.getText().toString().trim().isEmpty()) {
            showMessage("Enter email!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.getText().toString().trim()).matches()) {
            showMessage("Enter correct email");
            return false;
        } else if (binding.editTextPassword.getText().toString().trim().isEmpty()) {
            showMessage("Enter password!");
            return false;
        } else {
            return true;
        }
    }

    private void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
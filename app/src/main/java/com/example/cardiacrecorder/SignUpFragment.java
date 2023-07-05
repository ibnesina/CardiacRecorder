package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }

    private TextView alreadyHaveAnAccount;
    private FrameLayout parentFrameLayout;
    private EditText email, fullName, password, conformPassword;
    private Button btnSignUp;

    private ImageButton btnClose;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private final String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    /**
     * Inflates the layout for this fragment and initializes the necessary views and variables.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     *      any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     *      but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *      from a previous saved state as given here.
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        alreadyHaveAnAccount = view.findViewById(R.id.signIn);

        btnClose = view.findViewById(R.id.btnClose);

        email = view.findViewById(R.id.email);
        fullName = view.findViewById(R.id.fullName);
        password = view.findViewById(R.id.password);
        conformPassword = view.findViewById(R.id.conformPassword);


        btnSignUp = view.findViewById(R.id.signUp);

        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        return view;
    }

    /**
     * Called when the view hierarchy is created for the fragment.
     * It initializes the click listeners, text change listeners, and other necessary operations.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set click listener for "Already have an account" text view
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });

        // Set text change listeners for input fields
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        conformPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo send data to firebase
                checkEmailAndPassword();
            }
        });



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    /**
     * Replaces the current fragment in the parent FrameLayout with the specified fragment.
     * @param fragment The Fragment object to replace the current fragment.
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    /**
     * Checks the input fields and enables or disables the sign-up button based on the input validity.
     * The sign-up button will be enabled if all the input fields have non-empty values and meet the required criteria.
     * Otherwise, the sign-up button will be disabled.
     */
    public void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())) {
            if(!TextUtils.isEmpty(fullName.getText())) {
                if(!TextUtils.isEmpty(password.getText()) && password.length()>=8) {
                    if(!TextUtils.isEmpty(conformPassword.getText())) {
                        btnSignUp.setEnabled(true);
                    }
                    else {
                        btnSignUp.setEnabled(false);
                    }
                }
                else {
                    btnSignUp.setEnabled(false);
                }
            }
            else {
                btnSignUp.setEnabled(false);
            }
        }
        else {
            btnSignUp.setEnabled(false);
        }
    }

    /**
     * Checks the email and password inputs and performs the sign-up process if they are valid.
     * If the email is valid and the password matches the confirm password, the user will be created
     * using FirebaseAuth.createUserWithEmailAndPassword and the user data will be saved in Firestore.
     * Upon successful sign-up, the user will be redirected to the main activity.
     * If there is an error during the sign-up process, an error message will be displayed.
     */
    private void checkEmailAndPassword() {
        if(email.getText().toString().matches(emailPattern)) {
            if(password.getText().toString().equals(conformPassword.getText().toString())) {

                btnSignUp.setEnabled(false);
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Map<Object, String> userData = new HashMap<>();
                                    userData.put("FullName", fullName.getText().toString());
                                    userData.put("Email", email.getText().toString());
                                    firebaseFirestore.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .set(userData)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Intent mainIntent = new Intent(getActivity(), MainActivity.class);
                                                        startActivity(mainIntent);
                                                        getActivity().finish();
                                                    }
                                                    else {
                                                        btnSignUp.setEnabled(true);
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                }
                                else {
                                    btnSignUp.setEnabled(true);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                conformPassword.setError("Password doesn't match");
            }
        }
        else {
            email.setError("Invalid email");
        }
    }

}
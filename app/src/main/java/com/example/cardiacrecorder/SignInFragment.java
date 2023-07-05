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

public class SignInFragment extends Fragment {

    public SignInFragment() {
        // Required empty public constructor
    }

    private TextView dontHaveAnAccount, forgotPassword;
    private FrameLayout parentFrameLayout;
    private EditText email, password;
    private ImageButton btnClose;
    private Button btnSignIn;

    private FirebaseAuth firebaseAuth;

    private final String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    /**
     * Inflates the layout for this fragment and initializes the necessary views and variables.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     *      any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     *      UI should be attached to.  The fragment should not add the view itself,
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
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        dontHaveAnAccount = view.findViewById(R.id.signUp);
        forgotPassword = view.findViewById(R.id.forgotPassword);
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);

        btnClose = view.findViewById(R.id.btnClose);
        btnSignIn = view.findViewById(R.id.signIn);

        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    /**
     * Called when the fragment's view has been created and is ready to be used.
     *
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set click listener for "Don't have an account?" text view
        dontHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });

        // Set click listener for "Forgot Password?" text view
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ForgetPasswordFragment());
            }
        });


        // Add text changed listener for email EditText
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

        // Add text changed listener for password EditText
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

        // Set click listener for "Sign In" button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });

        // Set click listener for close button
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
     * Replaces the current fragment in the specified FrameLayout with the provided Fragment.
     * @param fragment The Fragment to be displayed.
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    /**
     * Checks the input fields (email and password) and enables or disables the sign-in button accordingly.
     * If both email and password are not empty, the sign-in button is enabled.
     *      Otherwise, it is disabled.
     */
    private void checkInputs() {
        if(!TextUtils.isEmpty(email.getText())) {
            if(!TextUtils.isEmpty(password.getText())){
                btnSignIn.setEnabled(true);
            }
            else {
                btnSignIn.setEnabled(false);
            }
        }
        else {
            btnSignIn.setEnabled(false);
        }
    }

    /**
     * Validates the email and password inputs and attempts to sign in the user using Firebase Authentication.
     * If the email is in the correct format and the password length is at least 8 characters,
     *      the sign-in process is initiated.
     * If the sign-in is successful, the user is redirected to the MainActivity.
     * If the sign-in fails, an error message is displayed and the sign-in button is enabled again.
     */
    private void checkEmailAndPassword() {
        if(email.getText().toString().matches(emailPattern)) {
            if(password.length()>=8) {
                btnSignIn.setEnabled(false);
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                    getActivity().finish();
                                }
                                else {
                                    btnSignIn.setEnabled(true);
                                    String error = task.getException().getMessage();
                                    Toast.makeText(getActivity(), "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
            else {
                Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getActivity(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
        }
    }

}
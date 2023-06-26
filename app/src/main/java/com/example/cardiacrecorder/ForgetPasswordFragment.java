package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordFragment extends Fragment {

    EditText email;
    ImageButton btnClose;
    Button btnForgetPassword;
    TextView btnSignIn;
    FirebaseAuth firebaseAuth;
    FrameLayout parentFrameLayout;
    private final String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    public ForgetPasswordFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        email = view.findViewById(R.id.email);
        btnClose = view.findViewById(R.id.btnClose);
        btnForgetPassword = view.findViewById(R.id.btnForgetPassword);
        btnSignIn = view.findViewById(R.id.login);
        firebaseAuth = FirebaseAuth.getInstance();
        parentFrameLayout = getActivity().findViewById(R.id.registerFrameLayout);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString().trim();
                if(mail.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Recovery mail sent to your Email", Toast.LENGTH_SHORT).show();
                                setFragment(new SignInFragment());
                            }
                            else {
                                Toast.makeText(getActivity(), "Wrong Email", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignInFragment());
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(parentFrameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }
}
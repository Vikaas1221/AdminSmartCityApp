package com.example.administrationkarnal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    String[] deptType={"Select Your Department","Adminstration","Nagar Nigam","PWD","Agriculture","Fire Depatment","Electricity"};
    ArrayAdapter<String> arrayAdapter;
    Spinner Dept;
    FirebaseAuth mauth;
    FirebaseUser currentuser;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference deptRefrence=db.collection("Departments");

    EditText emailAdress;
    EditText password;
    TextView loginButton;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        Dept=findViewById(R.id.department);
        emailAdress=findViewById(R.id.emailId);
        password=findViewById(R.id.password);
        progressBar=findViewById(R.id.progress);
        loginButton=findViewById(R.id.login);
        mauth=FirebaseAuth.getInstance();

        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,deptType);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Dept.setAdapter(arrayAdapter);

        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String Email=emailAdress.getText().toString();
                String Password=password.getText().toString();
                String DeptType=Dept.getSelectedItem().toString();
                loginToAccount(Email,Password,DeptType);
            }
        });
    }
    public void loginToAccount(String Email,String Password,String DeptType)
    {
        if (Email.isEmpty())
        {
            emailAdress.setError("Required field");
            emailAdress.requestFocus();
            return;
        }
        if (Password.isEmpty())
        {
            password.setError("Required field");
            password.requestFocus();
            return;
        }
        if (DeptType.isEmpty()||DeptType.equals("Select Your Department"))
        {
            ((TextView)Dept.getSelectedView()).setError("Please select the Department");
            return;
        }
        checkForDeptartmentInFirebase(Email,DeptType,Password);


    }
    public void checkForDeptartmentInFirebase(final String Email, final String DeptType, final String Password)
    {
        progressBar.setVisibility(View.VISIBLE);
        deptRefrence.whereEqualTo("Email",Email).whereEqualTo("DeptName",DeptType)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {
                    @Override
                    public void onEvent(@Nullable final QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                    {
                        assert queryDocumentSnapshots != null;
                        if (queryDocumentSnapshots.isEmpty())
                        {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"Wrong information",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else
                        {
                            mauth.signInWithEmailAndPassword(Email,Password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                                                {
                                                    String name=documentSnapshot.getString("Officer");
                                                    JournalApi api=JournalApi.getInstance();
                                                    api.setEmail(Email);
                                                    api.setDepartment(DeptType);
                                                    api.setName(name);
                                                }


                                                startActivity(new Intent(MainActivity.this,HomeScreen.class));
                                                finish();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getApplicationContext(),""+e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                    }
                });
    }
}

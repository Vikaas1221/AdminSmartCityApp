package com.example.administrationkarnal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class splashScreen extends AppCompatActivity
{
    Button login;
    FirebaseAuth mauth;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference deptRefrence=db.collection("Departments");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        login=findViewById(R.id.admin);
        mauth=FirebaseAuth.getInstance();
//        Log.d("inSplash",""+mauth.getCurrentUser().getEmail());
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try {
                    Thread.sleep(500);
                    Intent intent=new Intent(splashScreen.this,MainActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
       // Log.d("inSplash",""+mauth.getCurrentUser().getEmail());

        super.onStart();

        if (mauth.getCurrentUser()!=null)
        {
            String currentuserEmailId=mauth.getCurrentUser().getEmail();
            deptRefrence.whereEqualTo("Email",currentuserEmailId)
                    .addSnapshotListener(new EventListener<QuerySnapshot>()
                    {
                        @Override
                        public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
                        {
                            assert queryDocumentSnapshots != null;
                            for (QueryDocumentSnapshot queryDocumentSnapshot:queryDocumentSnapshots)
                            {
                                String name=queryDocumentSnapshot.getString("Officer");
                                String deptname=queryDocumentSnapshot.getString("DeptName");
                                String Email=queryDocumentSnapshot.getString("Email");
                                JournalApi api=JournalApi.getInstance();
                                api.setName(name);
                                api.setEmail(Email);
                                api.setDepartment(deptname);
                                startActivity(new Intent(splashScreen.this,HomeScreen.class));
                                finish();

                            }
                        }
                    });

            Log.d("current_user",""+mauth.getCurrentUser().getEmail());

        }

    }
}

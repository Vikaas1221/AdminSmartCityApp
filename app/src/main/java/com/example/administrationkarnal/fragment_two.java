package com.example.administrationkarnal;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class fragment_two extends Fragment
{
    RecyclerView recyclerView;
    ArrayList<new_complaint_Model> arrayList;
    RecyclerView.Adapter adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference reference = db.collection("Complaints");
    FirebaseAuth mauth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.fragment_two_layout, container, false);
        recyclerView = view.findViewById(R.id.ResolvedComplaintsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        mauth = FirebaseAuth.getInstance();
        JournalApi api=JournalApi.getInstance();
        String dept=api.getDepartment();






        reference.whereEqualTo("Department", dept)
                .addSnapshotListener(new EventListener<QuerySnapshot>()
                {

                    @Override
                    public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e)
                    {
                        arrayList.clear();
                        assert queryDocumentSnapshots != null;
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots)
                        {
                            if (queryDocumentSnapshot.exists()) {
                                String Description = queryDocumentSnapshot.getString("Description");
                                String status = queryDocumentSnapshot.getString("Status");
                                String Date = queryDocumentSnapshot.getString("Date");
                                String id = queryDocumentSnapshot.getString("ComplaintId");
                                String address=queryDocumentSnapshot.getString("Address");
                                String name=queryDocumentSnapshot.getString("Name");
                                String contact=queryDocumentSnapshot.getString("Mobile");
                                new_complaint_Model obj = new new_complaint_Model();
                                obj.setAddress(address);
                                obj.setDescription(Description);
                                obj.setStatus(status);
                                obj.setDate(Date);
                                obj.setComplaintId(id);
                                obj.setName(name);
                                obj.setMobile(contact);
                                assert status != null;
                                if (!status.equals("Pending"))
                                {
                                    arrayList.add(obj);
                                }

                            }
                            adapter = new ResolvedcomplaintAdapter(getContext(), arrayList);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        return view;
    }
}

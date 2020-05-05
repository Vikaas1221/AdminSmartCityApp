package com.example.administrationkarnal;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class complaintAdapter extends RecyclerView.Adapter<complaintAdapter.ViewHolder>
{
    ArrayList<new_complaint_Model> arrayList;
    Context context;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("Complaints");
    public complaintAdapter(Context context, ArrayList<new_complaint_Model> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public complaintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout_newcomplaints,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull complaintAdapter.ViewHolder holder, int position)
    {
        final new_complaint_Model model=arrayList.get(position);
        holder.desc.setText(model.getDescription());
        holder.status.setText(model.getStatus());
        holder.complaint_id.setText(model.getComplaintId());
        holder.date.setText(model.getDate());
        holder.address.setText(model.getAddress());
        holder.addAction.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showAddActionDialog(model.getComplaintId());
            }
        });

    }
    public void showAddActionDialog(final String complaintId)
    {
        View view=LayoutInflater.from(context)
                .inflate(R.layout.add_action_dialog,null,false);
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(view);
        final AlertDialog dialog=builder.create();
        dialog.show();
        final EditText status=view.findViewById(R.id.action_taken);
        TextView action_button=view.findViewById(R.id.ActionComplaint);
        action_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (status.getText().toString().isEmpty())
                {
                    status.setError("required field");
                    status.requestFocus();
                    return;
                }
                updateStatusToFirebase(complaintId,status.getText().toString());
                dialog.dismiss();


            }
        });

    }
    public void updateStatusToFirebase(String complaintId, final String status)
    {
        reference.whereEqualTo("ComplaintId",complaintId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e)
                    {
                        for (QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                        {
                            DocumentReference reference=documentSnapshot.getReference();
                            reference.update("Status",status)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid)
                                        {
                                            Toast.makeText(context,"Updated sucessfully",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e)
                                        {
                                            Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });

    }

    @Override
    public int getItemCount()
    {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView desc,status,date,complaint_id,address;
        Button addAction,close_complaint;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            desc=itemView.findViewById(R.id.description_type);
            status=itemView.findViewById(R.id.action_type);
            date=itemView.findViewById(R.id.date_type);
            complaint_id=itemView.findViewById(R.id.requestID);
            address=itemView.findViewById(R.id.address_type);
            addAction=itemView.findViewById(R.id.add_action);
            close_complaint=itemView.findViewById(R.id.closeComplaint);


        }
    }
}

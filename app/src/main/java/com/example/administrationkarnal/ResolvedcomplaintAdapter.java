package com.example.administrationkarnal;

import android.content.Context;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
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

public class ResolvedcomplaintAdapter extends RecyclerView.Adapter<ResolvedcomplaintAdapter.ViewHolder>
{
    ArrayList<new_complaint_Model> arrayList;
    Context context;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    CollectionReference reference=db.collection("Complaints");
    public ResolvedcomplaintAdapter(Context context, ArrayList<new_complaint_Model> arrayList)
    {
        this.context=context;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public ResolvedcomplaintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_layout_resolvedcomplaints,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ResolvedcomplaintAdapter.ViewHolder holder, int position)
    {
        final new_complaint_Model model=arrayList.get(position);
        holder.desc.setText(model.getDescription());
        holder.status.setText(model.getStatus());
        holder.complaint_id.setText(model.getComplaintId());
        holder.date.setText(model.getDate());
        holder.address.setText(model.getAddress());
        holder.mobile.setText(model.getMobile());
        holder.name.setText(model.getName());
        holder.expandbuton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (holder.layout.getVisibility()==View.GONE)
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    {
                        TransitionManager.beginDelayedTransition(holder.cardView,new AutoTransition());
                        holder.layout.setVisibility(View.VISIBLE);
                        holder.expandbuton.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    }
                }
                else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(holder.cardView, new AutoTransition());
                        holder.layout.setVisibility(View.GONE);
                        holder.expandbuton.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }

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
        TextView desc,status,date,complaint_id,address,name,mobile;
        CardView cardView;
        LinearLayout layout;
        ImageView expandbuton;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            desc=itemView.findViewById(R.id.description_type);
            status=itemView.findViewById(R.id.action_type);
            date=itemView.findViewById(R.id.date_type);
            complaint_id=itemView.findViewById(R.id.requestID);
            address=itemView.findViewById(R.id.address_type);
            name=itemView.findViewById(R.id.name_type);
            mobile=itemView.findViewById(R.id.mobile_type);
            cardView=itemView.findViewById(R.id.cardview);
            layout=itemView.findViewById(R.id.layoutt);
            expandbuton=itemView.findViewById(R.id.arrow_down_complaint);

        }
    }
}

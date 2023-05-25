package com.example.bftopsis;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bftopsis.Alternatif;
import com.example.bftopsis.R;

import java.util.ArrayList;

public class ListAlternatifAdapter extends RecyclerView.Adapter<ListAlternatifAdapter.ListViewHolder> {
    private ArrayList<Alternatif> listAlternatif;

    public ListAlternatifAdapter(ArrayList<Alternatif> list) {
        this.listAlternatif = list;
    }

    private OnItemClickCallback onItemClickCallback;
    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rank, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        Alternatif alternatif = listAlternatif.get(position);
        holder.tvName.setText(alternatif.getNamaAlternatif());
        holder.tvSkor.setText(String.valueOf(alternatif.getSkorAlternatif()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickCallback.onItemClicked(listAlternatif.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAlternatif.size();
    }

    public interface OnItemClickCallback {
        void onItemClicked(Alternatif data);
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvSkor;
        ListViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_nama_alternatif);
            tvSkor = itemView.findViewById(R.id.tv_skor_alternatif);
        }
    }

}

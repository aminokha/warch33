package com.example.myapplication2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.List;

public class HorofAdapter extends RecyclerView.Adapter<HorofAdapter.HorofViewHolder> {

    private final List<String> horofList;
    private final OnHorofClickListener listener;
    private int selectedPosition = -1;

    public interface OnHorofClickListener {
        void onHorofClick(String harf, int position);
    }

    public HorofAdapter(List<String> horofList, OnHorofClickListener listener) {
        this.horofList = horofList;
        this.listener = listener;
    }

    public void setSelectedPosition(int position) {
        int previousPosition = selectedPosition;
        selectedPosition = position;
        notifyItemChanged(previousPosition);
        notifyItemChanged(selectedPosition);
    }

    @NonNull
    @Override
    public HorofViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horof, parent, false);
        return new HorofViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorofViewHolder holder, int position) {
        String harf = horofList.get(position);
        holder.tvHorof.setText(harf);

        if (position == selectedPosition) {
            holder.cardHorof.setStrokeColor(holder.itemView.getContext().getResources().getColor(R.color.primary));
            holder.cardHorof.setStrokeWidth(4);
            holder.tvHorof.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.primary));
        } else {
            holder.cardHorof.setStrokeColor(holder.itemView.getContext().getResources().getColor(R.color.glass_stroke));
            holder.cardHorof.setStrokeWidth(2);
            holder.tvHorof.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onHorofClick(harf, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return horofList.size();
    }

    static class HorofViewHolder extends RecyclerView.ViewHolder {
        TextView tvHorof;
        MaterialCardView cardHorof;

        public HorofViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHorof = itemView.findViewById(R.id.tvHorof);
            cardHorof = itemView.findViewById(R.id.cardHorof);
        }
    }
}

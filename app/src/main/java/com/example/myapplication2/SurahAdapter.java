package com.example.myapplication2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SurahAdapter extends RecyclerView.Adapter<SurahAdapter.SurahViewHolder> {

    private List<Surah> surahList;
    private OnSurahClickListener listener;

    public interface OnSurahClickListener {
        void onSurahClick(Surah surah, View view);
    }

    public SurahAdapter(List<Surah> surahList, OnSurahClickListener listener) {
        this.surahList = surahList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SurahViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surah, parent, false);
        return new SurahViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurahViewHolder holder, int position) {
        Surah surah = surahList.get(position);
        holder.surahTitle.setText(surah.getName());
        holder.surahImage.setImageResource(surah.getImageResId());

        holder.itemView.setOnClickListener(v -> {
            animateCard(v, () -> {
                if (listener != null) {
                    listener.onSurahClick(surah, v);
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return surahList.size();
    }

    private void animateCard(View view, Runnable onEnd) {
        view.animate()
                .scaleX(1.2f).scaleY(1.2f)
                .setDuration(200)
                .setInterpolator(new LinearInterpolator()).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animator) {
                        view.setScaleX(1f);
                        view.setScaleY(1f);
                        if (onEnd != null) {
                            onEnd.run();
                        }
                    }
                });
    }

    static class SurahViewHolder extends RecyclerView.ViewHolder {
        ImageView surahImage;
        TextView surahTitle;

        public SurahViewHolder(@NonNull View itemView) {
            super(itemView);
            surahImage = itemView.findViewById(R.id.surah_image);
            surahTitle = itemView.findViewById(R.id.surah_title);
        }
    }
}

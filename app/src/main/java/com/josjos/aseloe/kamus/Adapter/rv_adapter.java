package com.josjos.aseloe.kamus.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.josjos.aseloe.kamus.Model.KamusModel;
import com.josjos.aseloe.kamus.R;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;

public class rv_adapter extends RecyclerView.Adapter<rv_adapter.KamusHolder> {
    @Setter @Getter private ArrayList<KamusModel> mData = new ArrayList<>();


    @Override
    public KamusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kata, parent, false);
        return new KamusHolder(view);
    }

    public void replace(ArrayList<KamusModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(KamusHolder holder, int position) {
        holder.tv_kata.setText(mData.get(position).getKata());
        holder.tv_kunci.setText(mData.get(position).getKunci());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class KamusHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_item_kata) TextView tv_kata;
        @BindView(R.id.tv_item_kunci) TextView tv_kunci;

        public KamusHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}

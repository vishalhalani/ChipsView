package com.vishal.chipsview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChipsAdapter extends RecyclerView.Adapter<ChipsAdapter.ChipViewHolder> {

    private ArrayList<String> itemsList;


    private Context context;
    private OnChipsRemovedListener onChipsRemovedListener;
    private int requestCode;


    public ChipsAdapter(Context context, ArrayList<String> itemsList, OnChipsRemovedListener onChipsRemovedListener, int requestcode) {
        super();
        this.context = context;
        this.onChipsRemovedListener = onChipsRemovedListener;
        this.requestCode = requestcode;
        this.itemsList = itemsList;

    }


    @NonNull
    @Override
    public ChipViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_chip_layout, viewGroup, false);
        return new ChipViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ChipViewHolder myViewHolder, int position) {
        myViewHolder.tv.setText(itemsList.get(myViewHolder.getAdapterPosition()));
        // Set a click listener for item remove button
        myViewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(myViewHolder.getAdapterPosition());
            }
        });

    }


    public ArrayList<String> getItemsList() {
        return itemsList;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void deleteItem(int adapterPosition) {

        this.itemsList.remove(adapterPosition);
        notifyDataSetChanged();
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, this.itemsList.size());

        ArrayList<String> updatedItem = (ArrayList<String>) itemsList.clone();
        onChipsRemovedListener.onChipsRemoved(updatedItem, requestCode);


    }

    public class ChipViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;
        private ImageView img;

        public ChipViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_chip_clear_img);
            tv = itemView.findViewById(R.id.item_chip_text_tv);

//            img.setOnClickListener(view -> deleteItem(getAdapterPosition()));
        }
    }
}
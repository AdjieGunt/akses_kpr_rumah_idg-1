package com.rumahkpr.akses.aksesrumahkpr.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rumahkpr.akses.aksesrumahkpr.R;
import com.rumahkpr.akses.aksesrumahkpr.activity.DetailProductActivity;
import com.rumahkpr.akses.aksesrumahkpr.model.Rumah;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by JEMMY CALAK on 2/25/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Rumah> mdata;
    private int codeActivity;
    private int codeLove = -1;

    public RecyclerViewAdapter(Context context, Activity activity, ArrayList<Rumah> data, int code){
        this.mdata = data;
        this.context = context;
        this.activity = activity;
        this.codeActivity = code;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final Rumah mrumah = mdata.get(position);

        holder.nama.setText(mrumah.getKlaster());
        holder.location.setText(mrumah.getAlamat());
        holder.developer.setText("PT. Developer Group");
        Picasso.with(context).load(mrumah.getImg1()).into(holder.imageHome);

        holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("rumah", mrumah);

                Intent i = new Intent(activity, DetailProductActivity.class);
                i.putExtras(bundle);
                activity.startActivity(i);
                activity.overridePendingTransition(R.anim.activity_slide_in_up, R.anim.activity_slide_out_up);
            }
        });
        holder.lov.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.lov.setImageResource(R.drawable.ic_wishlist);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageHome, lov;
        TextView nama, location, developer;
        RelativeLayout layoutProduct;
        LinearLayout layoutItem;
        public MyHolder(View itemView) {
            super(itemView);

            layoutProduct = (RelativeLayout)itemView.findViewById(R.id.layoutProduct);
            imageHome = (ImageView)itemView.findViewById(R.id.imageHome);
            lov = (ImageView)itemView.findViewById(R.id.love);
            nama = (TextView)itemView.findViewById(R.id.nama);
            location = (TextView)itemView.findViewById(R.id.location);
            developer = (TextView)itemView.findViewById(R.id.developer);
            layoutItem = (LinearLayout)itemView.findViewById(R.id.layoutItemRumah);

            if(codeActivity == 1){
                layoutItem.requestLayout();
                layoutItem.getLayoutParams().width = 300;
            }
        }
    }
}

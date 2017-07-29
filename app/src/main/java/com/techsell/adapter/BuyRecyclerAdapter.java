package com.techsell.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.techsell.R;
import com.techsell.dto.BuyDto;

import java.util.List;

/**
 * Created by Ankit on 12/09/16.
 */
public class BuyRecyclerAdapter extends RecyclerView.Adapter<BuyRecyclerAdapter.BuyViewHolder> {
    private Context context;
    List<BuyDto> data;
    ImageView postImage;

    public BuyRecyclerAdapter(Context context, List<BuyDto> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public BuyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.add_row, parent,false);
        BuyViewHolder holder = new BuyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BuyViewHolder holder, int position) {
        BuyDto current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.desc.setText(current.getDescription());
        String image = current.getImage();
        System.out.print("Image123" + image);
        Picasso.with(context).load(current.getImage()).into(postImage);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class BuyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, desc;
        Button btnWishlist, btnBuy;

        public BuyViewHolder(View itemView) {
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.postimage);
            title = (TextView) itemView.findViewById(R.id.posttitle);
            desc = (TextView) itemView.findViewById(R.id.postdesc);
            btnBuy = (Button) itemView.findViewById(R.id.btnbuy);
            btnWishlist = (Button) itemView.findViewById(R.id.btnwishlist);
            btnWishlist.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btnwishlist)
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();

        }
    }
}

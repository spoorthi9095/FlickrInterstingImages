package com.spoorthi.assignmentflickr.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.spoorthi.assignmentflickr.R;
import com.spoorthi.assignmentflickr.data_classes.ImageData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Spoorthi on 1/13/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder>
{
    private ArrayList<ImageData> listItems;
    private Context context;

    ArrayList<ImageData> duplicate = null;

    public RecyclerAdapter(ArrayList<ImageData> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater
                .inflate(R.layout.image_card,null);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position)
    {
        ImageData itemDataModel = listItems.get(position);

        //To select random image from the list of images
        Random r = new Random();

        Picasso.with(context).load(itemDataModel.getImageUrl()).placeholder(R.drawable.loading_place_holder)
                .into(holder.projectImg);
        holder.descTV.setText(itemDataModel.getImageTitle());

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView descTV;
        public ImageView projectImg;

        public ItemViewHolder(View itemView) {
            super(itemView);

            projectImg = (ImageView)itemView.findViewById(R.id.projectImg);
            descTV = (TextView)itemView.findViewById(R.id.textViewDesc);

        }
    }

    //To clear the adapter values
    public void clear() {
        listItems.clear();
        notifyDataSetChanged();
    }

    //To filter the array items,as user enters text for search
    public Filter getFilter()
    {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint)
            {
                final FilterResults oReturn = new FilterResults();
                List<ImageData> results = new ArrayList<ImageData>();
                if (duplicate == null)
                    duplicate = listItems;
                if (constraint != null)
                {
                    if (duplicate != null & duplicate.size() > 0) {
                        for ( final ImageData g :duplicate) {
                            if (g.getImageTitle().toLowerCase().contains(constraint.toString()))results.add(g);
                        }
                    }
                    else
                    {
                        results = listItems;
                    }
                    oReturn.values = results;

                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listItems = (ArrayList<ImageData>) results.values;
                notifyDataSetChanged();
                if (listItems.size()==0)//If there are no results found for search
                {
                    Toast.makeText(context,R.string.no_results,
                            Toast.LENGTH_SHORT).show();
                }

            }
        };
    }

}

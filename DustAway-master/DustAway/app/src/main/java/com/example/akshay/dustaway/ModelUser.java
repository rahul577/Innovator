package com.example.akshay.dustaway;


import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;


import java.util.List;

public class ModelUser extends AbstractItem<ModelUser, ModelUser.ViewHolder> {
    private String cash;
    private String username;
    public ModelUser(){


    }
    public ModelUser(String cash, String username){
        this.cash = cash;
        this.username = username;
    }
    public void setCash(String cash) {
        this.cash = cash;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCash() {

        return cash;
    }

    public String getUsername() {
        return username;
    }

    @NonNull
    @Override
    public ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.view_holder;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.view_holder_layout;
    }

    protected static class ViewHolder extends FastAdapter.ViewHolder<ModelUser> {

        TextView name;
        TextView points;
        ImageView image;

        public ViewHolder(View view) {
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            points = (TextView)view.findViewById(R.id.points);
            points = (TextView)view.findViewById(R.id.points);
        }

        @Override
        public void bindView(ModelUser item, List<Object> payloads) {
            name.setText(item.getUsername());

            points.setText(item.getCash());
        }

        @Override
        public void unbindView(ModelUser item) {
            name.setText(null);
            points.setText(null);
        }

    }
}

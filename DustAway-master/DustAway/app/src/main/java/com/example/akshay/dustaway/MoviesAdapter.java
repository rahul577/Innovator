package com.example.akshay.dustaway;

import android.graphics.ColorSpace;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<ModelUser> UserList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
           // genre = (TextView) view.findViewById(R.id.genre);
           // year = (TextView) view.findViewById(R.id.year);
        }
    }


    public MoviesAdapter(List<ModelUser> UserList) {
        this.UserList = UserList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ModelUser user = UserList.get(position);
       // holder.title.setText(user.getTitle());
        //holder.genre.setText(movie.getGenre());
        //holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return UserList.size();
    }
}
package com.kaam.ka.kapatia.instastoriesproject;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {

    ArrayList<Story> stories;

    public StoriesAdapter(ArrayList<Story> stories){
        this.stories = stories;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.story_list_item,viewGroup,false);
        return new StoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder storyViewHolder, int i) {
        Story story = stories.get(i);
        storyViewHolder.bind(story);
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class StoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView backgroundThumbnail;
        TextView username;
        CircularImageView circularThumbnail;

        public StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            backgroundThumbnail = (ImageView) itemView.findViewById(R.id.backgroundThumbnail);
            username = (TextView) itemView.findViewById(R.id.username);
            circularThumbnail = (CircularImageView) itemView.findViewById(R.id.circularThumbnail);
            itemView.setOnClickListener(this);
        }

        public void bind (Story story){
            username.setText(story.username);
            Picasso.get().load(story.profileThumbnail).resize(500, 900).transform(new RoundedTransformation(20, 0)).into(backgroundThumbnail);
            Picasso.get().load(story.profileThumbnail).into(circularThumbnail);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Story story = stories.get(position);
            Intent intent = new Intent(view.getContext(), VideoPlayNew.class);
            intent.putExtra("Story",story);
            view.getContext().startActivity(intent);
        }
    }


}

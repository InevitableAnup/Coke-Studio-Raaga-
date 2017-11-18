package in.handmademess.cokestudioraaga.Adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer.ExoPlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.handmademess.cokestudioraaga.MediaPlayerActivity;
import in.handmademess.cokestudioraaga.Model.SongsInfo;
import in.handmademess.cokestudioraaga.R;

/**
 * Created by Anup on 18-11-2017.
 */

public class CokeStudioAdapter extends RecyclerView.Adapter<CokeStudioAdapter.ViewHolder> {

    public Context mContext;
    public ArrayList<SongsInfo> mSongsInfoArrayList;
    ArrayList<String> posterLocation = new ArrayList<>();
    String posterPath;
    ImageView posterImg;
    MediaPlayer mediaPlayer;

    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;
    private ExoPlayer exoPlayer;

    public CokeStudioAdapter(Context mContext, ArrayList<SongsInfo> songsInfoArrayList) {
        this.mContext = mContext;
        this.mSongsInfoArrayList = songsInfoArrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_view, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int listPosition) {

        final SongsInfo item = mSongsInfoArrayList.get(listPosition);
        TextView songName = holder.songName;
        TextView artistName = holder.artistName;
        posterImg = holder.poster;

        songName.setText(mSongsInfoArrayList.get(listPosition).getSong());
        artistName.setText(mSongsInfoArrayList.get(listPosition).getArtists());


        Picasso.with(mContext)
                .load(mSongsInfoArrayList.get(listPosition).getCover_image())
                .placeholder(R.drawable.empty_gallery)
                .error(R.drawable.cstudio)
                .into(posterImg);


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = item.getUrl();
                String song = item.getSong();
                String artist = item.getArtists();

                Intent mediaPlayerIntent = new Intent(mContext, MediaPlayerActivity.class);
                mediaPlayerIntent.putExtra("url", url);
                mediaPlayerIntent.putExtra("song", song);
                mediaPlayerIntent.putExtra("artist", artist);
                mContext.startActivity(mediaPlayerIntent);

            }
        });


    }

    public void filter(String text)
    {
            text = text.toLowerCase();

    }

    @Override
    public int getItemCount() {
        return mSongsInfoArrayList == null ? 0 : mSongsInfoArrayList.size();
    }

    // Static inner class to initialize the views of rows
    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView songName, artistName;
        public ImageView poster;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            songName = (TextView) itemView.findViewById(R.id.list_item_name);
            artistName = (TextView) itemView.findViewById(R.id.list_item_artist);
            poster = (ImageView) itemView.findViewById(R.id.list_item_song);
        }

    }


}

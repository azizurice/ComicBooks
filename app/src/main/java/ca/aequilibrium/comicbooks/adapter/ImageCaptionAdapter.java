package ca.aequilibrium.comicbooks.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.aequilibrium.comicbooks.R;
import ca.aequilibrium.comicbooks.model.ComicCharacter;

/**
 * Created by Azizur on 19/10/2016.
 */

public class ImageCaptionAdapter extends RecyclerView.Adapter<ImageCaptionAdapter.ViewHolder> {

    private ArrayList<ComicCharacter> mComicCharacters = new ArrayList<>();
    private Listener listener;

    public static interface Listener {
        public void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        public ViewHolder(CardView v) {
            super(v);
            cardView=v;
        }
    }


    public ImageCaptionAdapter(ArrayList<ComicCharacter> cCharacters){
        this.mComicCharacters=cCharacters;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public ImageCaptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_image_caption, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView)cardView.findViewById(R.id.character_caption_image);
        TextView textView = (TextView)cardView.findViewById(R.id.character_caption);

        imageView.setImageBitmap(mComicCharacters.get(position).getThumbnail());
        imageView.setContentDescription(mComicCharacters.get(position).getTitle());

        textView.setText(mComicCharacters.get(position).getTitle());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mComicCharacters.size();
    }
}

package com.apppartner.androidprogrammertest.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.Utils.CircleTransform;
import com.apppartner.androidprogrammertest.Utils.Font;
import com.apppartner.androidprogrammertest.models.ChatData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */


public class ChatsArrayAdapter extends RecyclerView.Adapter<ChatsArrayAdapter.ViewHolder>{
    private Context mContext;
    private OnItemClickListener mItemClickListener;
    private ArrayList<ChatData> chatDataArrayList;


    public ChatsArrayAdapter(Context context) {
        this.mContext = context;
    }

    public void setChatDataArrayList(ArrayList<ChatData> chatDataArrayList) {
        this.chatDataArrayList = chatDataArrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView username;
        private TextView messageTextView;
        private ImageView usrImg;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.usernameTextView);
            username.setTypeface(Font.setFont(mContext, Font.FontType.Machinato.toString()));


            messageTextView = (TextView) itemView.findViewById(R.id.messageTextView);
            messageTextView.setTypeface(Font.setFont(mContext, Font.FontType.MachinatoLight.toString()));

            usrImg = (ImageView) itemView.findViewById(R.id.chatUsrImgID);
            //placeHolder.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(itemView, chatDataArrayList.get(getAdapterPosition()));
            }
        }

    }


    public interface OnItemClickListener {
        void onItemClick(View view, ChatData chatData);

    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }



    @Override
    public int getItemCount() {
        if (chatDataArrayList == null)
            return 0;
        return chatDataArrayList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_chat, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ChatData chatData = chatDataArrayList.get(position);
        holder.username.setText(chatData.username);
        holder.messageTextView.setText(chatData.message);

        Picasso.with(mContext)
                .load(chatData.avatarURL)
                .transform(new CircleTransform())
                .fit()
                .into(holder.usrImg);

    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}

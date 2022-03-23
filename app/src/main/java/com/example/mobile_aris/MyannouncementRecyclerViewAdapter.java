package com.example.mobile_aris;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile_aris.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.mobile_aris.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyannouncementRecyclerViewAdapter extends RecyclerView.Adapter<MyannouncementRecyclerViewAdapter.ViewHolder> {

    ArrayList<announcementModel> mValues = new ArrayList<announcementModel>();

    public MyannouncementRecyclerViewAdapter(ArrayList<announcementModel> items) {
        mValues = items;
    }

    public ArrayList<announcementModel> getmValues() {
        return mValues;
    }

    public void setmValues(ArrayList<announcementModel> mValues) {
        this.mValues = mValues;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.txtTitle.setText(mValues.get(position).getTitle());
        holder.txtDate.setText(mValues.get(position).getDate());
        holder.txtDesc.setText(mValues.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public announcementModel mItem;
        TextView txtTitle, txtDesc, txtDate;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            txtTitle = binding.txtTitle;
            txtDate = binding.Posted;
            txtDesc = binding.Content;
        }

    }
}
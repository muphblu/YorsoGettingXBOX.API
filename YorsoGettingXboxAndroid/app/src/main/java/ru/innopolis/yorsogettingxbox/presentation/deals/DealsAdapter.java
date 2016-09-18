package ru.innopolis.yorsogettingxbox.presentation.deals;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.innopolis.yorsogettingxbox.R;
import ru.innopolis.yorsogettingxbox.models.Deal;

/**
 * Created by enspa on 18.09.2016.
 */
public class DealsAdapter extends RecyclerView.Adapter<DealsAdapter.DealsViewHolder> {

    private List<Deal> deals;
    private final LayoutInflater layoutInflater;
    private final OnItemClickListener listener;


    public DealsAdapter(Activity activity, OnItemClickListener listener) {
        this(activity, listener, new ArrayList<>());
    }

    public DealsAdapter(Activity activity, OnItemClickListener listener,  List<Deal> deals) {
        this.deals = deals;
        this.layoutInflater = activity.getLayoutInflater();
        this.listener = listener;
    }

    @Override
    public DealsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = layoutInflater.inflate(R.layout.item_deals_layout, parent, false);
        return new DealsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DealsViewHolder holder, int position) {
        Deal deal = deals.get(position);
        holder.viewDealsName.setText(deal.getTitle());
        holder.viewDealsDescription.setText(deal.getDescription());
        holder.rootView.setOnClickListener(view -> listener.onItemClick(deals.get(position)));
    }

    @Override
    public int getItemCount() {
        return deals.size();
    }

    public void setDeals(List<Deal> newDeals) {
        deals = newDeals;
        notifyDataSetChanged();
        if (deals.size() == 0) {

        }
    }

    public void addDeal(Deal deal){
        deals.add(deal);
        notifyItemInserted(deals.size() - 1);
    }

    public interface OnItemClickListener {
        void onItemClick(Deal item);
    }

    class DealsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.view_deals_name)
        TextView viewDealsName;
        @BindView(R.id.view_deals_description)
        TextView viewDealsDescription;
        @BindView(R.id.view_deal)
        View rootView;

        public DealsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

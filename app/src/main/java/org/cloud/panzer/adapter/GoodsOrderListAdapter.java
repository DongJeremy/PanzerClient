package org.cloud.panzer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import org.cloud.core.base.BaseViewHolder;
import org.cloud.core.utils.ImageUtils;
import org.cloud.panzer.R;
import org.cloud.panzer.bean.OrderBean;

import java.util.ArrayList;

import butterknife.BindView;

public class GoodsOrderListAdapter extends RecyclerView.Adapter<GoodsOrderListAdapter.ViewHolder> {

    private final ArrayList<OrderBean.OrderListBean.ExtendOrderGoodsBean> arrayList;
    private OnItemClickListener onItemClickListener = null;

    public GoodsOrderListAdapter(ArrayList<OrderBean.OrderListBean.ExtendOrderGoodsBean> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_goods_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int positionInt = position;
        final OrderBean.OrderListBean.ExtendOrderGoodsBean bean = arrayList.get(position);

        ImageUtils.getInstance().displayRadius(bean.getGoodsImageUrl(), holder.mainImageView);
        holder.nameTextView.setText(bean.getGoodsName());
        holder.specTextView.setText(bean.getGoodsSpec());
        holder.moneyTextView.setText("￥");
        holder.moneyTextView.append(bean.getGoodsPrice());
        holder.numberTextView.setText(bean.getGoodsNum());
        holder.numberTextView.append(" 件");

        holder.mainRelativeLayout.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(positionInt, bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position, OrderBean.OrderListBean.ExtendOrderGoodsBean bean);
    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.mainRelativeLayout)
        RelativeLayout mainRelativeLayout;
        @BindView(R.id.mainImageView)
        AppCompatImageView mainImageView;
        @BindView(R.id.nameTextView)
        AppCompatTextView nameTextView;
        @BindView(R.id.specTextView)
        AppCompatTextView specTextView;
        @BindView(R.id.moneyTextView)
        AppCompatTextView moneyTextView;
        @BindView(R.id.numberTextView)
        AppCompatTextView numberTextView;

        private ViewHolder(View view) {
            super(view);
        }
    }
}
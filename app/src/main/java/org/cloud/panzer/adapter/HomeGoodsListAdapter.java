package org.cloud.panzer.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import org.cloud.core.base.BaseApplication;
import org.cloud.core.base.BaseImageLoader;
import org.cloud.core.base.BaseViewHolder;
import org.cloud.panzer.PanzerApplication;
import org.cloud.panzer.R;
import org.cloud.panzer.mvp.model.HomeInfoModel;

import java.util.ArrayList;

import butterknife.BindView;

public class HomeGoodsListAdapter extends RecyclerView.Adapter<HomeGoodsListAdapter.ViewHolder> {

    private final Activity activity;
    private final ArrayList<HomeInfoModel.HomeInfo.Goods> arrayList;

    public HomeGoodsListAdapter(Activity activity, ArrayList<HomeInfoModel.HomeInfo.Goods> arrayList) {
        this.activity = activity;
        this.arrayList = arrayList;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final HomeInfoModel.HomeInfo.Goods bean = arrayList.get(position);

        int width = BaseApplication.getInstance().getWidth() / 2 - 16;
        BaseImageLoader.getInstance().displayRadius(bean.getImage(), holder.mainImageView);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width);
        holder.mainImageView.setLayoutParams(layoutParams);
        holder.nameTextView.setText(bean.getName());
        holder.moneyTextView.setText("￥");
        holder.moneyTextView.append(String.valueOf(bean.getPrice()));

        holder.mainRelativeLayout.setOnClickListener(view -> ((PanzerApplication) PanzerApplication.getInstance()).startGoods(activity, bean.getId()+""));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int viewType) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_home_goods, group, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends BaseViewHolder {

        @BindView(R.id.mainRelativeLayout)
        RelativeLayout mainRelativeLayout;
        @BindView(R.id.mainImageView)
        AppCompatImageView mainImageView;
        @BindView(R.id.nameTextView)
        AppCompatTextView nameTextView;
        @BindView(R.id.moneyTextView)
        AppCompatTextView moneyTextView;

        private ViewHolder(View view) {
            super(view);
        }
    }
}
package org.cloud.panzer.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import org.cloud.core.base.BaseViewHolder;
import org.cloud.panzer.R;
import org.cloud.panzer.bean.EvaluateGoodsBean;

import java.util.ArrayList;

import butterknife.BindView;

public class EvaluateGoodsSimpleListAdapter extends RecyclerView.Adapter<EvaluateGoodsSimpleListAdapter.ViewHolder> {

    private final ArrayList<EvaluateGoodsBean> arrayList;
    private OnItemClickListener onItemClickListener;

    public EvaluateGoodsSimpleListAdapter(ArrayList<EvaluateGoodsBean> arrayList) {
        this.arrayList = arrayList;
        this.onItemClickListener = null;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final int positionInt = position;
        final EvaluateGoodsBean bean = arrayList.get(position);

        holder.mainRatingBar.setRating(Float.parseFloat(bean.getGevalScores()));
        holder.contentTextView.setText(bean.getGevalFrommembername());
        holder.contentTextView.append("：");
        holder.contentTextView.append(bean.getGevalContent());
        holder.timeTextView.setText(bean.getGevalAddtimeDate());

        holder.mainRelativeLayout.setOnClickListener(view -> {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(positionInt, bean);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup group, int viewType) {
        View view = LayoutInflater.from(group.getContext()).inflate(R.layout.item_list_evaluate_goods_simple, group, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int position, EvaluateGoodsBean bean);
    }

    class ViewHolder extends BaseViewHolder {

        @BindView(R.id.mainRelativeLayout)
        RelativeLayout mainRelativeLayout;
        @BindView(R.id.mainRatingBar)
        AppCompatRatingBar mainRatingBar;
        @BindView(R.id.contentTextView)
        AppCompatTextView contentTextView;
        @BindView(R.id.timeTextView)
        AppCompatTextView timeTextView;

        private ViewHolder(View view) {
            super(view);
        }

    }
}

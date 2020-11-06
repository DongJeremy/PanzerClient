package org.cloud.panzer.ui.main;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.cloud.core.base.BaseMvpFragment;
import org.cloud.core.utils.JsonUtils;
import org.cloud.panzer.App;
import org.cloud.panzer.R;
import org.cloud.panzer.adapter.HomeListAdapter;
import org.cloud.panzer.bean.ArticleBean;
import org.cloud.panzer.bean.HomeBean;
import org.cloud.panzer.mvp.contract.HomeContract;
import org.cloud.panzer.mvp.presenter.HomePresenter;

import java.util.ArrayList;

import butterknife.BindView;

@SuppressWarnings("rawtypes")
public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    public ArrayList<ArticleBean> articleArrayList;
    public HomeListAdapter mainAdapter;
    public ArrayList<HomeBean> mainArrayList;

    @BindView(R.id.mainRecyclerView)
    RecyclerView mainRecyclerView;
    @BindView(R.id.mainSwipeRefreshLayout)
    SwipeRefreshLayout mainSwipeRefreshLayout;
    @BindView(R.id.messageDotTextView)
    AppCompatTextView messageDotTextView;
    @BindView(R.id.messageImageView)
    AppCompatImageView messageImageView;
    @BindView(R.id.photoImageView)
    AppCompatImageView photoImageView;
    @BindView(R.id.scanImageView)
    AppCompatImageView scanImageView;
    @BindView(R.id.searchEditText)
    AppCompatEditText searchEditText;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView() {
        this.mainArrayList = new ArrayList<>();
        this.articleArrayList = new ArrayList<>();
        this.mainAdapter = new HomeListAdapter(getActivity(), this.mainArrayList);
        App.getInstance().setRecyclerView(getActivity(), mainRecyclerView, mainAdapter);
        App.getInstance().setSwipeRefreshLayout(mainSwipeRefreshLayout);
    }

    @Override
    protected void initListener() {
        scanImageView.setOnClickListener(v -> App.getInstance().startCapture(getActivity()));
        messageImageView.setOnClickListener(view -> App.getInstance().startChatList(getActivity()));
    }

    @Override
    protected void initData() {
        mPresenter.requestHomeInfoData();
        mPresenter.requestArticleListData();
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showHomeInfoData(String homeInfoData) {
        JsonArray jsonArrays = new JsonParser().parse(homeInfoData).getAsJsonArray();
        for (int i = 0; i < jsonArrays.size(); i++) {
            JsonObject jsonObject = jsonArrays.get(i).getAsJsonObject();
            HomeBean homeBean = new HomeBean();
            if (jsonObject.has("adv_list")) {
                homeBean.setType("adv_list");
                homeBean.setAdvListBean(JsonUtils.jsonToBean(jsonObject.get("adv_list"), HomeBean.AdvListBean.class));
                this.mainArrayList.add(homeBean);
            }
            if (jsonObject.has("home_nav")) {
                homeBean.setType("home_nav");
                homeBean.setHomeNavBean(JsonUtils.jsonToBean(jsonObject.get("home_nav"), HomeBean.HomeNavBean.class));
                this.mainArrayList.add(homeBean);
            }
            if (jsonObject.has("home1")) {
                homeBean.setType("home1");
                homeBean.setHome1Bean(JsonUtils.jsonToBean(jsonObject.get("home1"), HomeBean.Home1Bean.class));
                this.mainArrayList.add(homeBean);
            }
            if (jsonObject.has("goods")) {
                homeBean.setType("goods");
                homeBean.setGoodsBean(JsonUtils.jsonToBean(jsonObject.get("goods"), HomeBean.GoodsBean.class));
                this.mainArrayList.add(homeBean);
            }
        }
        this.mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void showArticleListData(String articleListData) {
        JsonArray jsonArrays = new JsonParser().parse(articleListData).getAsJsonObject().getAsJsonArray("article_list");
        this.articleArrayList.addAll(JsonUtils.jsonToList(jsonArrays, ArticleBean.class));
        if (articleArrayList.size() != 0) {
            HomeBean homeBean = new HomeBean();
            homeBean.setType("article");
            homeBean.setArticleList(HomeFragment.this.articleArrayList);
            if (mainArrayList.size() == 0) {
                this.mainArrayList.add(homeBean);
            } else {
                this.mainArrayList.add(2, homeBean);
            }
        }
        this.mainAdapter.notifyDataSetChanged();
    }
}
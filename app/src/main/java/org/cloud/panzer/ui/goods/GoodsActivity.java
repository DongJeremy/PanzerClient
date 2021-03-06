package org.cloud.panzer.ui.goods;

import android.content.Intent;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.VideoView;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import org.cloud.core.base.BaseAnimClient;
import org.cloud.core.base.BaseBean;
import org.cloud.core.base.BaseConstant;
import org.cloud.core.base.BaseMvpActivity;
import org.cloud.core.base.BaseToast;
import org.cloud.core.rx.RxBus;
import org.cloud.core.utils.BarUtils;
import org.cloud.core.utils.ConvertUtils;
import org.cloud.core.utils.ImageUtils;
import org.cloud.core.utils.JsonUtils;
import org.cloud.core.utils.ScreenUtils;
import org.cloud.core.utils.StatusBarUtils;
import org.cloud.core.utils.Utils;
import org.cloud.core.widget.FlowLayoutManager;
import org.cloud.panzer.App;
import org.cloud.panzer.R;
import org.cloud.panzer.adapter.EvaluateGoodsSimpleListAdapter;
import org.cloud.panzer.adapter.GoodsCommendListAdapter;
import org.cloud.panzer.adapter.SpecListAdapter;
import org.cloud.panzer.adapter.VoucherGoodsListAdapter;
import org.cloud.panzer.bean.EvaluateGoodsBean;
import org.cloud.panzer.bean.GoodsCommendBean;
import org.cloud.panzer.bean.VoucherGoodsBean;
import org.cloud.panzer.mvp.contract.GoodsContract;
import org.cloud.panzer.mvp.presenter.GoodsPresenter;
import org.cloud.panzer.ui.choose.AreaActivity;
import org.cloud.panzer.view.CenterTextView;
import org.cloud.panzer.view.CountdownTextView;
import org.cloud.panzer.view.ScrollDetailsLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;

import static org.cloud.panzer.event.RxBusCode.RX_BUS_CODE_MAIN_CART_SHOW;

//import static org.cloud.core.rx.RxBusCode.RX_BUS_CODE_MAIN_CART_SHOW;

public class GoodsActivity extends BaseMvpActivity<GoodsPresenter> implements GoodsContract.View {

    @BindView(R.id.mainSlideDetailsLayout)
    ScrollDetailsLayout mainSlideDetailsLayout;
    @BindView(R.id.mainScrollView)
    NestedScrollView mainScrollView;
    @BindView(R.id.headerRelativeLayout)
    RelativeLayout headerRelativeLayout;
    @BindView(R.id.mainBanner)
    Banner<String, BannerImageAdapter> mainBanner;
    @BindView(R.id.mainVideoPlayer)
    VideoView mainVideoPlayer;

    // toolbar
    @BindView(R.id.mainToolbar)
    Toolbar mainToolbar;
    @BindView(R.id.toolbarView)
    View toolbarView;
    @BindView(R.id.toolbarLineView)
    View toolbarLineView;
    @BindView(R.id.favoritesImageView)
    AppCompatImageView favoritesImageView;
    @BindView(R.id.shareImageView)
    AppCompatImageView shareImageView;
    @BindView(R.id.saleRelativeLayout)
    RelativeLayout saleRelativeLayout;
    @BindView(R.id.saleTypeTextView)
    AppCompatTextView saleTypeTextView;
    @BindView(R.id.saleTimeTextView)
    CountdownTextView saleTimeTextView;
    @BindView(R.id.nameTextView)
    AppCompatTextView nameTextView;
    @BindView(R.id.descTextView)
    AppCompatTextView descTextView;
    @BindView(R.id.moneyTextView)
    AppCompatTextView moneyTextView;
    @BindView(R.id.marketPriceTextView)
    AppCompatTextView marketPriceTextView;
    @BindView(R.id.mobileTextView)
    AppCompatTextView mobileTextView;
    @BindView(R.id.saleTextView)
    AppCompatTextView saleTextView;
    @BindView(R.id.activityLinearLayout)
    LinearLayoutCompat activityLinearLayout;
    @BindView(R.id.activityTitleTextView)
    AppCompatTextView activityTitleTextView;
    @BindView(R.id.activityDescTextView)
    AppCompatTextView activityDescTextView;
    @BindView(R.id.manSongLinearLayout)
    LinearLayoutCompat manSongLinearLayout;
    @BindView(R.id.manSongDescTextView)
    AppCompatTextView manSongDescTextView;
    @BindView(R.id.manSongGoodsImageView)
    AppCompatImageView manSongGoodsImageView;
    @BindView(R.id.voucherTextView)
    AppCompatTextView voucherTextView;
    @BindView(R.id.areaRelativeLayout)
    RelativeLayout areaRelativeLayout;
    @BindView(R.id.areaAddressTextView)
    AppCompatTextView areaAddressTextView;
    @BindView(R.id.areaHaveTextView)
    AppCompatTextView areaHaveTextView;
    @BindView(R.id.areaChooseTextView)
    AppCompatTextView areaChooseTextView;
    @BindView(R.id.specRelativeLayout)
    RelativeLayout specRelativeLayout;
    @BindView(R.id.specOneTextView)
    AppCompatTextView specOneTextView;
    @BindView(R.id.specTwoTextView)
    AppCompatTextView specTwoTextView;
    @BindView(R.id.specThrTextView)
    AppCompatTextView specThrTextView;
    @BindView(R.id.specFouTextView)
    AppCompatTextView specFouTextView;
    @BindView(R.id.specFivTextView)
    AppCompatTextView specFivTextView;

    @BindView(R.id.serviceDescTextView)
    AppCompatTextView serviceDescTextView;
    @BindView(R.id.serviceSevDayTextView)
    AppCompatTextView serviceSevDayTextView;
    @BindView(R.id.serviceQualityTextView)
    AppCompatTextView serviceQualityTextView;
    @BindView(R.id.serviceReissueTextView)
    AppCompatTextView serviceReissueTextView;
    @BindView(R.id.serviceLogisticsTextView)
    AppCompatTextView serviceLogisticsTextView;
    @BindView(R.id.evaluateRelativeLayout)
    RelativeLayout evaluateRelativeLayout;
    @BindView(R.id.evaluateDescTextView)
    AppCompatTextView evaluateDescTextView;
    @BindView(R.id.evaluateNumberTextView)
    AppCompatTextView evaluateNumberTextView;
    @BindView(R.id.evaluateRecyclerView)
    RecyclerView evaluateRecyclerView;
    @BindView(R.id.storeRelativeLayout)
    RelativeLayout storeRelativeLayout;
    @BindView(R.id.storeNameTextView)
    AppCompatTextView storeNameTextView;
    @BindView(R.id.storeOwnTextView)
    AppCompatTextView storeOwnTextView;
    @BindView(R.id.storeDescTextView)
    AppCompatTextView storeDescTextView;
    @BindView(R.id.storeDescPercentTextView)
    AppCompatTextView storeDescPercentTextView;
    @BindView(R.id.storeServiceTextView)
    AppCompatTextView storeServiceTextView;
    @BindView(R.id.storeServicePercentTextView)
    AppCompatTextView storeServicePercentTextView;
    @BindView(R.id.storeDeliveryTextView)
    AppCompatTextView storeDeliveryTextView;
    @BindView(R.id.storeDeliveryPercentTextView)
    AppCompatTextView storeDeliveryPercentTextView;
    @BindView(R.id.commendRecyclerView)
    RecyclerView commendRecyclerView;
    @BindView(R.id.customerTextView)
    CenterTextView customerTextView;
    @BindView(R.id.cartTextView)
    CenterTextView cartTextView;
    @BindView(R.id.addCartTextView)
    AppCompatTextView addCartTextView;
    @BindView(R.id.buyTextView)
    AppCompatTextView buyTextView;
    @BindView(R.id.chooseRelativeLayout)
    RelativeLayout chooseRelativeLayout;
    @BindView(R.id.chooseGoodsImageView)
    AppCompatImageView chooseGoodsImageView;
    @BindView(R.id.chooseNameTextView)
    AppCompatTextView chooseNameTextView;
    @BindView(R.id.choosePriceTextView)
    AppCompatTextView choosePriceTextView;
    @BindView(R.id.chooseStorageTextView)
    AppCompatTextView chooseStorageTextView;
    @BindView(R.id.chooseLineOneView)
    View chooseLineOneView;
    @BindView(R.id.chooseLineTwoView)
    View chooseLineTwoView;
    @BindView(R.id.chooseLineThrView)
    View chooseLineThrView;
    @BindView(R.id.chooseLineFouView)
    View chooseLineFouView;
    @BindView(R.id.chooseLineFivView)
    View chooseLineFivView;

    @BindView(R.id.mainWebView)
    WebView mainWebView;

    @BindView(R.id.chooseValueOneTextView)
    AppCompatTextView chooseValueOneTextView;
    @BindView(R.id.chooseValueTwoTextView)
    AppCompatTextView chooseValueTwoTextView;
    @BindView(R.id.chooseValueThrTextView)
    AppCompatTextView chooseValueThrTextView;
    @BindView(R.id.chooseValueFouTextView)
    AppCompatTextView chooseValueFouTextView;
    @BindView(R.id.chooseValueFivTextView)
    AppCompatTextView chooseValueFivTextView;

    @BindView(R.id.chooseValueOneRecyclerView)
    RecyclerView chooseValueOneRecyclerView;
    @BindView(R.id.chooseValueTwoRecyclerView)
    RecyclerView chooseValueTwoRecyclerView;
    @BindView(R.id.chooseValueThrRecyclerView)
    RecyclerView chooseValueThrRecyclerView;
    @BindView(R.id.chooseValueFouRecyclerView)
    RecyclerView chooseValueFouRecyclerView;
    @BindView(R.id.chooseValueFivRecyclerView)
    RecyclerView chooseValueFivRecyclerView;
    @BindView(R.id.chooseAddTextView)
    AppCompatTextView chooseAddTextView;
    @BindView(R.id.chooseNumberEditText)
    AppCompatEditText chooseNumberEditText;
    @BindView(R.id.chooseSubTextView)
    AppCompatTextView chooseSubTextView;
    @BindView(R.id.voucherLinearLayout)
    LinearLayoutCompat voucherLinearLayout;
    @BindView(R.id.voucherRecyclerView)
    RecyclerView voucherRecyclerView;
    @BindView(R.id.nightTextView)
    AppCompatTextView nightTextView;

    private View[] chooseLineView;
    private AppCompatTextView[] chooseValueTextView;
    private RecyclerView[] chooseValueRecyclerView;
    private AppCompatTextView[] specTextView;

    private ArrayList<EvaluateGoodsBean> evaluateGoodsArrayList;
    private EvaluateGoodsSimpleListAdapter evaluateGoodsAdapter;
    private ArrayList<GoodsCommendBean> commendArrayList;
    private GoodsCommendListAdapter commendAdapter;
    private ArrayList<VoucherGoodsBean> voucherArrayList;
    private VoucherGoodsListAdapter voucherAdapter;

    private final ArrayList<String> goodsImageArrayList = new ArrayList<>();

    private ArrayList<HashMap<String, String>> specNameArrayList;
    private ArrayList<HashMap<String, String>> specValueArrayList;
    private ArrayList<HashMap<String, String>> goodsSpecArrayList = new ArrayList<>();
    private ArrayList<HashMap<String, String>> specListArrayList = new ArrayList<>();
    private String[] specString = new String[]{"", "", "", "", ""};

    private String shareUrl;
    private String shareText;
    private String shareTitle;
    private String shareImageUrl = "";
    private String goodsId = "";
    // 是否有存货
    private boolean isHaveGoods;
    private String storeId = "";
    private String memberId = "";
    private boolean isBackBoolean;

    private String goodsStorageString = "";
    private String lowerLimitString = "";
    private String upperLimitString = "";

    private String goodsIdString;
    private boolean isShowBoolean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_goods;
    }

    @Override
    protected GoodsPresenter createPresenter() {
        return new GoodsPresenter();
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    protected void initView() {
        StatusBarUtils.setImageNoStatusBar(this, true);

        //headerRelativeLayout配置
        LinearLayoutCompat.LayoutParams layoutParams = (LinearLayoutCompat.LayoutParams) this.headerRelativeLayout.getLayoutParams();
        layoutParams.height = ScreenUtils.getScreenWidth();
        headerRelativeLayout.setLayoutParams(layoutParams);
        // toolbar虚拟高度配置增加StatusBarHeight
        LinearLayoutCompat.LayoutParams layoutParams2 = (LinearLayoutCompat.LayoutParams) this.toolbarView.getLayoutParams();
        layoutParams2.height = BarUtils.getStatusBarHeight(Utils.getContext());
        toolbarView.setLayoutParams(layoutParams2);
        setToolbar(this.mainToolbar, "商品详情");
        // toolbar初始状态透明
        this.mainToolbar.setAlpha(0.0f);
        this.toolbarView.setAlpha(0.0f);
        this.toolbarLineView.setAlpha(0.0f);

        App.getInstance().setWebView(this, mainWebView);

        // 配置详情图片页面

        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.shareImageView.getLayoutParams();
        layoutParams3.height = ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(112);
        this.shareImageView.setLayoutParams(layoutParams3);

        // 配置Banner
        mainBanner.setAdapter(new BannerImageAdapter<String>(goodsImageArrayList) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                ImageUtils.getInstance().display(data, holder.imageView);
            }
        });
        // 评价
        evaluateGoodsArrayList = new ArrayList<>();
        evaluateGoodsAdapter = new EvaluateGoodsSimpleListAdapter(evaluateGoodsArrayList);
        App.getInstance().setRecyclerView(getActivity(), evaluateRecyclerView, evaluateGoodsAdapter);
        // 推荐
        commendArrayList = new ArrayList<>();
        commendAdapter = new GoodsCommendListAdapter(commendArrayList);
        App.getInstance().setRecyclerView(getActivity(), commendRecyclerView, commendAdapter);
        commendRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        // 代币券
        voucherArrayList = new ArrayList<>();
        voucherAdapter = new VoucherGoodsListAdapter(voucherArrayList);
        App.getInstance().setRecyclerView(getActivity(), voucherRecyclerView, voucherAdapter);

        specTextView = new AppCompatTextView[] {
                specOneTextView, specTwoTextView, specThrTextView, specFouTextView, specFivTextView
        };
        for (AppCompatTextView spec : specTextView) {
            spec.setVisibility(View.GONE);
        }
        chooseLineView = new View[] {
                chooseLineOneView, chooseLineTwoView, chooseLineThrView, chooseLineFouView, chooseLineFivView
        };
        for (View view : chooseLineView) {
            view.setVisibility(View.GONE);
        }
        chooseValueTextView = new AppCompatTextView[] {
                chooseValueOneTextView, chooseValueTwoTextView, chooseValueThrTextView, chooseValueFouTextView, chooseValueFivTextView
        };
        for (AppCompatTextView appCompatTextView : chooseValueTextView) {
            appCompatTextView.setVisibility(View.GONE);
        }
        chooseValueRecyclerView = new RecyclerView[]{
                chooseValueOneRecyclerView, chooseValueTwoRecyclerView, chooseValueThrRecyclerView, chooseValueFouRecyclerView, chooseValueFivRecyclerView
        };
        for (RecyclerView recyclerView : chooseValueRecyclerView) {
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        goodsIdString = getIntent().getStringExtra(BaseConstant.DATA_ID);
        if (TextUtils.isEmpty(goodsIdString)) {
            BaseToast.getInstance().showDataError();
            App.getInstance().finish(getActivity());
        }
        isShowBoolean = false;

        this.isHaveGoods = true;
        // 获取数据
        getData(goodsIdString);
    }

    @Override
    protected void initListener() {
        mainSlideDetailsLayout.setOnSlideDetailsListener(currentTargetIndex -> {
            int i = currentTargetIndex.ordinal();
            if (i == 0) {
                setToolbar(this.mainToolbar, "商品详情");
            } else if (i == 1) {
                setToolbar(this.mainToolbar, "商品介绍");
                mPresenter.requestGoodsImagesData(goodsIdString);
            }
        });
        // 滚动加载
        mainScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            float scrollYY = (float) v.getScrollY();
            float width = (float) (ScreenUtils.getScreenWidth() - ConvertUtils.dp2px(48));
            int i5 = Float.compare(scrollYY, 0.0f);
            if (i5 == 0) {
                this.mainToolbar.setAlpha(0.0f);
                this.toolbarView.setAlpha(0.0f);
                this.toolbarLineView.setAlpha(0.0f);
            } else if (i5 > 0) {
                float max = 1.0f - Math.max((width - scrollYY) / width, 0.0f);
                this.mainToolbar.setAlpha(max);
                this.toolbarView.setAlpha(max);
                this.toolbarLineView.setAlpha(max);
            } else {
                this.mainToolbar.setAlpha(0.0f);
                this.toolbarView.setAlpha(0.0f);
                this.toolbarLineView.setAlpha(0.0f);
            }
        });
        // 相关产品列表
        commendAdapter.setOnItemClickListener((position, bean) -> {
            goodsIdString = bean.getGoodsId();
            getData(goodsIdString);
        });
        // 点击加入购物车
        chooseRelativeLayout.setOnClickListener(view -> {
            //仅仅只是为了防止点到暗色标签
        });
        // 显示购物车
        cartTextView.setOnClickListener(view -> {
            Log.e("TAG", "cart show: ");
            App.getInstance().finish(getActivity());
            RxBus.getInstance().send(RX_BUS_CODE_MAIN_CART_SHOW);
        });
        // 加入购物车
        addCartTextView.setOnClickListener(view -> {
            if (!isHaveGoods) {
                BaseToast.getInstance().show("没货啦！");
                return;
            }
            if (chooseRelativeLayout.getVisibility() == View.GONE) {
                showChooseLayout();
            } else {
                addCart();
            }
        });
        // 立即购买
        buyTextView.setOnClickListener(view -> {
            if (!this.isHaveGoods) {
                BaseToast.getInstance().show("没货啦");
            } else if (this.chooseRelativeLayout.getVisibility() == View.GONE) {
                showChooseLayout();
            } else {
                buy();
            }
        });
        // 商品评价
        this.evaluateRelativeLayout.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EvaluateActivity.class);
            intent.putExtra(BaseConstant.DATA_ID, this.goodsId);
            App.getInstance().start(getActivity(), intent);
        });
        // 店铺信息
        this.storeRelativeLayout.setOnClickListener(view -> App.getInstance().startStore(getActivity(), this.storeId));
        // 增加数量
        chooseAddTextView.setOnClickListener(view -> {
            String number = (Integer.parseInt(Objects.requireNonNull(chooseNumberEditText.getText()).toString()) + 1) + "";
            chooseNumberEditText.setText(number);
            changeNumber();
        });
        // 更改数量
        chooseNumberEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                changeNumber();
            }
            return false;
        });
        // 较少数量
        chooseSubTextView.setOnClickListener(view -> {
            String number = (Integer.parseInt(Objects.requireNonNull(chooseNumberEditText.getText()).toString()) - 1) + "";
            chooseNumberEditText.setText(number);
            changeNumber();
        });
        // 规格选择
        specRelativeLayout.setOnClickListener(view -> showChooseLayout());
        // 地址选择
        areaRelativeLayout.setOnClickListener(view -> App.getInstance().start(getActivity(), AreaActivity.class, 1000));
    }

    @Override
    public void showCalcSuccess(BaseBean baseBean) {
        JsonObject jsonObject = JsonUtils.parseJsonToJsonObject(baseBean.getDatas());
        this.areaHaveTextView.setText(jsonObject.get("if_store_cn").getAsString());
        this.areaChooseTextView.setText(jsonObject.get("content").getAsString());
        this.isHaveGoods = jsonObject.get("if_store").getAsBoolean();
    }

    @Override
    public void showGoodsImagesData(String goodsInfoData) {
        App.getInstance().loadHtml(mainWebView, goodsInfoData);
        //mainWebView.loadUrl(BaseConstant.URL_GOODS_BODY + goodsIdString);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == -1 && requestCode == 1000) {
            this.areaAddressTextView.setText(intent.getStringExtra("area_info"));
            mPresenter.requestCalc(this.goodsId, intent.getStringExtra("area_id"));
        }
    }

    @Override
    public void showGoodsDetailSuccess(BaseBean baseBean) {
        handlerData(baseBean);
    }

    @Override
    public void showAddGoodsSuccess(BaseBean baseBean) {
        BaseToast.getInstance().showSuccess();
        this.goneChooseLayout();
    }

    @Override
    public void onStart() {
        super.onStart();
        mainBanner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        mainBanner.stop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mainVideoPlayer.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mainVideoPlayer.resume();
    }

    @Override
    public void onBackPressed() {
        if (!this.mainVideoPlayer.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onReturn() {
        if (!this.mainVideoPlayer.isFullScreen()) {
            if (this.nightTextView.getVisibility() == View.VISIBLE) {
                goneChooseLayout();
//                goneVoucher();
//                goneShare();
                return;
            }
            super.onReturn();
        }
    }

    // 自定义数据和方法

    private void buy() {
        App.getInstance().startGoodsBuy(getActivity(), this.goodsId + "|" + ((Editable) Objects.requireNonNull(this.chooseNumberEditText.getText())).toString(), "");
        goneChooseLayout();
    }

    private void changeNumber() {
        if (TextUtils.isEmpty(Objects.requireNonNull(chooseNumberEditText.getText()).toString())) {
            BaseToast.getInstance().show("数量不能为空！");
            chooseNumberEditText.setText("1");
            chooseNumberEditText.setSelection(1);
            return;
        }
        int number = Integer.parseInt(chooseNumberEditText.getText().toString());
        if (number <= 0) {
            BaseToast.getInstance().show("最低要买 1 件");
            number = 1;
        }
        if (!TextUtils.isEmpty(upperLimitString)) {
            int upper = Integer.parseInt(upperLimitString);
            if (number > upper) {
                number = upper;
                BaseToast.getInstance().show("每人最高限购：" + number + " 件");
            }
        }
        if (!TextUtils.isEmpty(lowerLimitString)) {
            int lower = Integer.parseInt(lowerLimitString);
            if (number < lower) {
                number = lower;
                BaseToast.getInstance().show("最低要购买：" + number + " 件");
            }
        }
        int storage = Integer.parseInt(goodsStorageString);
        if (number > storage) {
            number = storage;
            BaseToast.getInstance().show("库存不足！");
        }
        String temp = number + "";
        chooseNumberEditText.setText(temp);
        chooseNumberEditText.setSelection(temp.length());
    }

    private void showChooseLayout() {
        if (this.nightTextView.getVisibility() == View.GONE) {
            this.nightTextView.setVisibility(View.VISIBLE);
            BaseAnimClient.objectAnimator(this.nightTextView, BaseAnimClient.ALPHA, 0.0f, 1.0f);
        }
        if (this.chooseRelativeLayout.getVisibility() == View.GONE) {
            this.chooseRelativeLayout.setVisibility(View.VISIBLE);
            BaseAnimClient.objectAnimator(this.chooseRelativeLayout, BaseAnimClient.TRABSLATION_Y, (float) ScreenUtils.getScreenHeight(), 0.0f);
        }
    }

    private void goneChooseLayout() {
        if (nightTextView.getVisibility() == View.VISIBLE) {
            nightTextView.setVisibility(View.GONE);
            BaseAnimClient.objectAnimator(this.nightTextView, BaseAnimClient.ALPHA, 0.0f, 1.0f);
        }

        if (chooseRelativeLayout.getVisibility() == View.VISIBLE) {
            chooseRelativeLayout.setVisibility(View.GONE);
            BaseAnimClient.objectAnimator(this.chooseRelativeLayout, BaseAnimClient.TRABSLATION_Y,
                    () -> this.chooseRelativeLayout.setVisibility(View.GONE), 0.0f, (float) ScreenUtils.getScreenHeight());
        }

    }

    private void addCart() {
        if (!App.getInstance().isLogin()) {
            App.getInstance().startLogin(getActivity());
        } else {
            mPresenter.requestCartAdd(this.goodsId, ((Editable) Objects.requireNonNull(this.chooseNumberEditText.getText())).toString());
        }
    }

    // 解析json数据
    private void handlerData(BaseBean baseBean) {
        String temp = "";
        JsonObject mainJsonObject = JsonUtils.parseJsonToJsonObject(baseBean.getDatas());
        JsonObject goodsInfoJSONObject = mainJsonObject.getAsJsonObject("goods_info");
        String[] goodsImages = mainJsonObject.get("goods_image").getAsString().split(",");
        goodsId = goodsInfoJSONObject.get("goods_id").getAsString();
        shareUrl = BaseConstant.URL_GOODS_DETAILED + goodsId;
        // 是否是视频文件
        if(!mainJsonObject.has("video_path")) {
            mainVideoPlayer.setVisibility(View.GONE);
        } else {
            mainVideoPlayer.setUrl(mainJsonObject.get("video_path").getAsString());
            StandardVideoController controller = new StandardVideoController(this);
            mainVideoPlayer.setVideoController(controller);
            mainVideoPlayer.start();
        }
        //轮播图
        goodsImageArrayList.clear();
        Collections.addAll(goodsImageArrayList, goodsImages);
        shareImageUrl = goodsImageArrayList.get(0);
        mainBanner.setDatas(goodsImageArrayList);
        mainBanner.start();
        //商品信息

        nameTextView.setText(goodsInfoJSONObject.get("goods_name").getAsString());
        descTextView.setText(goodsInfoJSONObject.get("goods_jingle").getAsString());
        descTextView.setVisibility(TextUtils.isEmpty(goodsInfoJSONObject.get("goods_jingle").getAsString()) ? View.GONE : View.VISIBLE);
        goodsStorageString = goodsInfoJSONObject.get("goods_storage").getAsString();
        shareTitle = nameTextView.getText().toString();
        shareText = descTextView.getText().toString();
        moneyTextView.setText("￥");
        marketPriceTextView.setText("￥");
        mobileTextView.setVisibility(View.GONE);
        saleRelativeLayout.setVisibility(View.GONE);
        if (goodsInfoJSONObject.has("goods_sale_type") && !goodsInfoJSONObject.get("goods_sale_type").getAsString().equals("0")) {
            activityLinearLayout.setVisibility(View.VISIBLE);
            activityTitleTextView.setText(goodsInfoJSONObject.get("title").getAsString());
            switch (goodsInfoJSONObject.get("sale_type").getAsString()) {
                case "sole":
                    mobileTextView.setVisibility(View.VISIBLE);
                    temp = "手机专享价格￥" + goodsInfoJSONObject.get("sale_price").getAsString();
                    break;
                case "xianshi":
                    saleRelativeLayout.setVisibility(View.VISIBLE);
                    saleTypeTextView.setText("限时打折");
                    lowerLimitString = goodsInfoJSONObject.get("lower_limit").getAsString();
                    temp = "直降￥" + goodsInfoJSONObject.get("down_price").getAsString() + "，最低 " + lowerLimitString + " 件起";
                    saleTimeTextView.init("", Long.parseLong(goodsInfoJSONObject.get("xs_time").getAsString()), "距离结束：", "");
                    saleTimeTextView.start(0);
                    break;
                case "groupbuy":
                    upperLimitString = goodsInfoJSONObject.get("upper_limit").getAsString();
                    temp = "直降￥" + goodsInfoJSONObject.get("down_price").getAsString() + "，限购 " + upperLimitString + " 件";
                    break;
                case "robbuy":
                    saleRelativeLayout.setVisibility(View.VISIBLE);
                    saleTypeTextView.setText("限时抢购");
                    upperLimitString = goodsInfoJSONObject.get("upper_limit").getAsString();
                    temp = "限购 " + upperLimitString + " 件，" + goodsInfoJSONObject.get("remark").getAsString();
                    saleTimeTextView.init("", Long.parseLong(goodsInfoJSONObject.get("end_time").getAsString()), "距离结束：", "");
                    saleTimeTextView.start(0);
                    break;
            }
            activityDescTextView.setText(temp);
            moneyTextView.append(goodsInfoJSONObject.get("sale_price").getAsString());
            marketPriceTextView.append(goodsInfoJSONObject.get("goods_price").getAsString());
        } else {
            activityLinearLayout.setVisibility(View.GONE);
            moneyTextView.append(goodsInfoJSONObject.get("goods_price").getAsString());
            marketPriceTextView.append(goodsInfoJSONObject.get("goods_marketprice").getAsString());
        }
        marketPriceTextView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        saleTextView.setText("销量：");
        saleTextView.append(goodsInfoJSONObject.get("goods_salenum").getAsString());

        //满送
        if(!mainJsonObject.has("mansong_info")) {
            manSongLinearLayout.setVisibility(View.GONE);
        } else {
            manSongLinearLayout.setVisibility(View.VISIBLE);
            JsonArray jsonArray = mainJsonObject.get("mansong_info").getAsJsonObject().getAsJsonArray("rules");
            JsonObject manSongJsonObject = jsonArray.get(0).getAsJsonObject();
            temp = "单笔订单满￥" + manSongJsonObject.get("price").getAsString() + "，立减￥" + manSongJsonObject.get("discount").getAsString() + "，送礼品";
            manSongDescTextView.setText(temp);
            if (manSongJsonObject.has("goods_image_url")) {
                ImageUtils.getInstance().display(manSongJsonObject.get("goods_image_url").getAsString(), manSongGoodsImageView);
            }
        }
        //代金券
        if (mainJsonObject.has("voucher")) {
            voucherArrayList.clear();
            voucherTextView.setVisibility(View.VISIBLE);
            JsonArray voucher = mainJsonObject.get("voucher").getAsJsonArray();
            voucherArrayList.addAll(JsonUtils.jsonToList(voucher, VoucherGoodsBean.class));
            voucherAdapter.notifyDataSetChanged();
        } else {
            voucherTextView.setVisibility(View.GONE);
        }
        //SpecName
        if(goodsInfoJSONObject.has("spec_name")) {
            specNameArrayList = new ArrayList<>();
            JsonObject jsonObject = goodsInfoJSONObject.get("spec_name").getAsJsonObject();
            for (Map.Entry<String, JsonElement> elementEntry : jsonObject.entrySet()) {
                HashMap<String, String> hashMap1 = new HashMap<>();
                String key = elementEntry.getKey();
                String value = jsonObject.get(key).getAsString();
                hashMap1.put("id", key);
                hashMap1.put("value", value);
                specNameArrayList.add(hashMap1);
            }
            for (int i = 0; i < specNameArrayList.size(); i++) {
                chooseLineView[i].setVisibility(View.VISIBLE);
                chooseValueRecyclerView[i].setVisibility(View.VISIBLE);
                chooseValueTextView[i].setVisibility(View.VISIBLE);
                chooseValueTextView[i].setText(specNameArrayList.get(i).get("value"));
            }
        } else {
            specTextView[0].setText("默认");
            specTextView[0].setVisibility(View.VISIBLE);
        }
        //specValue
        if(goodsInfoJSONObject.has("spec_value")) {
            specValueArrayList = new ArrayList<>();
            JsonObject jsonObject = goodsInfoJSONObject.get("spec_value").getAsJsonObject();
            if (specNameArrayList.size() != 0) {
                for (int i = 0; i < specNameArrayList.size(); i++) {
                    String id = specNameArrayList.get(i).get("id");
                    String value = specNameArrayList.get(i).get("value");
                    JsonObject object = jsonObject.getAsJsonObject(id);
                    for (Map.Entry<String, JsonElement> elementEntry : object.entrySet()) {
                        HashMap<String, String> hashMap1 = new HashMap<>();
                        String key = elementEntry.getKey();
                        hashMap1.put("value", object.get(key).getAsString());
                        hashMap1.put("parent_value", value);
                        hashMap1.put("parent_id", id);
                        hashMap1.put("id", key);
                        specValueArrayList.add(hashMap1);
                    }
                }
            }
        }

        //goodsSpec
        if(goodsInfoJSONObject.has("goods_spec")) {
            goodsSpecArrayList = new ArrayList<>();
            JsonObject jsonObject = goodsInfoJSONObject.get("goods_spec").getAsJsonObject();
            for (Map.Entry<String, JsonElement> elementEntry : jsonObject.entrySet()) {
                HashMap<String, String> hashMap1 = new HashMap<>();
                String key = elementEntry.getKey();
                String value = jsonObject.get(key).getAsString();
                for (int i = 0; i < specValueArrayList.size(); i++) {
                    String id = specValueArrayList.get(i).get("id");
                    if (key.equals(id)) {
                        String parent_value = specValueArrayList.get(i).get("parent_value");
                        hashMap1.put("key", key);
                        hashMap1.put("value", value);
                        hashMap1.put("content", parent_value + "：" + value);
                    }
                }
                goodsSpecArrayList.add(hashMap1);
            }
            for (int i = 0; i < goodsSpecArrayList.size(); i++) {
                specTextView[i].setVisibility(View.VISIBLE);
                specTextView[i].setText(goodsSpecArrayList.get(i).get("content"));
                specString[i] = goodsSpecArrayList.get(i).get("key");
            }
        }
        //specList
        //noinspection unchecked
        ArrayList<HashMap<String, String>>[] specArrayList = new ArrayList[2];
        SpecListAdapter[] specAdapter = new SpecListAdapter[2];
        JsonElement specList = mainJsonObject.get("spec_list");
        specListArrayList = new ArrayList<>(JsonUtils.jsonToList(specList));
        for (int i = 0; i < specNameArrayList.size(); i++) {
            specArrayList[i] = new ArrayList<>();
            String value = specNameArrayList.get(i).get("value");
            for (int j = 0; j < specValueArrayList.size(); j++) {
                if (value.equals(specValueArrayList.get(j).get("parent_value"))) {
                    HashMap<String, String> hashMap = new HashMap<>(specValueArrayList.get(j));
                    hashMap.put("default", "0");
                    for (int k = 0; k < goodsSpecArrayList.size(); k++) {
                        if (goodsSpecArrayList.get(k).get("value").equals(hashMap.get("value"))) {
                            hashMap.put("default", "1");
                            break;
                        }
                    }
                    specArrayList[i].add(hashMap);
                }
            }
        }
        isBackBoolean = true;
        if (specArrayList[0] != null) {
            for (int i = 0; i < specArrayList[0].size(); i++) {
                String id = specArrayList[0].get(i).get("id");
                if (id.equals(specString[0])) {
                    isBackBoolean = false;
                    break;
                }
            }
        }
        for (int i = 0; i < specArrayList.length; i++) {
            if (specArrayList[i] != null) {
                final int positionInt = i;
                specAdapter[i] = new SpecListAdapter(specArrayList[i]);
                FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
                chooseValueRecyclerView[i].setLayoutManager(flowLayoutManager);
                chooseValueRecyclerView[i].setAdapter(specAdapter[i]);
                specAdapter[i].setOnItemClickListener((position, id, value) -> {
                    if (isBackBoolean) {
                        if (positionInt == 1) {
                            specString[positionInt - 1] = id;
                        } else {
                            specString[positionInt + 1] = id;
                        }
                    } else {
                        specString[positionInt] = id;
                    }
                    refreshSpecData();
                });
            }
        }
        //虚拟物品
        if (goodsInfoJSONObject.get("is_virtual").getAsString().equals("0")) {
            areaRelativeLayout.setVisibility(View.VISIBLE);
        } else {
            areaRelativeLayout.setVisibility(View.GONE);
        }
        //店铺信息
        JsonObject storeInfo = mainJsonObject.getAsJsonObject("store_info");
        storeId = storeInfo.get("store_id").getAsString();
        memberId = storeInfo.get("member_id").getAsString();

        temp = "由 “" + storeInfo.get("store_name").getAsString() + "” 销售和发货，并享受售后服务";
        storeNameTextView.setText(storeInfo.get("store_name").getAsString());
        //voucherStoreNameTextView.setText(storeNameTextView.getText().toString());
        String isOwnShop = storeInfo.get("is_own_shop").getAsString();
        storeOwnTextView.setText(isOwnShop.equals("1") ? "自营店" : "");
        JsonObject storeCredit = storeInfo.getAsJsonObject("store_credit");
        JsonObject storeDesccredit = storeCredit.getAsJsonObject("store_desccredit");
        storeDescTextView.setText(storeDesccredit.get("credit").getAsString());
        JsonObject servicecredit = storeCredit.getAsJsonObject("store_servicecredit");
        storeServiceTextView.setText(servicecredit.get("credit").getAsString());
        JsonObject deliverycredit = storeCredit.getAsJsonObject("store_deliverycredit");
        storeDeliveryTextView.setText(deliverycredit.get("credit").getAsString());

        if(storeDesccredit.has("percent_text")) {
            String percent_text = storeDesccredit.get("percent_text").getAsString();
            storeDescPercentTextView.setText(isOwnShop.equals("1") ? "平" : percent_text);
            storeServicePercentTextView.setText(isOwnShop.equals("1") ? "平" : percent_text);
            storeDeliveryPercentTextView.setText(isOwnShop.equals("1") ? "平" : percent_text);
        }
        serviceDescTextView.setText(temp);
        //服务信息
        if (goodsInfoJSONObject.has("contractlist") && !(goodsInfoJSONObject.get("contractlist") instanceof JsonNull)) {
            serviceSevDayTextView.setVisibility(View.VISIBLE);
            serviceQualityTextView.setVisibility(View.VISIBLE);
            serviceReissueTextView.setVisibility(View.VISIBLE);
            serviceLogisticsTextView.setVisibility(View.VISIBLE);
        } else {
            serviceSevDayTextView.setVisibility(View.GONE);
            serviceQualityTextView.setVisibility(View.GONE);
            serviceReissueTextView.setVisibility(View.GONE);
            serviceLogisticsTextView.setVisibility(View.GONE);
        }
        //评价信息
        JsonObject goodsEvaluateInfo = mainJsonObject.getAsJsonObject("goods_evaluate_info");
        evaluateDescTextView.setText("好评率 ");
        evaluateDescTextView.append(goodsEvaluateInfo.get("good_percent").getAsString() + "%");
        evaluateNumberTextView.setText("(");
        evaluateNumberTextView.append(goodsEvaluateInfo.get("all").getAsString() + "人评价)");

        if(!mainJsonObject.has("goods_eval_list")) {
            evaluateRecyclerView.setVisibility(View.GONE);
        } else {
            evaluateRecyclerView.setVisibility(View.VISIBLE);
            evaluateGoodsArrayList.clear();
            evaluateGoodsArrayList.addAll(JsonUtils.jsonToList(mainJsonObject.get("goods_eval_list").getAsJsonArray(),
                    EvaluateGoodsBean.class));
            evaluateGoodsAdapter.notifyDataSetChanged();
        }
        //商品推荐
        commendArrayList.clear();
        commendArrayList.addAll(JsonUtils.jsonToList(mainJsonObject.getAsJsonArray("goods_commend_list"), GoodsCommendBean.class));
        commendAdapter.notifyDataSetChanged();
        //选择页面
        ImageUtils.getInstance().display(goodsImageArrayList.get(0), chooseGoodsImageView);
        chooseNameTextView.setText(nameTextView.getText().toString());
        choosePriceTextView.setText(moneyTextView.getText().toString());
        chooseStorageTextView.setText("库存：");
        chooseStorageTextView.append(goodsStorageString);
        if (!TextUtils.isEmpty(lowerLimitString)) {
            chooseNumberEditText.setText(lowerLimitString);
        }
        chooseNumberEditText.setSelection(chooseNumberEditText.getText().length());
//        haveGoods = !goodsStorageString.equals("0");
    }

    private void refreshSpecData() {
        int i = 0;
        while (true) {
            if (i >= this.specListArrayList.size()) {
                break;
            }
            String str = this.specListArrayList.get(i).get(BaseConstant.DATA_KEY);
            if (Objects.requireNonNull(str).contains(this.specString[0]) && str.contains(this.specString[1]) &&
                    str.contains(this.specString[2]) && str.contains(this.specString[3]) && str.contains(this.specString[4])) {
                this.goodsId = this.specListArrayList.get(i).get("value");
                break;
            }
            i++;
        }
        getData(goodsId);
    }

    private void getData(String goodsId) {
        mPresenter.requestGoodsDetailData(goodsId);
        //mPresenter.requestGoodsImagesData(goodsId);
    }
}
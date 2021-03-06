package org.cloud.panzer.mvp.contract;

import org.cloud.core.base.BaseBean;
import org.cloud.core.mvp.IModel;
import org.cloud.core.mvp.IView;

import io.reactivex.Observable;

public interface GoodsEvaluateContract {

    interface View extends IView {
        void showGoodsEvaluateSuccess(BaseBean baseBean);
        void showGoodsEvaluateFail(String reason);
    }

    interface Model extends IModel {
        Observable<String> goodsEvaluate(String goodsId, String type, String page, String curpage);
    }
}

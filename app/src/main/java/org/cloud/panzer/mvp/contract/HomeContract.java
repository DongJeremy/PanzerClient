package org.cloud.panzer.mvp.contract;

import org.cloud.core.mvp.IModel;
import org.cloud.core.mvp.IView;
import org.cloud.panzer.bean.BaseBean;
import org.cloud.panzer.mvp.model.HomeInfoModel;

import io.reactivex.Observable;

public interface HomeContract {

    interface View extends IView {
        void showHomeInfoData(String homeInfoData);
    }

    interface Model extends IModel {
        Observable<BaseBean> getHomeInfoData();
    }
}

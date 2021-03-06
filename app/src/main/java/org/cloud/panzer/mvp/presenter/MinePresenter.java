package org.cloud.panzer.mvp.presenter;

import org.cloud.core.mvp.BasePresenter;
import org.cloud.core.net.BaseObserver;
import org.cloud.core.rx.RxSchedulers;
import org.cloud.core.utils.JsonUtils;
import org.cloud.panzer.mvp.contract.MineContract;
import org.cloud.panzer.mvp.model.MineModel;

public class MinePresenter extends BasePresenter<MineContract.Model, MineContract.View> {
    @Override
    protected MineContract.Model createModel() {
        return new MineModel();
    }

    public void requestMemberIndexData() {
        getModel().getMemberIndex()
                .compose(RxSchedulers.applySchedulers(getLifecycleProvider()))
                .subscribe(new BaseObserver<String>(getView()){
                    @Override
                    public void onSuccess(String result) {
                        getView().showMemberIndexSuccess(JsonUtils.parseJsonToBaseBean(result));
                    }

                    @Override
                    public void onFailure(String errMsg, boolean isNetError) {
                        getView().showMemberIndexFail(errMsg);
                    }
                });

    }
}

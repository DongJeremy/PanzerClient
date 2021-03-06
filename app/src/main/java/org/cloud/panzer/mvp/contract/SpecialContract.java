package org.cloud.panzer.mvp.contract;

import org.cloud.core.base.BaseBean;
import org.cloud.core.mvp.IModel;
import org.cloud.core.mvp.IView;

import io.reactivex.Observable;

public interface SpecialContract {

    interface View extends IView {
        void showSpecialSuccess(BaseBean baseBean);
        void showSpecialFail(String msg);
    }

    interface Model extends IModel {
        Observable<String> special(String id);
    }
}

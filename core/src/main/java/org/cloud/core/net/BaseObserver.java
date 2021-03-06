package org.cloud.core.net;

import android.accounts.NetworkErrorException;
import android.util.Log;

import org.cloud.core.mvp.IView;
import org.cloud.core.net.exception.ServerException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author xuhao
 * @date 2018/6/12 10:51
 * @desc Observer基类
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private IView mView;

    private boolean isShowDialog = true;

    public BaseObserver(IView mView) {
        this.mView = mView;
    }

    public BaseObserver(IView mView, boolean isShowDialog) {
        this.mView = mView;
        this.isShowDialog = isShowDialog;
    }

    @Override
    public void onSubscribe(Disposable d) {
        showLoadingDialog();
        Log.e("DEBUG", "获取数据中");
    }

    @Override
    public void onNext(T result) {
        hideLoadingDialog();
        onSuccess(result);
    }

    @Override
    public void onError(Throwable e) {
        hideLoadingDialog();
        onFailure(ServerException.handleException(e).getMessage(), e instanceof ConnectException
                || e instanceof TimeoutException
                || e instanceof NetworkErrorException
                || e instanceof UnknownHostException);
    }

    /**
     * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
     * <p>
     * The {@link Observable} will not call this method if it calls {@link #onError}.
     * * 请求成功了才会调用 onComplete
     * onError 时不会调用
     */
    @Override
    public void onComplete() {
        hideLoadingDialog();
        Log.e("DEBUG", "获取数据完毕");
    }

    /**
     * 请求成功返回
     *
     * @param result 服务器返回数据
     */
    public abstract void onSuccess(T result);

    /**
     * 请求失败返回
     *
     * @param errMsg     失败信息
     * @param isNetError 是否是网络异常
     */
    public abstract void onFailure(String errMsg, boolean isNetError);

    /**
     * 显示 LoadingDialog
     */
    private void showLoadingDialog() {
        if (isShowDialog && mView != null) {
            mView.showLoading();
        }
    }

    /**
     * 隐藏 Loading
     */
    private void hideLoadingDialog() {
        if (isShowDialog && mView != null) {
            mView.hideLoading();
        }
    }

}

package com.yfy.app.netHttp;




import com.yfy.app.netHttp.result.Result;
import com.yfy.base.activity.BaseActivity;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Aj Liao
 */
public class ResHelpter {

    /**
     * 订阅帮助类 拦截errcode的处理
     *
     * @param context    用于记录subscription对象，方便Activity销毁的时候解除绑定
     * @param observable 事件源
     * @param onNext     返回成功处理
     * @param onError    错误处理
     * @param <T>        最后想要的结果类型
     */
    public static <T> void subscribe(BaseActivity context, Observable<? extends Result> observable, Action1<T> onNext, Action1<Throwable> onError) {
        Subscription subscription = observable
                .filter(new Func1<Result, Boolean>() {
                    @Override
                    public Boolean call(Result result) {
                        return result.errcode == 0 || result.errcode == 500; //0:代表正确，200:失败，100参数错误
                    }
                })
                .map(new Func1<Result, T>() {
                    @Override
                    public T call(Result result) {
                        return result.build();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        //添加到subscription,方便销毁时.unsubscribe();
        context.addToCompositeSubscription(subscription);
    }

    /**
     * 订阅帮助类  不拦截errcode的处理
     *
     * @param context    用于记录subscription对象，方便Activity销毁的时候解除绑定
     * @param observable 事件源
     * @param onNext     返回成功处理
     * @param onError    错误处理
     * @param <T>        最后想要的结果类型
     */
    public static <T> void subscribeResult(BaseActivity context, Observable<? extends Result> observable, Action1<T> onNext, Action1<Throwable> onError) {
        Subscription subscription = observable
                .map(new Func1<Result, T>() {
                    @Override
                    public T call(Result result) {
                        return result.build();
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        context.addToCompositeSubscription(subscription);
    }

    /**
     * 处理对返回结果不感兴趣的请求
     */
//    public static void noResultSubscribe(BaseActivity context, Observable<SellmanageResult> observable) {
//        subscribe(context, observable, new EmptyAction(), new EmptyAction<Throwable>());
//    }
}

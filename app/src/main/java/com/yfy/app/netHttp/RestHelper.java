package com.yfy.app.netHttp;




import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Aj Liao
 */
public class RestHelper {



    /**
     * 订阅帮助类  不拦截errcode的处理
     *
     * @param context    用于记录subscription对象，方便Activity销毁的时候解除绑定
     * @param observable 事件源
     * @param onNext     返回成功处理
     * @param onError    错误处理
     * @param <T>        最后想要的结果类型
     */
    public static <T> void subscribeResult(HttpPostActivity context, Observable<? extends String> observable, Action1<String> onNext, Action1<Throwable> onError) {



        Subscription subscription = observable
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String result) {
                        return result;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
        context.addToCompositeSubscription(subscription);
    }


}

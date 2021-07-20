package com.yfy.base.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.view.SQToolBar;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public abstract class BaseFragment extends Fragment {

    public static float mDensity = 0;
    public static int mScreenWidth = 0;
    public static int mScreenHeight = 0;
    public static Resources mResources;
    protected View contentView = null;
    protected LayoutInflater inflater = null;
    protected ViewGroup container = null;
    protected BaseActivity mActivity;
    @Nullable
    @BindView(R.id.sq_toolbar)
    protected SQToolBar toolbar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mDensity == 0) {
            initDensity();
        }
        if (mResources == null) {
            initRe();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inflater = inflater;
        this.container = container;
        this.mActivity =(BaseActivity) getActivity();
        onCreateView(savedInstanceState);

        return contentView;
    }




    public abstract void onCreateView(Bundle savedInstanceState);

    public void setContentView(@LayoutRes int layoutResID) {

        contentView = inflater.from(getActivity()).inflate(layoutResID, container, false);
        //初始化 ButterKnife
        ButterKnife.bind(this, contentView);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    /**
     * 显示一个ProgressDialog
     */
    protected ProgressDialog dialog = null;
    protected void showProgressDialog(String title, String message) {
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
        }
        dialog.setCancelable(false);
        if (title != null && !title.equals("")) {
            dialog.setTitle(title);
        }
        if (message != null && !message.equals("")) {
            dialog.setMessage(message);
        }
        dialog.show();
    }

    protected void showProgressDialog(String message) {
        showProgressDialog(null, message);
    }

    /**
     * 隐藏一个ProgressDialog
     */
    protected void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }
    /**

     */
    private void initDensity() {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
    }

    private void initRe() {
        mResources = getActivity().getResources();
    }

    protected void setOnClickListener(OnClickListener listener, View... views) {
        for (int i = 0; i < views.length; i++) {
            views[i].setOnClickListener(listener);
        }
    }

    protected void setOnClickListener(OnClickListener listener, View container,
                                      int... resIds) {
        for (int i = 0; i < resIds.length; i++) {
            container.findViewById(resIds[i]).setOnClickListener(listener);
        }
    }
}

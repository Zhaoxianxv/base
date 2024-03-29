package com.yfy.final_tag.permission;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;

import com.yfy.final_tag.data.TagFinal;


/**
 *  Created by yfy on 2017/9/21.
 *  基于工具 compile 'com.lovedise:permissiongen:0.0.6'//权限
 */

public class PermissionTools {

    /**
     *  照相摄像权限
     */
    public static void tryCameraPerm(Fragment mActivity){
        tryCameraPerm(mActivity.getActivity());
    }
    public static void tryCameraPerm(Activity mActivity){
        PermissionGen
                .with(mActivity)
                .addRequestCode(TagFinal.CAMERA)
                .permissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .request();
    }

    /**
     *  访问本地文件权限
     */
    public static void tryWRPerm(Fragment mActivity){
        tryCameraPerm(mActivity.getActivity());
    }
    public static void tryWRPerm(Activity mActivity){
        PermissionGen
                .needPermission(mActivity, TagFinal.PHOTO_ALBUM,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        });
    }
    /**
     *  访问拨号
     */
    public static void tryTellPhone(Fragment mActivity){
        tryCameraPerm(mActivity.getActivity());
    }
    public static void tryTellPhone(Activity mActivity){
        PermissionGen
                .with(mActivity)
                .addRequestCode(TagFinal.CALL_PHONE)
                .permissions( Manifest.permission.CALL_PHONE)
                .request();

    }
    public static void tryVoice(Activity mActivity){
        PermissionGen
                .needPermission(mActivity, TagFinal.VOICE_RECORD,
                        new String[]{
                                Manifest.permission.RECORD_AUDIO,
                                Manifest.permission.WAKE_LOCK,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        });

    }
}

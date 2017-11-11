package com.yftech.h5demo.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yftech.h5demo.R;


/**
 * Created by heshengfang on 2017/7/24.
 */

public class FragmentUtils {
    /**
     * 添加fragment
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addFragment(@NonNull FragmentManager fragmentManager,
                                   @NonNull Fragment fragment, int frameId) {

//        checkNotNull(fragmentManager);
//        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * 替换fragment，前面那个fragment不能后退，会被注销掉
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void replaceFragmentByNoStack(@NonNull FragmentManager fragmentManager,
                                                @NonNull Fragment fragment, int frameId) {

//        checkNotNull(fragmentManager);
//        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }


    /**
     * 替换fragment，前面那个fragment可以通过后退键后退。
     * 用这个方法的时候注意fragment的场景恢复，和转屏幕数据保护，不然不建议添加回退栈。
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void replaceFragmentByStack(@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {

//        checkNotNull(fragmentManager);
//        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * mode: 0-null , 1-leftexit-rightin , 2-rightexit-leftin
     *
     * @param fragmentManager
     * @param fragment
     * @param mode
     */
    public static void showFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int mode) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (mode) {
            case 0:
                break;
            case 1:
                transaction.setCustomAnimations(
                        R.anim.fragment_right_in,
                        R.anim.fragment_left_out);
                break;
            case 2:
                transaction.setCustomAnimations(
                        R.anim.fragment_left_in,
                        R.anim.fragment_right_out);
                break;
            default:
                break;
        }

        transaction.show(fragment);
        transaction.commit();
    }

    public static void hideFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int mode) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (mode) {
            case 0:
//                transaction.setCustomAnimations(
//                        R.anim.fragment_right_in,
//                        R.anim.fragment_left_out);
                break;
            case 1:
                transaction.setCustomAnimations(
                        R.anim.fragment_right_in,
                        R.anim.fragment_left_out);
                break;
            case 2:
                transaction.setCustomAnimations(
                        R.anim.fragment_left_in,
                        R.anim.fragment_right_out);
                break;
            default:
                break;
        }
        transaction.hide(fragment);
        transaction.commit();
    }
}

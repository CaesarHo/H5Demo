package com.yftech.h5demo.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yftech.h5demo.R;
import com.yftech.h5demo.activitys.AsyncActivity;
import com.yftech.h5demo.activitys.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Blank2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Blank2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Blank2Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private WebView mWeb = null;
    private LinearLayout ll_root;
    private EditText et_user;

    public Blank2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Blank2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Blank2Fragment newInstance(String param1, String param2) {
        Blank2Fragment fragment = new Blank2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank2, container, false);
        // Inflate the layout for this fragment
        initWebView(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    //初始化WebView
    private void initWebView(View view) {
        ll_root = (LinearLayout)view. findViewById(R.id.ll_root);
        et_user = (EditText)view. findViewById(R.id.et_user);

        //动态创建一个WebView对象并添加到LinearLayout中
        mWeb = new WebView(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWeb.setLayoutParams(params);
        ll_root.addView(mWeb);
        //不跳转到其他浏览器
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mWeb.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //加载本地html文件
        mWeb.loadUrl("file:///android_asset/JavaAndJavaScriptCall.html");
        mWeb.addJavascriptInterface(new JSInterface(),"Android");
    }

    //按钮的点击事件
    public void click(View view){
        //java调用JS方法
        mWeb.loadUrl("javascript:javaCallJs(" + "'" + et_user.getText().toString()+"'"+")");
    }

    public void clickAsync(View  view){
        Intent intent = new Intent(getActivity(),AsyncActivity.class);
        startActivity(intent);
    }
    public  void clickMap(View view){
        Intent intent = new Intent(getActivity(), com.yftech.h5demo.map.MainActivity.class);
        startActivity(intent);
    }

    //在页面销毁的时候将webView移除
    @Override
    public void onDestroy() {
        super.onDestroy();
        ll_root.removeView(mWeb);
        mWeb.stopLoading();
        mWeb.removeAllViews();
        mWeb.destroy();
        mWeb = null;
    }

    private class JSInterface {
        //JS需要调用的方法
        @JavascriptInterface
        public void showToast(String arg){
            Toast.makeText(getActivity(),arg,Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

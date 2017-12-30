package com.huoshan.api.huoshan.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.MainActivity;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.mine.setup.SetUpActivity;
import com.huoshan.api.huoshan.utils.OkHttp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lenovo on 2017/12/29.
 */

public class MineFragment  extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView mAdd;
    /**
     * 主人
     */
    private TextView mTvTitle;
    private ImageView mShare;
    private ImageView mShezhi;
    private RelativeLayout mTi;
    private ImageView mHead;
    /**
     * 0
     */
    private TextView mFensi;
    /**
     * 0
     */
    private TextView mGuanzhu;
    /**
     * 4
     */
    private TextView mShuoli;
    /**
     * 编辑
     */

    /**
     * 火力/钻石
     */

    /**
     * 有趣的个性签名可以吸引更多的粉丝...
     */
    private TextView mQianming;
    private ViewPager mViewpager;
    /**
     * 编辑
     */
    private Button mBianji;
    /**
     * 火力/钻石
     */
    private Button mHuoli;
    private PopupWindow mPopWindow;
    /**
     * 邀请好友加入火山
     */
    private TextView mTv;
    private ImageView mCha;
    private RelativeLayout mMa;
    /**
     * 与好友一起分享生活精彩瞬间
     */
    private TextView mF;
    /**
     * 邀请好友
     */
    private Button mYaoqing;
    private LinearLayout ll;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment,container,false);
        initView(view);
        //设置一张图片
        initListener();
        return view;

    }
    private void initView(View view) {
        mAdd = (ImageView) view.findViewById(R.id.mf_add);
        mTvTitle = (TextView) view.findViewById(R.id.mf__title);
        mShare = (ImageView) view.findViewById(R.id.mf_share);
        mShezhi = (ImageView) view.findViewById(R.id.mf_shezhi);
        mTi = (RelativeLayout) view.findViewById(R.id.mf_ti);
        mHead = (ImageView) view.findViewById(R.id.mf_head);
        mFensi = (TextView) view.findViewById(R.id.mf_fensi);
        mGuanzhu = (TextView) view.findViewById(R.id.mf_guanzhu);
        ll = view.findViewById(R.id.ll);

        mQianming = (TextView) view.findViewById(R.id.qianming);
        mViewpager = (ViewPager) view.findViewById(R.id.viewpager);
        mAdd.setOnClickListener(this);
        mTvTitle.setOnClickListener(this);
        mShare.setOnClickListener(this);
        mShezhi.setOnClickListener(this);
        mTi.setOnClickListener(this);
        mHead.setOnClickListener(this);
        mFensi.setOnClickListener(this);
        mGuanzhu.setOnClickListener(this);
        mShuoli = (TextView) view.findViewById(R.id.mf_shuoli);
        mBianji = (Button) view.findViewById(R.id.mf_bianji);
        mBianji.setOnClickListener(this);
        mHuoli = (Button) view.findViewById(R.id.mf_huoli);
        mHuoli.setOnClickListener(this);
        mQianming.setOnClickListener(this);
        mViewpager.setOnClickListener(this);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            mHead.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }
    private void initListener() {
        mHead.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.mf_add:
                break;
            case R.id.mf__title:
                break;
            case R.id.mf_share:
                break;
            case R.id.mf_shezhi:
                //点击设置--跳转到设置的界面
                Intent intent = new Intent(getActivity(), SetUpActivity.class);
                startActivity(intent);
                break;
            case R.id.mf_ti:
                break;
            case R.id.mf_head:
                //上传头像
                //点击退出弹出框
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                final AlertDialog dialog = builder.create();
                View view = View.inflate(getContext(), R.layout.head_img, null);
                TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
                TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
                tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
                    @Override
                    public void onClick(View v) {
                        // 激活系统图库，选择一张图片
                        Intent intent1 = new Intent(Intent.ACTION_PICK);
                        intent1.setType("image/*");
                        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                        startActivityForResult(intent1, PHOTO_REQUEST_GALLERY);
                        dialog.dismiss();
                    }
                });
                tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
                    @Override
                    public void onClick(View v) {
                        // 激活相机
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        // 判断存储卡是否可以用，可用进行存储
                        if (hasSdcard()) {
                            tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                            // 从文件中创建uri
                            Uri uri = Uri.fromFile(tempFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        }
                        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                        startActivityForResult(intent, PHOTO_REQUEST_CAREMA);// 采用ForResult打开
                        dialog.dismiss();
                    }
                });
                dialog.setView(view);
                dialog.show();
                break;
            case R.id.mf_fensi:
                break;
            case R.id.mf_guanzhu:
                break;
            case R.id.mf_bianji:
                break;
            case R.id.mf_huoli:
                break;
            case R.id.qianming:
                break;
            case R.id.viewpager:
                break;

        }
    }
    /*
   * 判断sdcard是否被挂载
   */
    private boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * 剪切图片
    */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                /**
                 * 获得图片
                 */
                mHead.setImageBitmap(bitmap);
                //保存到SharedPreferences
                saveBitmapToSharedPreferences(bitmap);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //保存图片到SharedPreferences
    private void saveBitmapToSharedPreferences(Bitmap bitmap) {
        // Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("testSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", imageString);
        editor.commit();
        //上传头像
        setImgByStr(imageString,"");
    }


    /**
     * 上传头像       此处使用用的OKHttp post请求上传的图片
     * @param imgStr
     * @param imgName
     */
    public  void setImgByStr(String imgStr, String imgName) {
        String url = "http://appserver.1035.mobi/MobiSoft/User_upLogo";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "11460047");// 11459832
        params.put("data", imgStr);
        OkHttp.postAsync(url, params, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {
                Log.i("上传失败", "失败" + request.toString() + e.toString());
            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Log.i("上传成功", result);
            }
        });
    }

    //从SharedPreferences获取图片
    private void getBitmapFromSharedPreferences(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("image", "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);
        if(byteArray.length==0){
            mHead.setImageResource(R.mipmap.ic_launcher);
        }else{
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);

            //第三步:利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
            mHead.setImageBitmap(bitmap);
        }

    }

}

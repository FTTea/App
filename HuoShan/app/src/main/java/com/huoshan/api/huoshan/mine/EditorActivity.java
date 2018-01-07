package com.huoshan.api.huoshan.mine;

import android.app.DatePickerDialog;
import android.content.Context;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.utils.OkHttp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

public class EditorActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mEdacBack;
    /**
     * 保存
     */
    private TextView mEdacCun;
    private ImageView mEdacImg;
    /**
     * 点击可以更换头像
     */
    private TextView mEdacHuan;
    private EditText mNicheng;
    private EditText mNum;
    private LinearLayout ll;
    /**
     * 点击复制
     */
    private Button mFuzhi;
    /**
     * 点击选择
     */
    private EditText mXuanSex;
    /**
     * 点击选择
     */
    private EditText mXuanAge;
    private LinearLayout mGexing;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private Calendar mCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        initView();
        //查询数据库
        //查询数据库
        SharedPreferences sp = getSharedPreferences("user", Context.MODE_PRIVATE);
        String string = sp.getString("name", "你拥我暖");
        //返回上一个界面
        mEdacBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 EditorActivity.this.finish();
            }
        });
        //设置一张图片
        initListener();
        //设置值
        mNicheng.setText(string);
        //点击选择性别
        mXuanSex.setCursorVisible(false);
        mXuanAge.setCursorVisible(false);
        mNicheng.setCursorVisible(false);

        mXuanSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(EditorActivity.this);
                  builder.setMessage("女")
                          .setMessage("男")
                          .setNegativeButton("取消",null )
                          .create();
                  builder.show();
                  //判断选择的是男还是女
            }
        });
        //点击保存
        mEdacCun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditorActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
            }
        });
        //点击选择生日
        mXuanAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar = Calendar.getInstance();
                DatePickerDialog pickerDialog = new DatePickerDialog(EditorActivity.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        mCalendar.set(year, month, day);//将点击获得的年月日获取到calendar中。
                        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");//转型
                        Toast.makeText(getApplicationContext(), format.format(mCalendar.getTime()), Toast.LENGTH_LONG).show();
                        mXuanAge.setText(format.format(mCalendar.getTime()));
                    }
                }, mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH), mCalendar.get(Calendar.DAY_OF_MONTH));
                pickerDialog.show();
            }
        });
        //点击下面的个性签名布局,实现上面的布局隐藏
        mGexing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll.setVisibility(View.GONE);
            }
        });
        //点击更换头像
        mEdacImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //上传头像
                //点击退出弹出框
                AlertDialog.Builder builder = new AlertDialog.Builder(EditorActivity.this);
                final AlertDialog dialog = builder.create();
                View view = View.inflate(EditorActivity.this, R.layout.head_img, null);
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
            }
        });

    }

    private void initView() {
        ll = findViewById(R.id.line);
        mEdacBack = (ImageView) findViewById(R.id.edac_back);
        mEdacCun = (TextView) findViewById(R.id.edac_cun);
        mEdacImg = (ImageView) findViewById(R.id.edac_img);
        mEdacHuan = (TextView) findViewById(R.id.edac_huan);
        mNicheng = (EditText) findViewById(R.id.nicheng);
        mNum = (EditText) findViewById(R.id.num);
        mFuzhi = (Button) findViewById(R.id.fuzhi);
        mFuzhi.setOnClickListener(this);
        mXuanSex = (EditText) findViewById(R.id.xuan_sex);
        mXuanAge = (EditText) findViewById(R.id.xuan_age);
        mGexing = (LinearLayout) findViewById(R.id.gexing);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");// 从SD卡中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            mEdacImg.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
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
                mEdacImg.setImageBitmap(bitmap);
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
        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
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
        SharedPreferences sharedPreferences=getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("image", "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray=Base64.decode(imageString, Base64.DEFAULT);
        if(byteArray.length==0){
            mEdacImg.setImageResource(R.mipmap.ic_launcher);
        }else{
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);

            //第三步:利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
            mEdacImg.setImageBitmap(bitmap);
        }

    }
    private void initListener() {
        mEdacImg.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.fuzhi:
                //点击复制
                String trim = mNum.getText().toString().trim();
                Toast.makeText(EditorActivity.this,"火山号已复制到剪切板",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

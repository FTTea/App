package com.huoshan.api.huoshan.mine;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.huoshan.api.huoshan.R;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends AppCompatActivity {
    private ContentResolver cr;
    private List<String> list = new ArrayList<>();
    private ListView  lv;
    private TextView addl_sum;
    private ImageView addl_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        lv = findViewById(R.id.addl_lv);
        addl_sum = findViewById(R.id.addl_sum);
        addl_back = findViewById(R.id.addl_back);
        //返回
        addl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressListActivity.this.finish();
            }
        });
        Uri uri = Uri.parse("content://Contacts/people");
        startActivityForResult(new Intent(
                Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);

        //联系人名展示
        ContentResolver cr = getContentResolver();
        lv = findViewById(R.id.addl_lv);
        Uri uri1=Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor=cr.query(uri,null,null,null,null);
        SimpleCursorAdapter simpleCursorAdapter = new android.widget.SimpleCursorAdapter(this, R.layout.item_list, cursor, new String[]{"_id", "display_name"}, new int[]{R.id.tv_item_list_id, R.id.tv_item_list_name});
        lv.setAdapter(simpleCursorAdapter);
        //创建适配器

        //查看一共有多少的人数
        /*int columnCount = cursor.getColumnCount();
        addl_sum.setText("待关注好友:"+columnCount);*/
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 0:
                if(data==null)
                {
                    return;
                }
                //处理返回的data,获取选择的联系人信息
                Uri uri=data.getData();
                String[] contacts=getPhoneContacts(uri);
                //tv_main_name.setText(contacts[0]);
                //tv_main_phonenumber.setText(contacts[1]);
                String contact = contacts[1];
                //添加到集合
                //展示效果

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private String[] getPhoneContacts(Uri uri){
        String[] contact=new String[2];
        //得到ContentResolver对象
         cr = getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor=cr.query(uri,null,null,null,null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex=cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            contact[0]=cursor.getString(nameFieldColumnIndex);
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            if(phone != null){
                phone.moveToFirst();
                contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            phone.close();
            cursor.close();
        }
        else
        {
            return null;
        }
        return contact;
    }
    public void getContacts(View view){
        //获取联系人raw_contacts
        //id  name
        Uri uri=Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor=cr.query(uri,null,null,null,null);
        while (cursor.moveToNext()){
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String name=cursor.getString(cursor.getColumnIndex("display_name"));
            Log.i("test",id+" "+name);

            //继续获取相对应的联系人的数据（电话号码）
            Uri uriData=Uri.parse("content://com.android.contacts/raw_contacts/"+id+"/data");
            Cursor cursorData=cr.query(uriData,null,null,null,null);
            while(cursorData.moveToNext()){
                String data1=cursorData.getString(cursorData.getColumnIndex("data1"));
                //int type=cursorData.getInt(cursorData.getColumnIndex("mimetype_id"));
                String type=cursorData.getString(cursorData.getColumnIndex("mimetype"));
                if("vnd.android.cursor.item/phone_v2".equals(type)){
                    Log.i("test","        "+data1+":"+type);
                }
            }
        }
    }
}

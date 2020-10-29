package com.example.administrator.newssystem;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leon.lib.settingview.LSettingItem;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private MsgFragment    fragment1;
    private FriendFragment fragment2;
    private MyFragment     fragment3;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    //private FragmentManager manager;
    //private FragmentTransaction transaction;

    private TextView tx1,tv_name;
    private ListView Lv = null;

    private ImageView mImageView;

    private WebView webView;


    /**
     * 新闻列表请求接口
     */
    public static final String URL = "http://v.juhe.cn/toutiao/index?type=top&key=a1a755458cc22f129942b34904feb820";
/**
     * ListView对象
     */
    private ListView listView;
    /**
     * 新闻集合对象
     */
    private List<NewsData> datas;
    /**
     * 自定义的Adapter对象
     */
    private  MyAdapter adapter;


    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 扫描跳转按钮
     */
    private Button sys=null;



    //----------------图片轮播开始-------------------//
    private ViewPager mViewPaper;
    private List<ImageView> images;
    private List<View> dots;
    private int currentItem;
    //记录上一次点的位置
    private int oldPosition = 0;
    //存放图片的id
    private int[] imageIds = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.d,
            R.drawable.e
    };
    //存放图片的标题
    private String[]  titles = new String[]{
            "今年过节不收礼，收礼还收脑白金",
            "我要O泡 给我O泡给我O泡 ～O泡果奶OOO",
            "杯装奶茶开创者,一年卖出十二亿多杯,连起来可绕地球三圈",
            "我的地盘听我的",
            "为什么追我? 我要急支糖浆！"
    };
    private TextView title;
    private ViewPagerAdapter adapter2;
    private ScheduledExecutorService scheduledExecutorService;
    //-------------------------图片轮播结束----------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //全屏显示，显示时间和电量
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) this.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);//支持脚本
        webView.loadUrl("https://wx.bbtzx.cn/app/index.php?i=2&c=entry&do=index&m=tiger_newhu&dluid=231");
        webView.setWebViewClient(new MyWebViewClient());



        //设置切换Fragment
        radioGroup = (RadioGroup)findViewById(R.id.radiogroup);
        RadioGroupList radigGroupList = new RadioGroupList();
        radioGroup.setOnCheckedChangeListener(radigGroupList);

        //设置默认按钮为选中状态
        radioButton =(RadioButton) findViewById(R.id.btn_0);
        radioButton.setChecked(true);


        //开始处理Fragment
        fragment1 = new MsgFragment();
        fragment2 = new FriendFragment();
        fragment3 = new MyFragment();

        findViewById(R.id.fragment_msg).setVisibility(View.INVISIBLE);
        findViewById(R.id.fragment_friend).setVisibility(View.INVISIBLE);
        findViewById(R.id.fragment_look).setVisibility(View.INVISIBLE);
        findViewById(R.id.fragment_my).setVisibility(View.INVISIBLE);

        mViewPaper = (ViewPager) findViewById(R.id.vp);



        images = new ArrayList<ImageView>();
        for(int i = 0; i < imageIds.length; i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[i]);
            images.add(imageView);
        }
        //显示的小点
        dots = new ArrayList<View>();
        dots.add(findViewById(R.id.dot_0));
        dots.add(findViewById(R.id.dot_1));
        dots.add(findViewById(R.id.dot_2));
        dots.add(findViewById(R.id.dot_3));
        dots.add(findViewById(R.id.dot_4));
        title = (TextView) findViewById(R.id.title);
        title.setText(titles[0]);
        adapter2 = new ViewPagerAdapter();
        mViewPaper.setAdapter(adapter2);
        mViewPaper.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                dots.get(position).setBackgroundResource(R.drawable.baidian);
                dots.get(oldPosition).setBackgroundResource(R.drawable.heidian);
                oldPosition = position;
                currentItem = position;
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });



        mImageView=(ImageView)findViewById(R.id.mImageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在当前方法onClick中监听

//                Toast.makeText(Listener.this,"点击成功",Toast.LENGTH_SHORT).show();

                Intent intent=new Intent();
                intent.setClass(MainActivity.this,loginActivity.class);
                startActivity(intent);

            }
        });
        findViewById(R.id.fragment_msg).setVisibility(View.VISIBLE);//显示首页

        LSettingItem sc =(LSettingItem)findViewById(R.id.sc);
        sc.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Toast.makeText(MainActivity.this,"待开发",Toast.LENGTH_SHORT).show();
            }
        });
        LSettingItem about =(LSettingItem)findViewById(R.id.about);
        about.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,aboutActivity.class);
                startActivity(intent);
            }
        });
        LSettingItem shebei =(LSettingItem)findViewById(R.id.shebei);
        shebei.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,shebeiActivity.class);
                startActivity(intent);
            }
        });

        LSettingItem sys =(LSettingItem)findViewById(R.id.sys);
        sys.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });


        LSettingItem exit =(LSettingItem)findViewById(R.id.exit);
        exit.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click() {
                AlertDialog.Builder exitBuilder = new AlertDialog.Builder(MainActivity.this);
                exitBuilder.setCancelable(false);
                exitBuilder.setMessage("小可爱你真的要离我而去了吗...呜呜呜...");
                exitBuilder.setPositiveButton("狠心离开", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                });
                exitBuilder.setNegativeButton("留下", null);
                exitBuilder.create().show();

            }
        });

//        sys.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
//                startActivityForResult(intent,REQUEST_CODE);
//            }
//        });



        listView = (ListView) this.findViewById(R.id.listView1);
        datas = new ArrayList<NewsData>();
        getDatas(URL);
        /**
         * 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)
         */
        adapter = new MyAdapter(this, datas);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long arg3) {

                /**
                 * 创建一个意图
                 */
                Intent intent = new Intent(MainActivity.this,NewsInfoActivity.class);

                /**
                 * 在datas中通过点击的位置position通过get()方法获得具体某个新闻
                 * 的数据然后通过Intent的putExtra()传递到NewsInfoActivity中
                 */
                intent.putExtra("newsTitle", datas.get(position).getNewsTitle());
                intent.putExtra("newsDate", datas.get(position).getNewsDate());
//                intent.putExtra("newsImgUrl", datas.get(position).getNewsImgUrl());
                intent.putExtra("newsUrl", datas.get(position).getNewsUrl());

                // 这里是获取到点击页面的网址
                MainActivity.this.startActivity(intent);//启动Activity

            }
        });




        tx1 = (TextView)findViewById(R.id.tx1);
        Intent myIntent = getIntent();
        if (myIntent != null) {
            tx1.setText(myIntent.getStringExtra("senttext"));
        }







    }



    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup view, int position, Object object) {
            // TODO Auto-generated method stub
//			super.destroyItem(container, position, object);
//			view.removeView(view.getChildAt(position));
//			view.removeViewAt(position);
            view.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            // TODO Auto-generated method stub
            view.addView(images.get(position));
            return images.get(position);
        }

    }
    /**
     * 利用线程池定时执行动画轮播
     */
    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(
                new ViewPageTask(),
                2,
                2,
                TimeUnit.SECONDS);
    }
    /**
     * 图片轮播任务
     *
     */
    private class ViewPageTask implements Runnable{

        @Override
        public void run() {
            currentItem = (currentItem + 1) % imageIds.length;
            mHandler.sendEmptyMessage(0);
        }
    }
    /**
     * 接收子线程传递过来的数据
     */
    private Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            mViewPaper.setCurrentItem(currentItem);
        };
    };
    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        if(scheduledExecutorService != null){
            scheduledExecutorService.shutdown();
            scheduledExecutorService = null;
        }
    }






    public class RadioGroupList implements RadioGroup.OnCheckedChangeListener
    {

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId)
        {
            if(group.getId() == R.id.radiogroup)
            {
                switch (checkedId)
                {
                    case R.id.btn_0:
                        findViewById(R.id.fragment_msg).setVisibility(View.VISIBLE);
                        findViewById(R.id.fragment_friend).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_look).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_my).setVisibility(View.INVISIBLE);

                        break;
                    case R.id.btn_1:
                        findViewById(R.id.fragment_msg).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_friend).setVisibility(View.VISIBLE );
                        findViewById(R.id.fragment_look).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_my).setVisibility(View.INVISIBLE);

                        break;
                    case R.id.btn_2:
                        findViewById(R.id.fragment_msg).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_friend).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_look).setVisibility(View.VISIBLE);
                        findViewById(R.id.fragment_my).setVisibility(View.INVISIBLE );

                        break;
                    case R.id.btn_3:
                        findViewById(R.id.fragment_msg).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_friend).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_look).setVisibility(View.INVISIBLE);
                        findViewById(R.id.fragment_my).setVisibility(View.VISIBLE );

                        break;
                    default :
                        break;
                }
            }
        }
    }




    class MyWebViewClient extends WebViewClient {//隐藏导航的网址和进度条
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);//这句很重要，否则打开的网页不能继续浏览
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//重写onKeyDown方法实现返回之前浏览的网页，shouldOverrideUrlLoading方法致使点击返回按钮时直接退出软件
        if(keyCode== KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }










    public void getDatas(String url){

        final RequestQueue mQueue = Volley.newRequestQueue(this);

        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {

                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("data");

                            for (int i = 0; i <jsonArray.length() ; i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                NewsData data = new NewsData();
                                data.setNewsTitle(item.getString("title"));
                                data.setNewsDate(item.getString("date"));
//                                data.setNewsImgUrl(item.getString("thumbnail_pic_s"));
                                data.setNewsUrl(item.getString("url"));
                                datas.add(data);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        /**
                         * 请求成功后为ListView设置Adapter
                         */
                        listView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
        );

        mQueue.add(stringRequest);

    }


    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;}
                if (bundle.getInt(CodeUtils.RESULT_TYPE)
                        == CodeUtils.RESULT_SUCCESS) {
                    String result =
                            bundle.getString(CodeUtils.RESULT_STRING);
                    //用默认浏览器打开扫描得到的地址
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(result.toString());
                    intent.setData(content_url);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE)
                        == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this,
                            "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


}




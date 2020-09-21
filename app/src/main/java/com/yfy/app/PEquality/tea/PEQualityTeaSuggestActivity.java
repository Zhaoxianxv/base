package com.yfy.app.PEquality.tea;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.yfy.app.PEquality.adapter.PEQualityTeaSuggestAdapter;
import com.yfy.app.album.AlbumOneActivity;
import com.yfy.app.bean.BaseRes;
import com.yfy.app.bean.KeyValue;
import com.yfy.app.net.ReqBody;
import com.yfy.app.net.ReqEnv;
import com.yfy.app.net.ResBody;
import com.yfy.app.net.ResEnv;
import com.yfy.app.net.RetrofitGenerator;
import com.yfy.app.net.SaveImgReq;
import com.yfy.app.net.base.UserGetTermListReq;
import com.yfy.base.R;
import com.yfy.base.activity.BaseActivity;
import com.yfy.final_tag.AppLess;
import com.yfy.final_tag.Logger;
import com.yfy.final_tag.stringtool.StringJudge;
import com.yfy.final_tag.stringtool.StringUtils;
import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.FileCamera;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.glide.ZoomImage;
import com.yfy.final_tag.permission.PermissionFail;
import com.yfy.final_tag.permission.PermissionGen;
import com.yfy.final_tag.permission.PermissionSuccess;
import com.yfy.view.SQToolBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PEQualityTeaSuggestActivity extends BaseActivity {
    private static final String TAG = PEQualityTeaSuggestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_recycler_view);
        initRecycler();
        getData();
    }


    private String title,type;
    private void getData(){
        title=getIntent().getStringExtra(Base.title);
        type=getIntent().getStringExtra(Base.type);
        initSQToolbar(title);
        initView(type);
    }
    private void initSQToolbar(String title) {
        assert toolbar!=null;
        toolbar.setTitle(title);
        toolbar.addMenuText(TagFinal.ONE_INT,R.string.submit);
        toolbar.setOnMenuClickListener(new SQToolBar.OnMenuClickListener() {
            @Override
            public void onClick(View view, int position) {
                finish();
            }
        });

    }




    public List<KeyValue> keyValueAdapterData =new ArrayList<>();
    public PEQualityTeaSuggestAdapter adapter;
    public RecyclerView recyclerView;
    public void initRecycler(){

        recyclerView =  findViewById(R.id.public_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
//        recyclerView.addItemDecoration(new RecycleViewDivider(
//                mActivity,
//                LinearLayoutManager.HORIZONTAL,
//                1,
//                getResources().getColor(R.color.gray)));
        adapter=new PEQualityTeaSuggestAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setSealChoice(new PEQualityTeaSuggestAdapter.SealChoice() {
            @Override
            public void refresh(KeyValue bean, int pos_index) {
                position_index=pos_index;
                multi_bean=bean;
            }
        });

    }


    private KeyValue multi_bean;
    private int position_index;

    private void initView(String type){
        switch (type){
            case "PERecipeActivity":
                setRecipe();
                break;
            case "PEQualityTeaMainActivity":
                setSuggest();
                break;
            case "PEQualitySkillsActivity":
                setSkillsAdapterData();
                break;
            case "PEQualityAttenListActivity":
                setAttenData();
                break;
            case "PEQualityHomeworkActivity":
                setHomework();
                break;
            case "stamina":
                setStamina();
                break;
            case "PEQualityStandardListActivity":
                setStandard();
                break;
            case "PEQualityAttitudeActivity":
                setAttitudeData();
                break;
            case "show":
                setShow();
                break;
        }
    }

    private void setShow(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);

        one.setTitle(title);
        one.setValue("");
        one.setKey("请输入分数");




        keyValueAdapterData.add(one);

        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setAttitudeData(){
        keyValueAdapterData.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_DATE);
        one.setTitle("选择日期");
        one.setRight_value("");
        one.setRight_name("");
        one.setRight_key("未选择");


        KeyValue two=new KeyValue(TagFinal.TYPE_SELECT_SINGLE);
        two.setTitle("选择课时");
        two.setRight_value("");
        two.setRight_name("");
        two.setRight_key("未选择");
        two.setContent("上午·第1节,上午·第2节,上午·第3节,上午·第4节,下午·第1节,下午·第2节,下午·第3节");
        two.setGroup_id(TagFinal.FALSE);

        KeyValue three=new KeyValue(TagFinal.TYPE_SELECT_SINGLE);
        three.setTitle("扣分项目");
        three.setRight_value("");
        three.setRight_name("");
        three.setRight_key("未选择");
        three.setContent("大课间体育活动违纪或缺席,旷课,迟到,早退,违反课堂纪律,着装不符合运动要求,其他");
        three.setGroup_id(TagFinal.FALSE);




        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        keyValueAdapterData.add(three);


        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setStandard(){
        keyValueAdapterData.clear();

        KeyValue one=new KeyValue("请输入分数","","",TagFinal.TYPE_TXT_EDIT);
        one.setTitle("体重指数");
        KeyValue two=new KeyValue("请输入分数","","",TagFinal.TYPE_TXT_EDIT);
        two.setTitle("肺活量");

        KeyValue three=new KeyValue("请输入分数","","",TagFinal.TYPE_TXT_EDIT);
        three.setTitle("50米");

        KeyValue four=new KeyValue("请输入分数","","",TagFinal.TYPE_TXT_EDIT);
        four.setTitle("坐位体前屈");

        KeyValue five=new KeyValue("请输入分数","","",TagFinal.TYPE_TXT_EDIT);
        five.setTitle("一分钟跳绳");

        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        keyValueAdapterData.add(three);
        keyValueAdapterData.add(four);
        keyValueAdapterData.add(five);

        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setStamina(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);

        one.setTitle(title);
        one.setValue("");
        one.setKey("请输入分数");




        keyValueAdapterData.add(one);

        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setHomework(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        KeyValue three=new KeyValue(TagFinal.TYPE_TXT_EDIT);

        one.setTitle("长跑");
        one.setValue("");
        one.setKey("请输入分数");

        two.setTitle("短跑");
        two.setValue("");
        two.setKey("请输入分数");

        three.setTitle("总分");
        three.setValue("");
        three.setKey("88");
        three.setIs_edit(TagFinal.FALSE);


        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        keyValueAdapterData.add(two);
        keyValueAdapterData.add(two);
        keyValueAdapterData.add(three);
        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setAttenData(){
        keyValueAdapterData.clear();

        KeyValue one=new KeyValue(TagFinal.TYPE_DATE);
        one.setTitle("选择日期");
        one.setRight_value("");
        one.setRight_name("");
        one.setRight_key("未选择");


        KeyValue two=new KeyValue(TagFinal.TYPE_SELECT_SINGLE);
        two.setTitle("选择课时");
        two.setRight_value("");
        two.setRight_name("");
        two.setRight_key("未选择");
        two.setContent("上午·第1节,上午·第2节,上午·第3节,上午·第4节,下午·第1节,下午·第2节,下午·第3节");
        two.setGroup_id(TagFinal.FALSE);

        KeyValue three=new KeyValue(TagFinal.TYPE_LONG_TXT_EDIT);
        three.setTitle("请假因由");
        three.setKey("点击输入请假因由(限制字数20字)");
        three.setValue("");

        KeyValue four=new KeyValue(TagFinal.TYPE_IMAGE);
        four.setTitle("图片");
        four.setListValue(new ArrayList<String>());




        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        keyValueAdapterData.add(three);
        keyValueAdapterData.add(four);


        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setSkillsAdapterData(){
        keyValueAdapterData.clear();
        KeyValue two=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        two.setTitle("球类");
        two.setKey("未打分");
        two.setValue("88");

        KeyValue three=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        three.setTitle("体操 ");
        three.setKey("未打分");
        three.setValue("88");

        KeyValue four=new KeyValue(TagFinal.TYPE_SELECT_SINGLE);
        four.setTitle("选考项");
        four.setContent("田径,民传,新兴体育");
        four.setRight_key("未选择");
        four.setRight_value("");
        four.setGroup_id(TagFinal.TRUE);

        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        one.setTitle("总分");
        one.setValue("88");
        one.setIs_edit(TagFinal.FALSE);

        KeyValue five=new KeyValue(TagFinal.TYPE_LONG_TXT_EDIT);
        five.setTitle("运动技能简评");
        five.setKey("未评论");
        five.setValue("");

        keyValueAdapterData.add(two);
        keyValueAdapterData.add(three);
        keyValueAdapterData.add(four);
//        keyValueAdapterData.add(one);
        keyValueAdapterData.add(five);

        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setSuggest(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        KeyValue two=new KeyValue(TagFinal.TYPE_LONG_TXT_EDIT);

        one.setTitle("标题");
        one.setValue("膳食建议标题");
        one.setKey("请输入标题");
        two.setTitle("内容");
        two.setValue("五谷杂粮，如莜麦面、荞麦面、燕麦片、玉米面、紫山药等富含维生素B、多种微量元素及食物纤维，以低糖，低淀粉的食物或者粗粮以及蔬菜等做主食。");

        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }
    private void setRecipe(){
        keyValueAdapterData.clear();
        KeyValue one=new KeyValue(TagFinal.TYPE_TXT_EDIT);
        KeyValue two=new KeyValue(TagFinal.TYPE_LONG_TXT_EDIT);

        one.setTitle("项目名");
        one.setValue("长跑");
        one.setKey("请输入项目名");
        two.setTitle("处方建议");
        two.setValue("建议加强体能锻炼");

        keyValueAdapterData.add(one);
        keyValueAdapterData.add(two);
        adapter.setDataList(keyValueAdapterData);
        adapter.setLoadState(TagFinal.LOADING_END);
    }









    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TagFinal.CAMERA:
                    mtask=new MyAsyncTask();
                    mtask.execute(FileCamera.photo_camera);
                    break;
                case TagFinal.PHOTO_ALBUM:
                    ArrayList<Photo> photo_a=data.getParcelableArrayListExtra(TagFinal.ALBUM_TAG);
                    if (photo_a==null)return;
                    if (photo_a.size()==0)return;
                    Logger.e(photo_a.get(0).getPath());
                    List<String> list_a=new ArrayList<>();
                    for (Photo photo:photo_a){
                        if (photo==null) continue;
                        list_a.add(photo.getPath());
                    }
                    mtask=new MyAsyncTask();
                    mtask.execute(StringUtils.arraysToListString(list_a));
                    break;



            }
        }
    }

    /**
     * ----------------------------retrofit-----------------------
     */
    public void getTerm() {
        ReqEnv env = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        UserGetTermListReq req = new UserGetTermListReq();
        //获取参数
        reqBody.userGetTermListReq = req;
        env.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().get_term_list(env);
        call.enqueue(this);
    }


    private void saveImg(String flie_string){
        ReqEnv reqEnv = new ReqEnv();
        ReqBody reqBody = new ReqBody();
        SaveImgReq req = new SaveImgReq();
        //获取参数
        req.setImage_file(flie_string);
        req.setFileext("jpg");
        reqBody.saveImgReq = req;
        reqEnv.body = reqBody;
        Call<ResEnv> call = RetrofitGenerator.getWeatherInterfaceApi().base_save_img(reqEnv);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<ResEnv> call, Response<ResEnv> response) {
        if (!isActivity())return;
        dismissProgressDialog();
        List<String> names=StringUtils.listToStringSplitCharacters(call.request().headers().toString().trim(), "/");
        String name=names.get(names.size()-1);
        ResEnv respEnvelope = response.body();
        if (respEnvelope != null) {
            ResBody b=respEnvelope.body;
            if (b.userGetTermListRes !=null){
                String result=b.userGetTermListRes.result;
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result, BaseRes.class);
                if (res.getResult().equals("true")){
                }else{
                    toastShow("error");
                }
            }
            if (b.saveImgRes!=null) {
                String result = b.saveImgRes.result;
                Logger.e(StringUtils.getTextJoint("%1$s:\n%2$s",name,result));
                BaseRes res=gson.fromJson(result,BaseRes.class );
                if (res.getResult().equalsIgnoreCase(TagFinal.TRUE)){
                    List<String> list_c=multi_bean.getListValue();
                    list_c.add(Base.RETROFIT_URI+res.getImg());
                    adapter.notifyItemChanged(position_index,multi_bean);
                }else{
                    toastShow(res.getError_code());
                }
                if (num==1){
                    dismissProgressDialog();
                }else{
                    num--;
                }
            }
        }else{
            try {
                String s=response.errorBody().string();
                Logger.e(StringUtils.stringToGetTextJoint("%1$s:%2$d:%3$s",name,response.code(),s));
            } catch (IOException e) {
                Logger.e("onResponse: IOException");
                e.printStackTrace();
            }
            toastShow(StringUtils.stringToGetTextJoint("数据错误:%1$d",response.code()));
        }

    }

    @Override
    public void onFailure(Call<ResEnv> call, Throwable t) {
        if (!isActivity())return;
        toastShow(R.string.fail_do_not);
        Logger.e("onFailure  :"+call.request().headers().toString());
        dismissProgressDialog();
    }

    @Override
    public boolean isActivity() {
        return AppLess.isTopActivy(TAG);
    }



















    private MyAsyncTask mtask;
    private int num=0;
    private List<String> base64_list=new ArrayList<>();
    public class MyAsyncTask extends AsyncTask<String, Integer, Void> {
        //内部执行后台任务,不可在此方法内修改UI
        @Override
        protected Void doInBackground(String... arg0) {
            if (isCancelled()) {
                return null;
            }
            List<String> list = Arrays.asList(arg0);
            base64_list.clear();
            num=0;
            for (String s:list){
                base64_list.add(ZoomImage.fileToBase64Str(s));
                num++;
            }
            return null;
        }
        //onPostExecute方法用于在执行完后台任务doInBackground后更新UI,显示结果
        @Override
        protected void onPostExecute(Void result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (StringJudge.isEmpty(base64_list)){
                toastShow("没有数据");
            }
            for (String s:base64_list){
                saveImg(s);
            }
        }
        //onProgressUpdate方法用于更新进度信息
        protected void onProgressUpdate(Integer... integers) {
            super.onProgressUpdate(integers);
        }
        //onPreExecute方法用于在执行后台任务前做一些UI操作
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog("");
        }
    }
    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //判断AsyncTask不为null且Status.RUNNING在运行状态
        if (mtask!=null&&mtask.getStatus()==AsyncTask.Status.RUNNING) {//为mtask标记cancel(true)状态
            mtask.cancel(true);
        }
    }



    @PermissionSuccess(requestCode = TagFinal.CAMERA)
    private void takePhoto() {
        FileCamera camera = new FileCamera(mActivity);
        startActivityForResult(camera.takeCamera(), TagFinal.CAMERA);
    }

    @PermissionSuccess(requestCode = TagFinal.PHOTO_ALBUM)
    private void photoAlbum() {
        Intent intent = new Intent(mActivity, AlbumOneActivity.class);
        Bundle b = new Bundle();
        b.putInt(TagFinal.ALBUM_LIST_INDEX, 0);
        b.putBoolean(TagFinal.ALBUM_SINGLE, false);
        intent.putExtras(b);
        startActivityForResult(intent, TagFinal.PHOTO_ALBUM);
    }

    @PermissionFail(requestCode = TagFinal.CAMERA)
    private void showCamere() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_camere, Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = TagFinal.PHOTO_ALBUM)
    private void showTip1() {
        Toast.makeText(getApplicationContext(), R.string.permission_fail_album, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

}
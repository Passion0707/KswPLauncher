package com.wits.ksw.settings.lexus;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.wits.ksw.R;
import com.wits.ksw.settings.BaseActivity;
import com.wits.ksw.settings.id7.FactoryActivity;
import com.wits.ksw.settings.id7.bean.FunctionBean;
import com.wits.ksw.settings.id7.bean.MapBean;
import com.wits.ksw.settings.lexus.adapter.FunctionAdapter;
import com.wits.ksw.settings.lexus.interfaces.IUpdateTwoLayout;
import com.wits.ksw.settings.lexus.layout_one.SetFactoryLayout;
import com.wits.ksw.settings.lexus.layout_one.SetLanguageLayout;
import com.wits.ksw.settings.lexus.layout_one.SetNaviLayout;
import com.wits.ksw.settings.lexus.layout_one.SetSystemInfoLayout;
import com.wits.ksw.settings.lexus.layout_one.SetSystemLayout;
import com.wits.ksw.settings.lexus.layout_one.SetTimeLayout;
import com.wits.ksw.settings.lexus.layout_one.SetToAndSysLayout;
import com.wits.ksw.settings.lexus.layout_one.SetVocModeLayout;
import com.wits.ksw.settings.lexus.layout_one.SetVoiceLayout;
import com.wits.ksw.settings.lexus.layout_two.NaviTwo;
import com.wits.ksw.settings.lexus.layout_two.SetImageTwo;
import com.wits.ksw.settings.lexus.layout_two.SetSystemTwo;
import com.wits.ksw.settings.lexus.layout_two.SetVocModelTwo;
import com.wits.ksw.settings.lexus.layout_two.SetVoiceTwo;
import com.wits.ksw.settings.lexus.layout_two.TimeSetTwo;
import com.wits.ksw.settings.utlis_view.KeyConfig;
import com.wits.ksw.settings.utlis_view.ScanNaviList;
import com.wits.pms.statuscontrol.PowerManagerApp;
import java.util.ArrayList;
import java.util.List;

public class LexusSettingsActivity extends BaseActivity implements IUpdateTwoLayout, ScanNaviList.OnMapListScanListener {
    private ImageView Img_SetBack;
    private String TAG = "LexusSettingsActivity";
    /* access modifiers changed from: private */
    public FunctionAdapter adapter;
    /* access modifiers changed from: private */
    public List<FunctionBean> data;
    /* access modifiers changed from: private */
    public String defPwd = "1314";
    private boolean first = true;
    private FrameLayout frame_OneLayout;
    private FrameLayout frame_TwoLayout;
    Handler handler = new Handler() {
        @RequiresApi(api = 24)
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int i = msg.what;
            if (i != 0) {
                switch (i) {
                    case 2:
                        if (TextUtils.equals(LexusSettingsActivity.this.defPwd, (String) msg.obj)) {
                            LexusSettingsActivity.this.startActivity(new Intent(LexusSettingsActivity.this, FactoryActivity.class));
                            LexusSettingsActivity.this.finish();
                            return;
                        }
                        LexusSettingsActivity.this.setFactoryLayout.SetTextEEro();
                        return;
                    case 3:
                        if (LexusSettingsActivity.this.naviTwo != null) {
                            Log.d("Navi", "updateList: " + LexusSettingsActivity.this.mapList.size());
                            LexusSettingsActivity.this.naviTwo.updateMapList(LexusSettingsActivity.this.mapList);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            } else {
                LexusSettingsActivity.this.initOneLayout();
                LexusSettingsActivity.this.initTwoLayout();
            }
        }
    };
    private LinearLayoutManager layoutManager;
    /* access modifiers changed from: private */
    public TextView lexus_set_title;
    /* access modifiers changed from: private */
    public List<MapBean> mapList = new ArrayList();
    /* access modifiers changed from: private */
    public NaviTwo naviTwo;
    private RecyclerView recyclerView;
    /* access modifiers changed from: private */
    public SetFactoryLayout setFactoryLayout;
    private SetImageTwo setImageTwo;
    private SetLanguageLayout setLanguageLayout;
    private SetNaviLayout setNaviLayout;
    private SetSystemInfoLayout setSystemInfoLayout;
    private SetSystemLayout setSystemLayout;
    private SetSystemTwo setSystemTwo;
    private SetTimeLayout setTimeLayout;
    private SetToAndSysLayout setToAndSysLayout;
    private SetVocModeLayout setVocModeLayout;
    private SetVocModelTwo setVocModelTwo;
    private SetVoiceLayout setVoiceLayout;
    private SetVoiceTwo setVoiceTwo;
    private TimeSetTwo timeSetTwo;
    private String voiceData;

    /* access modifiers changed from: protected */
    @RequiresApi(api = 24)
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_lexus_settings);
        initData();
        initView();
    }

    @RequiresApi(api = 24)
    public void skipItem() {
        if (TextUtils.equals("voic", this.voiceData)) {
            initOneLayout();
            initTwoLayout();
            setOneLayout(2);
            for (FunctionBean fb : this.data) {
                fb.setIscheck(false);
            }
            this.data.get(2).setIscheck(true);
            if (this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }
        } else if (TextUtils.equals("voicFun", this.voiceData)) {
            initOneLayout();
            initTwoLayout();
            setOneLayout(3);
            for (FunctionBean fb2 : this.data) {
                fb2.setIscheck(false);
            }
            this.data.get(3).setIscheck(true);
            if (this.adapter != null) {
                this.adapter.notifyDataSetChanged();
            }
        }
    }

    private void initSaveData() {
        try {
            List<String> naviList = PowerManagerApp.getDataListFromJsonKey(KeyConfig.NAVI_LIST);
            String navidefual = PowerManagerApp.getSettingsString(KeyConfig.NAVI_DEFUAL);
            if (naviList != null && naviList.size() > 0) {
                ScanNaviList.getInstance().scanList(naviList, navidefual, this);
                Log.d("Navi", "==size==:" + ScanNaviList.getInstance().getMapList().size());
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    @RequiresApi(api = 24)
    public void onResume() {
        super.onResume();
        ScanNaviList.getInstance().setMapListScanListener(this);
        this.handler.sendEmptyMessageDelayed(0, 1000);
        initSaveData();
        skipItem();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        ScanNaviList.getInstance().setMapListScanListener((ScanNaviList.OnMapListScanListener) null);
    }

    /* access modifiers changed from: private */
    @RequiresApi(api = 24)
    public void initOneLayout() {
        if (this.setSystemInfoLayout == null) {
            this.setSystemLayout = new SetSystemLayout(this);
            this.setSystemLayout.registIUpdateTwoLayout(this);
        }
        if (this.setNaviLayout == null) {
            this.setNaviLayout = new SetNaviLayout(this);
            this.setNaviLayout.registIUpdateTwoLayout(this);
        }
        if (this.setVoiceLayout == null) {
            this.setVoiceLayout = new SetVoiceLayout(this);
            this.setVoiceLayout.registIUpdateTwoLayout(this);
        }
        if (this.setVocModeLayout == null) {
            this.setVocModeLayout = new SetVocModeLayout(this);
            this.setVocModeLayout.registIUpdateTwoLayout(this);
        }
        if (this.setLanguageLayout == null) {
            this.setLanguageLayout = new SetLanguageLayout(this);
        }
        if (this.setToAndSysLayout == null) {
            this.setToAndSysLayout = new SetToAndSysLayout(this);
        }
        if (this.setTimeLayout == null) {
            this.setTimeLayout = new SetTimeLayout(this);
            this.setTimeLayout.registIUpdateTwoLayout(this);
        }
        if (this.setSystemInfoLayout == null) {
            this.setSystemInfoLayout = new SetSystemInfoLayout(this);
        }
        if (this.setFactoryLayout == null) {
            this.setFactoryLayout = new SetFactoryLayout(this, this.handler);
        }
    }

    /* access modifiers changed from: private */
    public void initTwoLayout() {
        if (this.setSystemTwo == null) {
            this.setSystemTwo = new SetSystemTwo(this);
        }
        if (this.naviTwo == null) {
            this.naviTwo = new NaviTwo(this);
        }
        if (this.setImageTwo == null) {
            this.setImageTwo = new SetImageTwo(this);
        }
        if (this.setVocModelTwo == null) {
            this.setVocModelTwo = new SetVocModelTwo(this);
        }
        if (this.timeSetTwo == null) {
            this.timeSetTwo = new TimeSetTwo(this);
        }
        if (this.setVoiceTwo == null) {
            this.setVoiceTwo = new SetVoiceTwo(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.voiceData = intent.getStringExtra("voiceData");
        Log.d("lexusstartAction", "===data====:" + this.voiceData);
    }

    private void initData() {
        try {
            this.voiceData = getIntent().getStringExtra("voiceData");
            Log.d("lexusstartAction", "===data====:" + this.voiceData);
            this.defPwd = PowerManagerApp.getSettingsString(KeyConfig.PASSWORD);
            Log.d("lexusFactoryPwd", "===pwd====:" + this.defPwd);
            if (TextUtils.isEmpty(this.defPwd)) {
                this.defPwd = "1314";
            }
        } catch (Exception e) {
            e.getStackTrace();
            this.defPwd = "1314";
        }
        int[] icons = {R.drawable.lexus_settings_btn_set_n, R.drawable.lexus_settings_btn_gps_n, R.drawable.lexus_settings_btn_audio_n, R.drawable.lexus_settings_btn_language_n, R.drawable.lexus_settings_btn_time_n, R.drawable.lexus_settings_btn_info_n, R.drawable.lexus_settings_btn_android_n, R.drawable.lexus_settings_btn_factory_n};
        this.data = new ArrayList();
        String[] stringArray = getResources().getStringArray(R.array.set_function);
        for (int icon : icons) {
            FunctionBean fcb = new FunctionBean();
            fcb.setIcon(icon);
            this.data.add(fcb);
        }
        this.data.get(0).setIscheck(true);
    }

    private void initView() {
        this.recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        this.lexus_set_title = (TextView) findViewById(R.id.lexus_set_title);
        this.layoutManager = new LinearLayoutManager(this);
        this.layoutManager.setOrientation(1);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.adapter = new FunctionAdapter(this, this.data);
        this.recyclerView.setAdapter(this.adapter);
        new DividerItemDecoration(this, 1).setDrawable(ContextCompat.getDrawable(this, R.drawable.lexus_settings_line_left));
        this.adapter.registOnFunctionClickListener(new FunctionAdapter.OnFunctionClickListener() {
            @RequiresApi(api = 24)
            public void functonClick(int pos) {
                String[] stringArray = LexusSettingsActivity.this.getResources().getStringArray(R.array.set_function);
                int arrayPos = pos;
                if (arrayPos >= 3) {
                    arrayPos++;
                }
                LexusSettingsActivity.this.lexus_set_title.setText(stringArray[arrayPos]);
                LexusSettingsActivity.this.setOneLayout(pos);
                for (FunctionBean fb : LexusSettingsActivity.this.data) {
                    fb.setIscheck(false);
                }
                ((FunctionBean) LexusSettingsActivity.this.data.get(pos)).setIscheck(true);
                LexusSettingsActivity.this.adapter.notifyDataSetChanged();
            }
        });
        this.frame_OneLayout = (FrameLayout) findViewById(R.id.frame_OneLayout);
        this.frame_TwoLayout = (FrameLayout) findViewById(R.id.frame_TwoLayout);
        if (this.setSystemLayout == null) {
            this.setSystemLayout = new SetSystemLayout(this);
            this.setSystemLayout.registIUpdateTwoLayout(this);
        }
        this.frame_OneLayout.addView(this.setSystemLayout);
        if (this.setSystemTwo == null) {
            this.setSystemTwo = new SetSystemTwo(this);
        }
        this.frame_TwoLayout.addView(this.setSystemTwo);
    }

    /* access modifiers changed from: private */
    @RequiresApi(api = 24)
    public void setOneLayout(int type) {
        this.frame_OneLayout.removeAllViews();
        this.frame_TwoLayout.removeAllViews();
        this.frame_TwoLayout.setVisibility(type == 7 ? 8 : 0);
        switch (type) {
            case 0:
                if (this.setSystemLayout == null) {
                    this.setSystemLayout = new SetSystemLayout(this);
                    this.setSystemLayout.registIUpdateTwoLayout(this);
                }
                if (this.setSystemTwo == null) {
                    this.setSystemTwo = new SetSystemTwo(this);
                }
                this.frame_OneLayout.addView(this.setSystemLayout);
                this.setSystemLayout.resetTextColor();
                this.frame_TwoLayout.addView(this.setSystemTwo);
                break;
            case 1:
                if (this.setNaviLayout == null) {
                    this.setNaviLayout = new SetNaviLayout(this);
                    this.setNaviLayout.registIUpdateTwoLayout(this);
                }
                if (this.naviTwo == null) {
                    this.naviTwo = new NaviTwo(this);
                }
                if (this.setImageTwo == null) {
                    this.setImageTwo = new SetImageTwo(this);
                }
                this.setImageTwo.setResource(R.drawable.lexus_settings_icon2);
                this.frame_OneLayout.addView(this.naviTwo);
                this.setNaviLayout.resetTextColor();
                this.frame_TwoLayout.addView(this.setImageTwo);
                break;
            case 2:
                if (this.setVoiceLayout == null) {
                    this.setVoiceLayout = new SetVoiceLayout(this);
                }
                if (this.setVoiceTwo == null) {
                    this.setVoiceTwo = new SetVoiceTwo(this);
                }
                this.frame_OneLayout.addView(this.setVoiceLayout);
                this.setVoiceLayout.resetTextColor();
                this.frame_TwoLayout.addView(this.setVoiceTwo);
                break;
            case 3:
                if (this.setLanguageLayout == null) {
                    this.setLanguageLayout = new SetLanguageLayout(this);
                }
                if (this.setImageTwo == null) {
                    this.setImageTwo = new SetImageTwo(this);
                }
                this.setImageTwo.setResource(R.drawable.lexus_settings_icon4);
                this.frame_OneLayout.addView(this.setLanguageLayout);
                this.frame_TwoLayout.addView(this.setImageTwo);
                break;
            case 4:
                if (this.setTimeLayout == null) {
                    this.setTimeLayout = new SetTimeLayout(this);
                    this.setTimeLayout.registIUpdateTwoLayout(this);
                }
                if (this.timeSetTwo == null) {
                    this.timeSetTwo = new TimeSetTwo(this);
                }
                this.frame_OneLayout.addView(this.setTimeLayout);
                this.setTimeLayout.resetTextColor();
                this.frame_TwoLayout.addView(this.timeSetTwo);
                break;
            case 5:
                if (this.setSystemInfoLayout == null) {
                    this.setSystemInfoLayout = new SetSystemInfoLayout(this);
                }
                if (this.setImageTwo == null) {
                    this.setImageTwo = new SetImageTwo(this);
                }
                this.setImageTwo.setResource(R.drawable.lexus_settings_icon6);
                this.frame_OneLayout.addView(this.setSystemInfoLayout);
                this.frame_TwoLayout.addView(this.setImageTwo);
                break;
            case 6:
                if (this.setToAndSysLayout == null) {
                    this.setToAndSysLayout = new SetToAndSysLayout(this);
                }
                if (this.setImageTwo == null) {
                    this.setImageTwo = new SetImageTwo(this);
                }
                this.setImageTwo.setResource(R.drawable.lexus_settings_icon7);
                this.frame_OneLayout.addView(this.setToAndSysLayout);
                this.frame_TwoLayout.addView(this.setImageTwo);
                break;
            case 7:
                if (this.setFactoryLayout == null) {
                    this.setFactoryLayout = new SetFactoryLayout(this, this.handler);
                }
                if (this.setImageTwo == null) {
                    this.setImageTwo = new SetImageTwo(this);
                }
                this.frame_OneLayout.addView(this.setFactoryLayout);
                break;
        }
        this.frame_OneLayout.requestFocus();
    }

    public void updateTwoLayout(int type, int shwoIndex) {
        String str = this.TAG;
        Log.d(str, "updateTwoLayout type=" + type + " shwoIndex=" + shwoIndex);
        switch (type) {
            case 1:
                this.setSystemTwo.showLayout(shwoIndex);
                this.setSystemTwo.invalidate();
                this.setSystemTwo.requestFocus();
                return;
            case 2:
                this.naviTwo.showLayout(shwoIndex);
                this.naviTwo.invalidate();
                this.naviTwo.requestFocus();
                return;
            case 3:
                this.setVocModelTwo.showLayout(shwoIndex);
                this.setVocModelTwo.invalidate();
                this.setVocModelTwo.requestFocus();
                return;
            case 4:
                this.timeSetTwo.showLayout(shwoIndex);
                this.timeSetTwo.invalidate();
                this.timeSetTwo.requestFocus();
                return;
            case 5:
                this.setVoiceTwo.showLayout(shwoIndex);
                this.setVoiceTwo.invalidate();
                this.setVoiceTwo.requestFocus();
                return;
            default:
                return;
        }
    }

    public void onScanFinish(List<MapBean> mapList2) {
        Log.d("Navi", "onScanFinish " + mapList2.size());
        this.mapList = mapList2;
        this.handler.sendEmptyMessage(3);
    }
}

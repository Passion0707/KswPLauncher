package com.wits.ksw.settings.romeo.layout_one;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.wits.ksw.R;
import com.wits.ksw.launcher.utils.FixLinearSnapHelper;
import com.wits.ksw.launcher.utils.KswUtils;
import com.wits.ksw.settings.id7.bean.FunctionBean;
import com.wits.ksw.settings.romeo.adapter.LanguageAdapter;
import com.wits.ksw.settings.romeo.interfaces.IUpdateListBg;
import com.wits.ksw.settings.utlis_view.KeyConfig;
import com.wits.pms.statuscontrol.PowerManagerApp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SetLanguageLayout extends RelativeLayout {
    private LanguageAdapter adapter;
    private Context context;
    /* access modifiers changed from: private */
    public List<FunctionBean> data;
    private int defLanguage = 0;
    private LinearLayoutManager layoutManager;
    private List<Locale> locales;
    private RecyclerView recyclerView;
    private IUpdateListBg updateListBg;

    @RequiresApi(api = 24)
    public SetLanguageLayout(Context context2) {
        super(context2);
        this.context = context2;
        View view = LayoutInflater.from(context2).inflate(R.layout.romeo_layout_set_language, (ViewGroup) null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        LocaleList();
        initData();
        initView(view);
        view.setLayoutParams(layoutParams);
        addView(view);
    }

    public void registIUpdateListBg(IUpdateListBg updateListBg2) {
        this.updateListBg = updateListBg2;
        this.adapter.setIUpdateListBg(updateListBg2);
    }

    @RequiresApi(api = 24)
    private void initData() {
        try {
            this.defLanguage = PowerManagerApp.getSettingsInt(KeyConfig.LANGUAGE_DEFUAL);
            this.data = new ArrayList();
            List<String> languags = PowerManagerApp.getDataListFromJsonKey(KeyConfig.LANGUAGE_LIST);
            for (String lang : languags) {
                Log.d("SettingSetLaung", "=====:" + lang);
                FunctionBean fb = new FunctionBean();
                fb.setTitle(lang);
                this.data.add(fb);
            }
            this.data.get(this.defLanguage).setIscheck(true);
            Locale sysLanguage = Locale.getDefault();
            Log.d("SystemLa", "21 la:" + sysLanguage.getLanguage() + "dw:" + sysLanguage.getCountry());
            int checkInex = -1;
            boolean ishave = false;
            for (int i = 0; i < languags.size(); i++) {
                Log.d("SystemLa", "language:" + this.locales.get(i).getLanguage() + "country:" + this.locales.get(i).getCountry());
                Log.d("SystemLa", "22 la:" + sysLanguage.getLanguage() + "dw:" + sysLanguage.getCountry());
                if (TextUtils.equals(this.locales.get(i).getLanguage(), sysLanguage.getLanguage()) && this.locales.get(i).getCountry().equals(sysLanguage.getCountry())) {
                    ishave = true;
                    checkInex = i;
                }
            }
            if (ishave) {
                for (FunctionBean lsg : this.data) {
                    lsg.setIscheck(false);
                }
                this.data.get(checkInex).setIscheck(true);
                return;
            }
            for (FunctionBean lsg2 : this.data) {
                lsg2.setIscheck(false);
            }
            FunctionBean fb2 = new FunctionBean();
            fb2.setTitle("Other Launguae");
            fb2.setIscheck(true);
            this.data.add(fb2);
        } catch (Exception e) {
        }
    }

    private void initView(View view) {
        this.recyclerView = (RecyclerView) view.findViewById(R.id.language_recycle);
        this.layoutManager = new LinearLayoutManager(this.context);
        this.layoutManager.setOrientation(1);
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.recyclerView.setItemViewCacheSize(0);
        new FixLinearSnapHelper().attachToRecyclerView(this.recyclerView);
        this.adapter = new LanguageAdapter(this.context, this.data);
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.setIUpdateListBg(this.updateListBg);
        new DividerItemDecoration(this.context, 1).setDrawable(ContextCompat.getDrawable(this.context, R.drawable.lexus_settings_line_left));
        this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                for (int i = 0; i < recyclerView.getChildCount(); i++) {
                    int pad = KswUtils.calculateTranslate(recyclerView.getChildAt(i).getTop(), KswUtils.dip2px(SetLanguageLayout.this.getContext(), 428.0f), i, SetLanguageLayout.this.getContext());
                    recyclerView.getChildAt(i).setPadding(pad, 0, pad, 0);
                }
            }
        });
        this.adapter.registOnFunctionClickListener(new LanguageAdapter.OnFunctionClickListener() {
            @RequiresApi(api = 24)
            public void functonClick(int pos) {
                for (FunctionBean fb : SetLanguageLayout.this.data) {
                    fb.setIscheck(false);
                }
                ((FunctionBean) SetLanguageLayout.this.data.get(pos)).setIscheck(true);
                try {
                    PowerManagerApp.setSettingsInt(KeyConfig.LANGUAGE_DEFUAL, pos);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
        });
    }

    private void LocaleList() {
        this.locales = new ArrayList();
        this.locales.add(new Locale("zh", "CN"));
        this.locales.add(new Locale("zh", "TW"));
        this.locales.add(new Locale("en"));
        this.locales.add(new Locale("de"));
        this.locales.add(new Locale("es"));
        this.locales.add(new Locale("ko", "KR"));
        this.locales.add(new Locale("it"));
        this.locales.add(new Locale("nl"));
        this.locales.add(new Locale("ru"));
        this.locales.add(new Locale("fr"));
    }
}

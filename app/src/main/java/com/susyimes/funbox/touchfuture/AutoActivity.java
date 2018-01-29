package com.susyimes.funbox.touchfuture;

import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.susyimes.funbox.network.CoinData;
import com.susyimes.funbox.network.HuobiBaseBean;
import com.susyimes.funbox.network.Retrofits;
import com.susyimes.funbox.touchfuture.databinding.ActivityAutoBinding;
import com.susyimes.funbox.touchfuture.utils.DataPoint;
import com.susyimes.funbox.touchfuture.utils.RegressionLine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class AutoActivity extends AppCompatActivity {
    private ActivityAutoBinding binding;
    private List<CoinData> coinDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auto);
        coinDataList=new ArrayList<>();
        initNetWork();
    }

    private void initNetWork() {

        Log.d("feee","xxx1");
        Retrofits.getService(this).base("eosusdt","1day").doOnNext(new Consumer<HuobiBaseBean>() {
            @Override
            public void accept(HuobiBaseBean huobiBaseBean) throws Exception {


            }
        }).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() {
            @Override
            public void run() throws Exception {

            }
        }).subscribe(new Consumer<HuobiBaseBean>() {
            @Override
            public void accept(HuobiBaseBean huobiBaseBean) throws Exception {
                Log.d("feee2","xxx"+huobiBaseBean.data.size());
                coinDataList.addAll(huobiBaseBean.data);
                computer7();
                computerMon();

            }
        });

    }

    private void computerMon() {
        RegressionLine linehigh = new RegressionLine();
        List<CoinData> monCoinDatas=coinDataList.subList(1,31);
        Log.d("feee",monCoinDatas.size()+"xxx"+monCoinDatas.get(0).high+"ss");
        Collections.reverse(monCoinDatas);
        Log.d("feee",monCoinDatas.size()+"xxx"+monCoinDatas.get(0).high+"ss2");

        for (int i=0;i<monCoinDatas.size();i++){


            linehigh.addDataPoint(new DataPoint(i+1, monCoinDatas.get(i).open));
        }

        System.out.println("\n回归线公式:  y = " + linehigh.getA1() + "x + "
                + linehigh.getA0());
        //System.out.println("误差：     R^2 = " + linehigh.getR());
        String devationhigh=linehigh.getR()+"";
        float resultnumhigh=(linehigh.getA1()*(monCoinDatas.size()))+linehigh.getA0();


        RegressionLine linelow = new RegressionLine();


        for (int i=0;i<monCoinDatas.size();i++){


            linelow.addDataPoint(new DataPoint(i+1,monCoinDatas.get(i).close));
        }

        System.out.println("\n回归线公式:  y = " + linelow.getA1() + "x + "
                + linelow.getA0());
        //System.out.println("误差：     R^2 = " + linelow.getR());
        String devationlow=linelow.getR()+"";

        float resultnumlow=(linelow.getA1()*(monCoinDatas.size()))+linelow.getA0();

        binding.monthNum.setText("MonthDay: "+" open: "+resultnumhigh+" deviation："+devationlow+"     close: "+resultnumlow +" deviation："+devationhigh);

        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setText(binding.monthNum.getText());



    }

    private void computer7() {

        RegressionLine linehigh = new RegressionLine();

        List<CoinData> sevCoinDatas=coinDataList.subList(1,8);
        Log.d("feee",sevCoinDatas.size()+"xxx"+sevCoinDatas.get(0).high+"ss");
        Collections.reverse(sevCoinDatas);
        Log.d("feee",sevCoinDatas.size()+"xxx"+sevCoinDatas.get(0).high+"ss2");

        for (int i=0;i<sevCoinDatas.size();i++){

            linehigh.addDataPoint(new DataPoint(i+1, sevCoinDatas.get(i).high));
        }

        System.out.println("\n回归线公式:  y = " + linehigh.getA1() + "x + "
                + linehigh.getA0());
        System.out.println("误差：     R^2 = " + linehigh.getR());

        float resultnumhigh=(linehigh.getA1()*(sevCoinDatas.size()))+linehigh.getA0();


        RegressionLine linelow = new RegressionLine();


        for (int i=0;i<sevCoinDatas.size();i++){


            linelow.addDataPoint(new DataPoint(i+1, sevCoinDatas.get(i).low));
        }

        System.out.println("\n回归线公式:  y = " + linelow.getA1() + "x + "
                + linelow.getA0());
        System.out.println("误差：     R^2 = " + linelow.getR());

        float resultnumlow=(linelow.getA1()*(sevCoinDatas.size()))+linelow.getA0();

        //binding.sevendayNum.setText("SevenDay: low:"+resultnumlow+" high:"+resultnumhigh);

    }


}

package com.susyimes.funbox.touchfuture;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.susyimes.funbox.network.CleanRetrofit;
import com.susyimes.funbox.network.Retrofits;
import com.susyimes.funbox.touchfuture.databinding.ActivityMainBinding;
import com.susyimes.funbox.touchfuture.utils.DataPoint;
import com.susyimes.funbox.touchfuture.utils.RegressionLine;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private List<Float> list;
    private ActivityMainBinding binding;
    private boolean allequals;
    private boolean noequals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        list=new ArrayList<>();

        //result();

        initAction();

        initNetWork();

    }

    private void initNetWork() {
        Log.d("feee","xxx1");
        Retrofits.getService(this).base("btcusdt","1day").doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("feee",s+"xxx");
            }
        }).observeOn(AndroidSchedulers.mainThread()).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                Log.d("feee","xxx2");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d("feee",s+"xxx");
            }
        });

    }

    private void result() {
        initNetWork();
        allequals=true;
        RegressionLine line = new RegressionLine();



        for (int i=0;i<list.size();i++){
            if (i>=1){
                if (list.get(i-1)!=list.get(i)){
                    allequals=false;
                }
            }

            line.addDataPoint(new DataPoint(i+1, list.get(i)));
        }

        if (allequals){
            binding.textResult.setText(list.get(0)+"");
        }else {

        System.out.println("\n回归线公式:  y = " + line.getA1() + "x + "
                + line.getA0());
        System.out.println("误差：     R^2 = " + line.getR());

        float resultnum=(line.getA1()*(list.size()+1))+line.getA0();

        binding.textResult.setText(resultnum+"");}


    }

    private void initAction() {

        binding.computeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.et1.getText().length()!=0){
                    list.add(Float.valueOf(binding.et1.getText().toString()));
                }
                if (binding.et2.getText().length()!=0){
                    list.add(Float.valueOf(binding.et2.getText().toString()));
                }
                if (binding.et3.getText().length()!=0){
                    list.add(Float.valueOf(binding.et3.getText().toString()));
                }
                if (binding.et4.getText().length()!=0){
                    list.add(Float.valueOf(binding.et4.getText().toString()));
                }
                if (binding.et5.getText().length()!=0){
                    list.add(Float.valueOf(binding.et5.getText().toString()));
                }
                if (binding.et6.getText().length()!=0){
                    list.add(Float.valueOf(binding.et6.getText().toString()));
                }
                if (binding.et7.getText().length()!=0){
                    list.add(Float.valueOf(binding.et7.getText().toString()));
                }
                Log.d("boom",list.toString()+"");
                Log.d("boom2",list.size()+"222");
                if (list.size()>1){
                result();
                list.clear();
                }else {
                    Toast.makeText(getApplicationContext(),"please txt double num",Toast.LENGTH_SHORT).show();
                    list.clear();
                }
            }
        });

        binding.cleanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.et1.setText("");
                binding.et2.setText("");
                binding.et3.setText("");
                binding.et4.setText("");
                binding.et5.setText("");
                binding.et6.setText("");
                binding.et7.setText("");
                list.clear();
            }
        });



    }


}

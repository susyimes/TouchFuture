package com.susyimes.funbox.touchfuture;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.susyimes.funbox.touchfuture.databinding.ActivityMainBinding;
import com.susyimes.funbox.touchfuture.utils.DataPoint;
import com.susyimes.funbox.touchfuture.utils.RegressionLine;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Integer> list;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);

        list=new ArrayList<>();

        result();

        initAction();

    }

    private void result() {
        if (binding.et1.getText().length()!=0){};


        RegressionLine line = new RegressionLine();

        for (int i=0;i<list.size();i++){
            line.addDataPoint(new DataPoint(i, list.get(i)));
        }

        System.out.println("\n回归线公式:  y = " + line.getA1() + "x + "
                + line.getA0());
        System.out.println("误差：     R^2 = " + line.getR());

        float resultnum=(line.getA1()*list.size()+1)+line.getA0();

        binding.textResult.setText(resultnum+"");


    }

    private void initAction() {

        binding.computeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result();
            }
        });

    }


}

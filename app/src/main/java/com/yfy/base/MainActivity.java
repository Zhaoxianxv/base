package com.yfy.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.yfy.charting_mp_test.charts.RadarChart;
import com.yfy.charting_mp_test.components.XAxis;
import com.yfy.charting_mp_test.components.YAxis;
import com.yfy.charting_mp_test.data.RadarData;
import com.yfy.charting_mp_test.data.RadarDataSet;
import com.yfy.charting_mp_test.data.RadarEntry;
import com.yfy.charting_mp_test.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RadarChart radarChart;

    float x = 0;
    float y = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radarChart = findViewById(R.id.radarchart);

        initRadarState();

        radarChart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                List<RadarPointBean> pointBeans = RadarUtil.computePosition(radarChart);
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x = event.getX();
                        y = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        for (int i = 0; i < pointBeans.size(); i++) {
                            RadarPointBean pointBean = pointBeans.get(i);
                            if (pointBean.isIn(x, y)) {
                                showToast(axisArray[i]);
                                return true;
                            }
                        }
                        break;
                    default:

                        break;
                }
                return false;
            }
        });

        bindcompositeRadar();
    }

    Toast toast;

    private void showToast(String text) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void initRadarState() {
        // 绘制线条宽度，圆形向外辐射的线条
        radarChart.setWebColor(Color.LTGRAY);
        radarChart.setWebLineWidth(1.5f);
        // 内部线条宽度，外面的环状线条
        radarChart.setWebColorInner(Color.LTGRAY);
        radarChart.setWebLineWidthInner(1.0f);
        // 所有线条WebLine透明度
        radarChart.setWebAlpha(100);

        XAxis xAxis = radarChart.getXAxis();

        // X坐标值字体大小
        xAxis.setTextSize(10f);

        YAxis yAxis = radarChart.getYAxis();

        yAxis.setDrawLabels(false);
        // Y坐标值标签个数
        yAxis.setLabelCount(6, false);
        // Y坐标值字体大小
        yAxis.setTextSize(10f);
        // Y坐标值是否从0开始
        yAxis.setStartAtZero(true);
    }

    String[] axisArray = new String[]{"实践创新", "思想道德", "学业成长", "审美表现", "身心健康"};

    private void bindcompositeRadar() {

        List<String> list = new ArrayList<>();
        List<RadarEntry> studentEntries = new ArrayList<>();//个人
        for (int i = 0; i < axisArray.length; i++) {
            studentEntries.add(new RadarEntry((float) (Math.random() * 10), axisArray[i]));
            list.add(axisArray[i]);
        }

        XAxis xAxis = radarChart.getXAxis();


        RadarDataSet studentSet = new RadarDataSet(studentEntries, "个人");
        studentSet.setColor(Color.parseColor("#14d089"));
        studentSet.setFillColor(Color.parseColor("#14d089"));
        studentSet.setDrawFilled(true);
        studentSet.setFillAlpha(85);
        studentSet.setLineWidth(1f);
        studentSet.setDrawHighlightCircleEnabled(true);
        studentSet.setDrawHighlightIndicators(false);


        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(studentSet);

        RadarData data = new RadarData(sets);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        radarChart.getLegend().setEnabled(true);
        radarChart.setData(data);
        radarChart.invalidate();
    }
}

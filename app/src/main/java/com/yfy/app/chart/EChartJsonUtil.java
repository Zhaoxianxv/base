package com.yfy.app.chart;

import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.SeriesType;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Pie;

import java.util.List;
import java.util.Map;

/**
 * Created by zxx.
 * Date: 2021/4/6
 */
public class EChartOptionUtil {


    /**
     * -------极坐标堆叠图
     */

    public static GsonOption getPileChartOptions(){
        GsonOption option = new GsonOption();


        return option;
    }
    /**
     * 饼状图
     *
     */
    public static GsonOption getBingTuChartOptions(Object[] data, List<Map<String, Object>> yAxis) {
        GsonOption option = new GsonOption();

        option.title("支付方式占比");
        option.title().setX(X.center);

        option.tooltip().trigger(Trigger.item);
        option.tooltip().formatter("{a} {b}:{c} ({d}%)");

        option.legend().left(X.left).orient(Orient.vertical);
        option.legend().data(data);

        Pie pie = new Pie();
        pie.name("");
        pie.type(SeriesType.pie);
        pie.center("50%", "70%").radius("55%");
        pie.itemStyle().emphasis().shadowBlur(10).shadowOffsetX(0).shadowColor("rgba(0, 0, 0, 0.5)");
        pie.setData(yAxis);
        option.series(pie);
        return option;
    }


}

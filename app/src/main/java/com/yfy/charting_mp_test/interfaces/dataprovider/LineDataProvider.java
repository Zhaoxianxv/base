package com.yfy.charting_mp_test.interfaces.dataprovider;

import com.yfy.charting_mp_test.components.YAxis;
import com.yfy.charting_mp_test.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}

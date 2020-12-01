package com.yfy.charting.interfaces.dataprovider;

import com.yfy.charting.components.YAxis;
import com.yfy.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}

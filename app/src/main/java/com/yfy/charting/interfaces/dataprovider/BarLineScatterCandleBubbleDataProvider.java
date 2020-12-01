package com.yfy.charting.interfaces.dataprovider;

import com.yfy.charting.components.YAxis.AxisDependency;
import com.yfy.charting.data.BarLineScatterCandleBubbleData;
import com.yfy.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}

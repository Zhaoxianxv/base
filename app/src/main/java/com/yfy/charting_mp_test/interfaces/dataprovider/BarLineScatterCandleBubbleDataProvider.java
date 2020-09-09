package com.yfy.charting_mp_test.interfaces.dataprovider;

import com.yfy.charting_mp_test.components.YAxis.AxisDependency;
import com.yfy.charting_mp_test.data.BarLineScatterCandleBubbleData;
import com.yfy.charting_mp_test.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}

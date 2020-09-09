package com.yfy.charting_mp_test.interfaces.dataprovider;

import com.yfy.charting_mp_test.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}

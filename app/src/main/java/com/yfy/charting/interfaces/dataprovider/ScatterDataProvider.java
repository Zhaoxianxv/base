package com.yfy.charting.interfaces.dataprovider;

import com.yfy.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}

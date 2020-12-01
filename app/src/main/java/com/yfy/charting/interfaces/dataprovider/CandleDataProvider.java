package com.yfy.charting.interfaces.dataprovider;

import com.yfy.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}

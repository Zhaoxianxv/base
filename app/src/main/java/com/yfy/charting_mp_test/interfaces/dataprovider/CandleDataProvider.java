package com.yfy.charting_mp_test.interfaces.dataprovider;

import com.yfy.charting_mp_test.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}

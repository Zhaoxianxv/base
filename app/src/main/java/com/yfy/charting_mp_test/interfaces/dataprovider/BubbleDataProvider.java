package com.yfy.charting_mp_test.interfaces.dataprovider;

import com.yfy.charting_mp_test.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}

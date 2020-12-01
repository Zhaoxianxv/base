package com.yfy.charting.interfaces.dataprovider;

import com.yfy.charting.data.BubbleData;

public interface BubbleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    BubbleData getBubbleData();
}

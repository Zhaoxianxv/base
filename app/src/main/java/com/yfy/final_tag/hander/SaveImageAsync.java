package com.yfy.final_tag.hander;


import java.util.List;

/**
 * Created by zxx.
 * Date: 2021/2/4
 */
public interface SaveImageAsync {
    List<String> doIn(String... arg0);
    void doUpData(List<String> list);
    void onPre();
}

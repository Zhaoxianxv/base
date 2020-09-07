package com.yfy.app.net.applied_order;

import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/5/10.
 */
@Root(name =  TagFinal.ORDER_GET_DETAIL +Base.RESPONSE)
public class OrderGetDetailRes {
    @Attribute(name = "xmlns", empty = TagFinal.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name =  TagFinal.ORDER_GET_DETAIL +Base.RESULT, required = false)
    public String result;
}

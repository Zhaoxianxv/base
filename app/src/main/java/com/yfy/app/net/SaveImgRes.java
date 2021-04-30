package com.yfy.app.net;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.BASE_SAVE_IMG +Base.RESPONSE)
public class SaveImgRes {

    @Attribute(name = Base.XMLNS, empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name =  TagFinal.BASE_SAVE_IMG +Base.RESULT, required = false)
    public String result;
}

package com.yfy.app.net.maintain;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/27.
 */
@Root(name =  TagFinal.MAINTAIN_SET_SECTION_MSG + Base.RESPONSE)
public class MaintainSetSectionRes {
    @Attribute(name = "xmlns", empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = TagFinal.MAINTAIN_SET_SECTION_MSG +"Result", required = false)
    public String result;
}

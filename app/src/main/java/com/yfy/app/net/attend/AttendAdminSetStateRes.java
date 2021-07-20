package com.yfy.app.net.attend;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/27.
 */
@Root(name = TagFinal.ATTEND_ADMIN_SET_STATE +"Response")
public class AttendAdminSetStateRes {
    @Attribute(name = "xmlns", empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = TagFinal.ATTEND_ADMIN_SET_STATE +"Result", required = false)
    public String result;
}

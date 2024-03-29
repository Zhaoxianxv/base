package com.yfy.app.net.login;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.USER_ADD_HEAD+"Response")
public class UserResetPicRes {
    @Attribute(name = "xmlns", empty = Base.NAMESPACE, required = false)
    public String nameSpace;


    @Element(name = TagFinal.USER_ADD_HEAD+"Result", required = false)
    public String result;
}

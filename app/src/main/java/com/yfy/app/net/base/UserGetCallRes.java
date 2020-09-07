package com.yfy.app.net.base;

import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.USER_GET_MOBILE+"Response")
public class UserGetCallRes {

    @Attribute(name = "xmlns", empty = TagFinal.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = TagFinal.USER_GET_MOBILE+"Result", required = false)
    public String result;
}

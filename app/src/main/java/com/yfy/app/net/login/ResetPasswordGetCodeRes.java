package com.yfy.app.net.login;

import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.USER_GET_CODE_TO_EDIT_PHONE +"Response")
public class ResetPasswordGetCodeRes {

    @Attribute(name = "xmlns", empty = "http://tempuri.org/", required = false)
    public String nameSpace;

    @Element(name = TagFinal.USER_GET_CODE_TO_EDIT_PHONE +"Result", required = false)
    public String result;
}

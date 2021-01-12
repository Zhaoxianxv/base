package com.yfy.app.net.base;

import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.USER_RESET_PASSWORD +Base.RESPONSE)
public class UserResetPasswordRes {

    @Attribute(name = Base.XMLNS, empty = Base.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name = TagFinal.USER_RESET_PASSWORD +Base.RESULT, required = false)
    public String result;
}

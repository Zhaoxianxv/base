package com.yfy.app.net.base;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.USER_RESET_PASSWORD, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {"userinfo"})
public class UserResetPasswordReq {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "userinfo", required = false)
    public String userinfo;

    public void setUserinfo(String userinfo) {
        this.userinfo = userinfo;
    }
}

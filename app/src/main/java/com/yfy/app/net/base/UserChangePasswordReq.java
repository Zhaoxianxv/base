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
@Root(name = TagFinal.USER_CHANGE_PASSWORD, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"oldpassword","newpassword"})
public class UserChangePasswordReq {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    public String session_key;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "oldpassword", required = false)
    public String oldpassword;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "newpassword", required = false)
    public String newpassword;

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}

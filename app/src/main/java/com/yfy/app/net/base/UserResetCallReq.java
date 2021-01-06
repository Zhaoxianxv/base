package com.yfy.app.net.base;

import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.USER_SET_MOBILE, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"no"})
public class UserResetCallReq {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    public String session_key ;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "no", required = false)
    public String no;

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public void setNo(String no) {
        this.no = no;
    }
}

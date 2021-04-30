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
@Root(name = TagFinal.USER_GET_CLASS_LIST, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements ={Base.session_key})
public class UserGetClassListReq {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    public String session_key;


    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }



}

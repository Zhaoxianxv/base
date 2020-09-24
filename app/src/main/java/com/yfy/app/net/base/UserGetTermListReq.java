package com.yfy.app.net.base;

import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.USER_GET_TERM_LIST, strict = false)
@Namespace(reference = TagFinal.NAMESPACE)
public class UserGetTermListReq {

    @Namespace(reference = TagFinal.NAMESPACE)
    @Element(name = "session_key", required = false)
    public String session_key=Base.user.getSession_key();


    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }



}

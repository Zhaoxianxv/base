package com.yfy.app.net.login;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.USER_ADD_HEAD, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"filename"})
public class UserResetPicReq {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "session_key", required = false)
    private String session_key= Base.user.getSession_key();     ///


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "filename", required = false)
    private String filename;


    public void setFilename(String filename) {
        this.filename = filename;
    }
}

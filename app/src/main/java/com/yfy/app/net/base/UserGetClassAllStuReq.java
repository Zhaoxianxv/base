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
@Root(name = TagFinal.USER_GET_CLASS_TO_ALL_STU, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"classid","termid"})
public class UserGetClassAllStuReq {

    @Namespace(reference = TagFinal.NAMESPACE)
    @Element(name = "session_key", required = false)
    public String session_key;     ///


    @Namespace(reference = TagFinal.NAMESPACE)
    @Element(name = "classid", required = false)
    public int classid;

    @Namespace(reference = TagFinal.NAMESPACE)
    @Element(name = "termid", required = false)
    public int termid;


    public void setClassid(int classid) {
        this.classid = classid;
    }

    public void setTermid(int termid) {
        this.termid = termid;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }



}

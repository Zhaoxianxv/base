package com.yfy.app.net.duty;

import com.yfy.final_tag.data.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.DUTY_TYPE, strict = false)
@Namespace(reference = TagFinal.NAMESPACE)
public class DutyTypeReq {
    @Namespace(reference = TagFinal.NAMESPACE)
    @Element(name = "session_key", required = false)
    private String session_key=Base.user.getSession_key();;

}
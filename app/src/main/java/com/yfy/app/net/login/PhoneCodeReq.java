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
@Root(name = TagFinal.USER_GET_CODE_TO_SYSTEM_PHONE, strict = false)
@Namespace(reference = TagFinal.NAMESPACE)
@Order(elements = {Base.session_key})
public class PhoneCodeReq {
    @Namespace(reference = TagFinal.NAMESPACE)
    @Element(name = "session_key", required = false)
    private String session_key= Base.user.getSession_key();     ///
}

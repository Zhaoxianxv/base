package com.yfy.app.net.maintain;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/5/22.
 */
@Root(name = TagFinal.MAINTAIN_SET_DELETE, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key, Base.id})
public class MaintainSetDeleteReq {
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key= Base.user.getSession_key();

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.id, required = false)
    private int id;

    public void setId(int id) {
        this.id = id;
    }
}

package com.yfy.app.net.maintain;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/27.
 */
@Root(name = TagFinal.MAINTAIN_USER_GET_LIST, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"dealstate", Base.page, Base.size})
public class MaintainUserGetListReq {


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key= Base.user.getSession_key();

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "dealstate", required = false)
    private String dealstate="0";

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "page", required = false)
    public int page;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "size", required = false)
    private int size= TagFinal.TEN_INT;


    public void setPage(int page) {
        this.page = page;
    }
}

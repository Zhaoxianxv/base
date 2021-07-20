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
@Root(name = TagFinal.MAINTAIN_ADMIN_GET_LIST, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"dealstate", Base.page, Base.size})
public class MaintainAdminGetListReq {


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "dealstate", required = false)
    private String dealstate;//

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.page, required = false)
    public int page;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.size, required = false)
    private int size;


    public String getDealstate() {
        return dealstate;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public void setDealstate(String dealstate) {
        this.dealstate = dealstate;
    }

    public void setPage(int page) {
        this.page = page;
    }
}

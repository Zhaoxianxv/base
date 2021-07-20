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
@Root(name = TagFinal.MAINTAIN_SET_ADD, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {
        Base.session_key,
        "dealuser",
        "date",
        "nr",
        "address",
        "username",
        "officename",
        "officeid",
        "goodsname",
        "goodsid",
        "pictures",
        "fileContext",
        "classid"})
public class MaintainSetAddReq {
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key= Base.user.getSession_key();

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "dealuser", required = false)
    private int dealuser;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "date", required = false)
    private String date;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "goodsid", required = false)
    private int goodsid;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "officeid", required = false)
    private int officeid;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "username", required = false)
    private String username;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "officename", required = false)
    private String officename;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "goodsname", required = false)
    private String goodsname;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "pictures", required = false)
    private String pictures;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "fileContext", required = false)
    private String fileContext;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "classid", required = false)
    private String classid;

}

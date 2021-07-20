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
@Root(name = TagFinal.MAINTAIN_SET_SECTION_MSG, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"realname","Maintainid","classid","userid","username","opearid","money","content"})
public class MaintainSetSectionReq {
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key= Base.user.getSession_key();

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "realname", required = false)
    private String realname="";

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "Maintainid", required = false)
    private int Maintainid;


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "classid", required = false)
    private int classid;


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "userid", required = false)
    private int userid=0;


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "username", required = false)
    private String username="";


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "opearid", required = false)
    private int opearid;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "money", required = false)
    private String money;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "content", required = false)
    private String content;

    public void setMoney(String money) {
        this.money = money;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public void setMaintainid(int maintainid) {
        Maintainid = maintainid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOpearid(int opearid) {
        this.opearid = opearid;
    }
}

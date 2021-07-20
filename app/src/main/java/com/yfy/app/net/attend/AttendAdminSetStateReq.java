package com.yfy.app.net.attend;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/27.
 */
@Root(name = TagFinal.ATTEND_ADMIN_SET_STATE, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key, Base.id,"reply","table_plan","review_result"})
public class AttendAdminSetStateReq {
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key= Base.user.getSession_key();
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.id, required = false)
    private String id;
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "reply", required = false)
    private String reply;
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "table_plan", required = false)
    private String table_plan;
    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "review_result", required = false)
    private int review_result;


    public void setId(String id) {
        this.id = id;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setTable_plan(String table_plan) {
        this.table_plan = table_plan;
    }

    public void setReview_result(int review_result) {
        this.review_result = review_result;
    }
}

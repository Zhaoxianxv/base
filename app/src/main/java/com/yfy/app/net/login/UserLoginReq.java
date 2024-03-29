package com.yfy.app.net.login;

import com.yfy.app.net.ElementToString;
import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.USER_LOGIN, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {"username","password","role_id","appid","andios","firsttoken"})
public class UserLoginReq extends ElementToString {

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "username", required = false)
    public String username;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "password", required = false)
    public String password;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "role_id", required = false)
    private String role_id;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "appid", required = false)
    private String appid;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "andios", required = false)
    private String andios="and";

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "firsttoken", required = false)
    private String firsttoken;


    public void setFirsttoken(String firsttoken) {
        this.firsttoken = firsttoken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole_id(String role_id) {
        this.role_id = role_id;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole_id() {
        return role_id;
    }

    public String getAppid() {
        return appid;
    }

    public String getAndios() {
        return andios;
    }

    public void setAndios(String andios) {
        this.andios = andios;
    }

    public String getFirsttoken() {
        return firsttoken;
    }
}

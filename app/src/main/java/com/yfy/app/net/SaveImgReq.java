package com.yfy.app.net;

import com.yfy.base.Base;
import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Order;
import org.simpleframework.xml.Root;

/**
 * Created by yfy on 2018/4/26.
 */
@Root(name = TagFinal.BASE_SAVE_IMG, strict = false)
@Namespace(reference = Base.NAMESPACE)
@Order(elements = {Base.session_key,"fileext","image_file"})
public class SaveImgReq extends ElementToString{


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = Base.session_key, required = false)
    private String session_key=Base.user.getSession_key();


    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "fileext", required = false)
    private String fileext;

    @Namespace(reference = Base.NAMESPACE)
    @Element(name = "image_file", required = false)
    private String image_file;


    public void setFileext(String fileext) {
        this.fileext = fileext;
    }

    public void setImage_file(String image_file) {
        this.image_file = image_file;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getFileext() {
        return fileext;
    }

    public String getImage_file() {
        return image_file;
    }
}

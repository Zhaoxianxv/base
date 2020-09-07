package com.yfy.app.net.footbook;

import com.yfy.final_tag.data.TagFinal;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by yfyandr on 2018/4/26.
 */
@Root(name = TagFinal.FOOT_BOOK_PRAISE +"Response")
public class FootBookPraiseRes {
    @Attribute(name = "xmlns", empty = TagFinal.NAMESPACE, required = false)
    public String nameSpace;

    @Element(name =  TagFinal.FOOT_BOOK_PRAISE +"Result", required = false)
    public String result;
}

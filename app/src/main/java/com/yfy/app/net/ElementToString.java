package com.yfy.app.net;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * Created by zxx.
 * Date: 2021/3/1
 */
public abstract class ElementToString {

    public static Strategy strategy = new AnnotationStrategy();
    public static Serializer serializer = new Persister(strategy);
    @Override
    public String toString() {

        OutputStream out = new ByteArrayOutputStream();
        try {
            serializer.write(this, out);
            String result = out.toString();
            out.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception";
        }

    }

}

package com.yfy.final_tag.stringtool;


import android.content.Context;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.Uri;


import com.yfy.base.Base;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;





public class StringUtils {


	//---------------------返回byte[]-----------------
	public static byte[] arraysByteToString(String content ){
		return content.getBytes();
	}
	public static String arraysByteToStringAt0(String content){
		if (StringJudge.isEmpty(content))return "";
		return content.substring(0,1);
	}


	//---------------------返回List<String>-----------------
	public static List<String> listToStringSplitCharacters(String content, String tag){
		List<String> list = Arrays.asList(content.split(stringToSpecialASCIICharacters(tag)));
		List<String> stringList=new ArrayList<>(list);
		if (StringJudge.isEmpty(content)){
			return new ArrayList<>();
		}else{
			return stringList;
		}
	}

	public static List<String> listToArrays(String[] content){
		if (content==null)return new ArrayList<>();
		List<String> list = Arrays.asList(content);
		List<String> stringList=new ArrayList<>(list);
		if (content.length==0){
			return new ArrayList<>();
		}else{
			return stringList;
		}
	}

	public static List<String> listToStringSplitCharactersHttp(String content, String tag, String http){
		List<String> list = Arrays.asList(content.split(stringToSpecialASCIICharacters(tag)));
		List<String> data = new ArrayList<>();
		for (String s:list){
			data.add(http+s);
		}
		return data;
	}
	//---------------------返回String[]-----------------

	public static String[] arraysToStringSplitCharacters(String content, String tag){
		List<String> list = Arrays.asList(content.split(stringToSpecialASCIICharacters(tag)));
		return arraysToListString(list);
	}

	public static String[] arraysToListString(List<String> list){
		String[] se=new String[list.size()];
		return list.toArray(se);
	}



	//---------------------返回String-----------------
	public static String stringToSpecialASCIICharacters(String name) {
		return Pattern.quote(name);
	}


	public static String listToStringSplitCharactersAt0(String content, String tag){
		if (StringJudge.isEmpty(content)){
			return "";
		}else{
			List<String> list = Arrays.asList(content.split(stringToSpecialASCIICharacters(tag)));
			List<String> stringList=new ArrayList<>(list);
			return stringList.get(0);
		}
	}
	public static String stringToUpJson(String name) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			switch (c) {
				case '\"':
					sb.append("\\\"");
					break;
				case '\n':
					sb.append("\\n");
					break;
				default:
					sb.append(c);
					break;

			}
		}
		return sb.toString();
	}

	public static String stringToGetParamsXml(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String s :list) {
			sb.append("<arr:string>")
					.append(s)
					.append("</arr:string>");
		}
		return  sb.toString();
	}



	public static String getTextJoint(String type,Object... args){
		return String.format(type,args);
	}

	public static String stringToGetTextJoint(String type, Object... args){
		return String.format(type,args);
	}

	public static String stringToImgToURlImg(String path){
//		Logger.e(path);
//		Logger.e(String.valueOf(path.indexOf("storage")));
		if (StringJudge.isEmpty(path)){
			return path;
		}
		String title=path.substring(0, 4);
		if (title.equalsIgnoreCase("http")){
			return path;
		}else{
			String title_sub=path.substring(1, 8);
			if (title_sub.equalsIgnoreCase("storage")){
				return path;
			}else{
				return Base.RETROFIT_URI+path;

			}
		}
	}



	public static String stringToByteArrayGetString(byte[] encrypt ){
		StringBuilder sb = new StringBuilder();
		for (byte name : encrypt) {
			sb.append(name);
		}
		String result = sb.toString();
		if (StringJudge.isEmpty(result))result="";
		return result;
	}

	public static String stringToArraysGetString(List<String> list, String tag) {
		StringBuilder sb = new StringBuilder();
		for (String name : list) {
			sb.append(name).append(tag);
		}
		String result = sb.toString();
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}


	public static boolean booleanToInclude(String content,String include){
		//返回指定字符在字符串中第一次出现处的索引，如果此字符串中没有这样的字符，则返回 -1。
//		System.out.print("查找字符 o 第一次出现的位置 :" );
//		System.out.println(Str.indexOf( 'o' ));
//		System.out.print("从第14个位置查找字符 o 第一次出现的位置 :" );
//		System.out.println(Str.indexOf( 'o', 14 ));
		if (StringJudge.isEmpty(content))return false;
		int i=content.indexOf(include);
		return i != -1;
	}

	/**
	 * 将子节数转换为Kb
	 */
	public static String convertBytesToOther(long byteSize) {
		String result = null;
		float size;

		DecimalFormat decimalFormat1 = new DecimalFormat(".0");
		DecimalFormat decimalFormat2 = new DecimalFormat(".00");

		if (byteSize < 1024) {
			result = byteSize + "B";
		} else {
			size = byteSize / 1024;
			if (size < 1024) {
				result = decimalFormat1.format(size) + "KB";
			} else {
				size = size / 1024;
				if (size < 1024) {
					result = decimalFormat2.format(size) + "M";
				} else {
					size = size / 1024;
					if (size < 1024) {
						result = decimalFormat2.format(size) + "G";
					}
				}
			}
		}
		return result;
	}

	public static int getBitmapDegree(String path) {
		int degree = 0;
		try {
			// 从指定路径下读取图片，并获取其EXIF信息
			ExifInterface exifInterface = new ExifInterface(path);
			// 获取图片的旋转信息
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	public static int getRecordDuration(Context context, String path) {
		MediaPlayer mp = MediaPlayer.create(context, Uri.parse(path));
		int duration = mp.getDuration();
		mp.release();
		mp = null;
		return duration;
	}

	public static String getFileName(String path) {

		File file = new File(path);
		if (file.exists()) {
			return file.getName();
		}
		return "";
	}

	public static String getFileName2(String pathandname) {

		int start = pathandname.lastIndexOf("/");
		int end = pathandname.lastIndexOf(".");
		if (start != -1 && end != -1) {
			return pathandname.substring(start + 1, end);
		} else {
			return null;
		}

	}
	
}

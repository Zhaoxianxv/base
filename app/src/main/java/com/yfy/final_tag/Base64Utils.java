package com.yfy.final_tag;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import com.yfy.final_tag.data.TagFinal;
import com.yfy.final_tag.glide.Photo;
import com.yfy.final_tag.stringtool.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @version 1.0
 * @Desprition
 */
public class Base64Utils {
	public final static String TAG = Base64Utils.class.getSimpleName();

	/**
	 * ----------------------修改文件名称--------------
	 */

	public static String getZipTitleNotice(List<Photo> photoList) {
		StringBuilder sb = new StringBuilder();
		for (Photo photo : photoList) {
			sb.append(photo.getFileName()).append("^").append("|");
		}
		//保留”^“
		String result = sb.toString();
		if (result.length() > 0) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String filesToZipBase64Notice(List<Photo> photoList) {
		String base64Str = "";
		ZipOutputStream zos = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);
			for (Photo photo : photoList) {
				Bitmap bitmap = ratio(photo.getPath(), 480f, 800f);
				Bitmap bitmap_ang=rotaingImageView(readPictureDegree(photo.getPath()),bitmap);
				addStreamToZip(zos, getStreamFromBitmap(bitmap_ang),photo.getFileName());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		base64Str = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
		if (base64Str.length() == 0) {
			base64Str = "";
		}
		return base64Str;
	}


	/**
	 * 单图片压缩转base64Str
	 */
	public static String fileToBase64Str(String path) {
		int inSampleSize = 7;//采样率设置
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		options.inSampleSize = inSampleSize;

		Bitmap bitmap = ratio(path, 1000f);
//		Bitmap bitmap = BitmapFactory.decodeFile(path, options);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);//quality  图片精度
		byte[] bytes = bos.toByteArray();
		//base64 encode
		byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
		return  new String(encode);
	}










	public static String getNameFromPath(String path) {
		String[] strs = path.split("/");
		return changToJpg(strs[strs.length - 1]);
	}

	private static String changToJpg(String s) {
		String[] strs = s.split("\\.");
		return strs[0] + ".jpg";
	}


	public static String getZipBase64Str(String path) {
		String base64Str = "";
		File file = new File(path);
		FileInputStream inputFile = null;
		ZipOutputStream zos = null;
		ByteArrayOutputStream bos = null;
		byte[] buffer = null;
		try {
			inputFile = new FileInputStream(file);
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);

			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);

			addBytesToZip(zos, buffer, StringUtils.getFileName(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputFile != null) {
					inputFile.close();
				}
				if (zos != null) {
					zos.close();
				}
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		base64Str = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
		if (base64Str.length() == 0) {
			base64Str = "";
		}
		return base64Str;
	}

	public static void addBytesToZip(ZipOutputStream zos, byte[] buf, String name) {
		try {
			ZipEntry entry = new ZipEntry(name);
			zos.putNextEntry(entry);
			zos.write(buf);
			zos.closeEntry();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String encodeBase64File(String path) {
		File file = new File(path);
		FileInputStream inputFile = null;
		byte[] buffer = null;
		try {
			inputFile = new FileInputStream(file);
			buffer = new byte[(int) file.length()];
			inputFile.read(buffer);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputFile != null) {
					inputFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return Base64.encodeToString(buffer, Base64.DEFAULT);
	}

	public static String filesToZipBase64(List<String> pathList, String curPath) {
		String base64Str = "";
		ZipOutputStream zos = null;
		ByteArrayOutputStream bos = null;
		try {
			bos = new ByteArrayOutputStream();
			zos = new ZipOutputStream(bos);
			for (String path : pathList) {
				Bitmap bitmap = ratio(path, 1000f);
				Bitmap bitmap_ang=rotaingImageView(readPictureDegree(path),bitmap);
				addStreamToZip(zos, getStreamFromBitmap(bitmap_ang), curPath + getNameFromPath(path));
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (zos != null) {
				try {
					zos.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		base64Str = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT);
		if (base64Str.length() == 0) {
			base64Str = "";
		}
		return base64Str;
	}





	public static void addStreamToZip(ZipOutputStream zos, ByteArrayOutputStream bos, String name) {

		byte[] buf = bos.toByteArray();
		try {
			ZipEntry entry = new ZipEntry(name);
			zos.putNextEntry(entry);
			zos.write(buf);
			zos.closeEntry();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}






//	/**
//	 * 单图片转base64Str
//	 */
//	public static String fileToBase64Str(String path) {
//		//decode to bitmap
//		Bitmap bitmap = ratio(path, 480f, 800f);
////		Bitmap bitmap = BitmapFactory.decodeFile(path);
//		//convert to byte array
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//		byte[] bytes = baos.toByteArray();
//		//base64 encode
//		byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
//		return  new String(encode);
//	}






	private static ByteArrayOutputStream getStreamFromBitmap(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);
		int p = 100;
		while (bos.toByteArray().length > TagFinal.UPLOAD_LIMIT) {
			bos.reset();
			p -= 10;
			bitmap.compress(Bitmap.CompressFormat.JPEG, p, bos);
		}

		if (!bitmap.isRecycled()) {
			bitmap.recycle();
		}

		return bos;
	}

	//获取bitmap

	public static Bitmap ratio(String imgPath, float pixelW, float pixelH) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		newOpts.inPreferredConfig = Config.RGB_565;
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		int be = 1;
		if (w > h && w > pixelW) {
			be = (int) (newOpts.outWidth / pixelW);
		} else if (w < h && h > pixelH) {
			be = (int) (newOpts.outHeight / pixelH);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;
		return BitmapFactory.decodeFile(imgPath, newOpts);
	}
	//获取bitmap

	public static Bitmap ratio(String imgPath, float pixelW) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		newOpts.inJustDecodeBounds = true;
		newOpts.inPreferredConfig = Config.RGB_565;
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		//        float hh = pixelH;// 设置高度为240f时，可以明显看到图片缩小了
		//        float ww = pixelW;// 设置宽度为120f，可以明显看到图片缩小了
		int be = 1;//be=1表示不缩放:> 1，请求解码器对原始文件进行子采样

		//      if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
		//            be = (int) (newOpts.outWidth / ww);
		//        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
		//            be = (int) (newOpts.outHeight / hh);
		//        }
		if (w > pixelW) {
			be = (int) (w / pixelW);
		}
		if (be <= 0) be = 1;
		newOpts.inSampleSize = be;
		return BitmapFactory.decodeFile(imgPath, newOpts);
	}


	/**
	 * 读取照片旋转角度
	 *
	 * @param path 照片路径
	 * @return 角度
	 */
	private static int readPictureDegree(String path) throws IOException{
		int degree = 0;
		ExifInterface exifInterface = new ExifInterface(path);
		int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
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
		return degree;
	}

	/**
	 * 旋转图片
	 * @param angle 被旋转角度
	 * @param bitmap 图片对象
	 * @return 旋转后的图片
	 */
	private static Bitmap rotaingImageView(int angle, Bitmap bitmap) throws OutOfMemoryError{
		// 根据旋转角度，生成旋转矩阵
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 将原始图片按照旋转矩阵进行旋转，并得到新的图片
		Bitmap returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

		if (returnBm == null) {
			returnBm = bitmap;
		}
		if (bitmap != returnBm) {
			bitmap.recycle();
		}
		return returnBm;
	}



}

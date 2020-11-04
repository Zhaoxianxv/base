package com.yfy.app.voice.unite;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CurrentTime {
	
	private SimpleDateFormat dateFormat;
	
	public static String getCurrentTime() {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss EEEE",Locale.getDefault());
		//Date date = new Date(System.currentTimeMillis());
		String timeString = dateFormat.format(new Date());
		
		/*Calendar calendar =Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		String mWeek = String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));  
        if("1".equals(mWeek)){  
        	mWeek ="��";  
        }else if("2".equals(mWeek)){  
        	mWeek ="һ";  
        }else if("3".equals(mWeek)){  
        	mWeek ="��";  
        }else if("4".equals(mWeek)){  
        	mWeek ="��";  
        }else if("5".equals(mWeek)){  
        	mWeek ="��";  
        }else if("6".equals(mWeek)){  
        	mWeek ="��";  
        }else if("7".equals(mWeek)){  
        	mWeek ="��";  
        }  
		return timeString + mWeek;*/
		
		return timeString;
	}
	
	/*//�ж���AM����PM
	private String getAMorPM(){
		String am_pm=Calendar.getInstance().getTime().getHours() >= 12 ? "PM" : "AM";
		return am_pm;
	}*/
}

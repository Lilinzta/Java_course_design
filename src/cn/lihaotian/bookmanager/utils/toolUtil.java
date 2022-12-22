package cn.lihaotian.bookmanager.utils;

import cn.lihaotian.bookmanager.model.User;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

public class toolUtil {
	public static boolean isEmpty(String str){
		return str == null || "".equals(str.trim());
	}
	
	public static Long getTime(){
		return System.currentTimeMillis();
	}

	
	public static String getDateByTime(Long time){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}
	public static User getUser(HttpSession session){
		return (User) session.getAttribute("user");
	}
	public static void setUser(HttpSession session,User user){
		session.setAttribute("user", user);
	}
}

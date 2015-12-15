package com.loveskill.statediagramview.utils;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/** 涓庤澶囧垎杈ㄧ巼鐩稿叧鐨勫伐鍏风被
 * @author linwenxiong
 *
 */
public class ViewUtils {

	public static int mScreenWidth=-1,mScreenHeigh=-1;
	
	
	/**
	 * 鑾峰彇灞忓箷瀹藉害
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowWidth(Context context) {
		if( mScreenWidth <= 0 )
		{
			WindowManager wm = (WindowManager) (context
					.getSystemService(Context.WINDOW_SERVICE));
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			 mScreenWidth = dm.widthPixels;
		}
		return mScreenWidth;
	}

	/**
	 * 鑾峰彇灞忓箷楂樺害
	 * 
	 * @param context
	 * @return
	 */
	public static int getWindowHeigh(Context context) {

		if( mScreenHeigh <=0 )
		{
			WindowManager wm = (WindowManager) (context
					.getSystemService(Context.WINDOW_SERVICE));
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			 mScreenHeigh = dm.heightPixels;
		}
		return mScreenHeigh;
	}

//	/**
//	 * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠�dp 鐨勫崟浣�杞垚涓�px(鍍忕礌)
//	 */
//	public static int dip2px(float dpValue) {
//		final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
//		return (int) (dpValue * scale + 0.5f);
//	}

//	/**
//	 * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠�px(鍍忕礌) 鐨勫崟浣�杞垚涓�dp
//	 */
//	public static int px2dip(float pxValue) {
//		final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().density;
//		return (int) (pxValue / scale + 0.5f);
//	}

	/**
	 * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠�dp 鐨勫崟浣�杞垚涓�px(鍍忕礌)
	 */
	public static int dip2px(Context context,float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠�px(鍍忕礌) 鐨勫崟浣�杞垚涓�dp
	 */
	public static int px2dip(Context context,float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}

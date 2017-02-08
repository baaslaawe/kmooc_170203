package com.nile.elementlocators;

public interface IFindCourseLocators_Offline {
	
	String getLNPFindCoursesId();

	String getFindCourseName();

	String getHeaderId();

	public String getOfflineModeTextId();

	public String getOfflineBarId();

	public String getOfflineModeLabelId();
	
	//Android id's
	
	String Android_lnpFindCoursesId="com.nile.kmooc:id/drawer_option_find_courses";
	String Android_FindCoursesName="Find Courses";
	String Android_HeaderId="android:id/up";
	
	String Android_OfflineModeTextId="com.nile.kmooc:id/offline_mode_message";
	
	String Android_OfflineBarId="com.nile.kmooc:id/offline_bar";
	
	String Android_OfflineModeLabelId="com.nile.kmooc:id/offline_tv";

}

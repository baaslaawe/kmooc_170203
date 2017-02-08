/**
 * 
 */
package com.nile.elementlocators;

/**
 * @author divakarpatil
 * 
 */
public interface IMyVideosLocators_Offline {

	public String getOfflineBarId();

	public String getOfflineLabelName();

	public String getTxtMyVideosId();

	public String getHeaderId();

	public String getPlayPauseId();

	public String getSeekBarId();

	public String getVideoPlayerId();

	public String getRewindId();

	public String getSettingsPopUpId();

	public String getFullScreenId();

	public String getLMSId();

	public String getLstVideoId();

	public String getLstCourseId();

	public String getTxtAllVideosName();

	public String getTxtRecentVideosName();

	public String getOkPopupId();

	public String getBtnDeleteId();

	public String getCbVideoSelectId();

	public String getBtnEditId();

	public void gotoMyVideosView();

	String getVideoPlayerSettings();

	String getCourseListId();

	String getHeaderNameId();
	
	boolean isAndroid();
	
	String getTxtMyVideosName();
	
	public String getBtnDeletePopupId();

	// Android Locators
	String btnHeaderId = "android:id/up";
	String btnHeaderNameId = "android:id/action_bar_title";
	String btnCourseId = "com.nile.kmooc:id/course_row_layout";
	String txtMyVideosName = "My Videos";
	String txtMyVideosId = "com.nile.kmooc:id/drawer_option_my_videos";
	String txtAllVideosName = "All Videos";

	String btnOkPopupId = "com.nile.kmooc:id/positiveButton";
	String btnCancelPopupId = "com.nile.kmooc:id/negativeButton";
	String btnLMS = "com.nile.kmooc:id/lms_btn";
	String btnPlayPause = "com.nile.kmooc:id/pause";
	String btnRewind = "com.nile.kmooc:id/rew";
	String btnSettings = "com.nile.kmooc:id/settings";
	String btnFullScreenId = "com.nile.kmooc:id/fullscreen";
	String vpVideoPlayerId = "com.nile.kmooc:id/preview";

	String btnViewId = "com.nile.kmooc:id/button_view";

	String txtRecentVideosName = "Recent Videos";
	String lstVideoId = "com.nile.kmooc:id/video_row_layout";
	String btnEditId = "com.nile.kmooc:id/edit_btn";
	String btnDeleteId = "com.nile.kmooc:id/delete_btn";
	String btnCancelId = "com.nile.kmooc:id/cancel_btn";
	String cbAllSelectId = "com.nile.kmooc:id/select_checkbox";
	String cbVideoSelectId = "com.nile.kmooc:id/video_select_checkbox";
	String lstAllVideos_Courses = "com.nile.kmooc:id/my_video_course_list";
	String lstRecentVideos = "com.nile.kmooc:id/list_video";
	
	String offlineBarId = "com.nile.kmooc:id/offline_bar";
	

	// iOS Id's
	String btnHeaderIdiOS = "btnNavigation";
	String btnHeaderNameIdiOS = "myVideosHeader";
	String btnCourseIdiOS = "";
	String txtMyVideosNameiOS = "My Videos";
	String txtMyVideosIdiOS = "txtMyVideosLNP";
	String txtAllVideosNameiOS = "ALL VIDEOS";

	String btnOkPopupIdiOS = "Delete";
	String btnCancelPopupIdiOS = "Cancel";
	String btnLMSiOS = "btnLMS";
	String btnPlayPauseiOS = "btnPlayPause";
	String btnRewindiOS = "btnRewind";
	String btnSettingsiOS = "btnSettings";
	String btnFullScreenIdiOS = "btnFullScreen";
	String vpVideoPlayerIdiOS = "Video";

	String txtRecentVideosNameiOS = "RECENT VIDEOS";
	String lstVideoIdiOS = "lbVideoName";
	String btnEditIdiOS = "btnEdit";
	String btnDeleteIdiOS = "btnDelete";
	String btnCancelIdiOS = "btnCancel";
	String cbAllSelectIdiOS = "";
	String cbVideoSelectIdiOS = "btnCheckBoxDelete";
	String lstAllVideos_CoursesiOS = "lbCourseTitle";
	String lstRecentVideosiOS = "";
	String offlineBarIdiOS = "offline";

	String txtOfflineName = "OFFLINE MODE";
	String btnDeletePopupIdiOS = "Delete";
	
	
}

/**
 * 
 */
package com.nile.elementlocators;

/**
 * @author divakarpatil
 * 
 */
public interface IMyVideosLocators {
	
	public String getTxtMySettingsId();
	
	public String getTxtMyVideosName();

	public String getHeaderNameId();

	public String getOkPopupId();

	public String getSettingsBtnId();

	public String getSectionSubsectionDownloadId();

	public String getCourseListId();

	public String getSubmitFeedBackId();

	public String getHeaderId();

	public String getTxtAllVideosName();

	public String getTxtRecentVideosName();

	public String getTxtMyVideosId();

	public String getLstVideoId();

	public String getLstCourseId();

	public String getLstDownloadId();

	public String getBtnDownloadScreenId();

	public String getBtnDeleteId();

	public String getCbVideoSelectId();

	public String getBtnEditId();

	public String getEmailLocatorId();

	public boolean isAndroid();

	public String getPasswordLocatorId();

	public String getSignInLocatorId();

	public String getEmailId();

	public String getUserNameId();

	public String getVersion();

	public String getLogoutId();

	public String getPlayPauseId();

	public String getLMSId();

	public String getRewindId();

	public String getFullScreenId();

	public String getVideoPlayerSettings();

	public String getVideoPlayerId();

	public void gotoMyVideosView();

	public String getSeekBarId();

	public String getVideoHeaderId();

	public String getSettingsPopUpId();

	public String getBtnDeletePopupId();

	/*
	 * Android id's
	 */

	// Login id
	String btnLogOutId = "com.nile.kmooc:id/logout_button";
	String tbPasswordId = "com.nile.kmooc:id/password_et";
	String btnSigninId = "com.nile.kmooc:id/login_button_layout";
	String tbEmailId = "com.nile.kmooc:id/email_et";

	// Header id
	String btnHeaderId = "android:id/up";
	String btnHeaderNameId = "android:id/action_bar_title";

	// LeftNavigationPanel
	String txtMyVideosName = "My Videos";
	String txtFindCoursesId="com.nile.kmooc:id/drawer_option_find_courses";
	String txtMyVideosId = "com.nile.kmooc:id/drawer_option_my_videos";
	String txtAllVideosName = "All Videos";
	String txtMySettingsId="com.nile.kmooc:id/drawer_option_my_settings";
	String btnSubmitFeedBackId = "com.nile.kmooc:id/drawer_option_submit_feedback";
	String btnOkPopupId = "com.nile.kmooc:id/positiveButton";
	String btnCancelPopupId = "com.nile.kmooc:id/negativeButton";
	String txtVersion = "com.nile.kmooc:id/tv_version_no";
	String txtUserNameId = "com.nile.kmooc:id/name_tv";
	String txtEmailId = "com.nile.kmooc:id/email_tv";
	
	//Video player
	String btnLMS = "com.nile.kmooc:id/lms_btn";
	String btnPlayPause = "com.nile.kmooc:id/pause";
	String btnRewind = "com.nile.kmooc:id/rew";
	String btnSettings = "com.nile.kmooc:id/settings";
	String btnFullScreenId = "com.nile.kmooc:id/fullscreen";
	String vpVideoPlayerId = "com.nile.kmooc:id/preview";

	String btnViewId = "com.nile.kmooc:id/button_view";

	String btnCourseId = "com.nile.kmooc:id/course_row_layout";

	String txtRecentVideosName = "Recent Videos";
	String lstVideoId = "com.nile.kmooc:id/video_row_layout";
	String btnEditId = "com.nile.kmooc:id/edit_btn";
	String btnDeleteId = "com.nile.kmooc:id/delete_btn";
	String btnCancelId = "com.nile.kmooc:id/cancel_btn";
	String cbAllSelectId = "com.nile.kmooc:id/select_checkbox";
	String cbVideoSelectId = "com.nile.kmooc:id/video_select_checkbox";
	String btnDownloadScreenId = "com.nile.kmooc:id/down_arrow";
	String lstDownloadVideosId = "com.nile.kmooc:id/downloads_row_layout";
	String btnSettingsId = "com.nile.kmooc:id/wifi_setting";
	String lstAllVideos_Courses = "com.nile.kmooc:id/my_video_course_list";
	String lstRecentVideos = "com.nile.kmooc:id/list_video";
	String intVideoCount = "com.nile.kmooc:id/no_of_videos";

	String btnSectionSubsectionDownloadId = "com.nile.kmooc:id/bulk_download";

	// iOS Id's
	String btnHeaderIdiOS = "btnNavigation";
	String btnHeaderNameIdiOS = "myVideosHeader";
	String btnCourseIdiOS = "lbCourseTitle";

	String tbEmailIdiOS = "tbUserName";
	String tbPasswordIdiOS = "tbPassword";
	String btnSigninIdiOS = "btnSignIn";

	// Left Navigation Bar
	String txtUserNameIdiOS = "lbUser";
	String txtEmailIdiOS = "lbEmail";
	String txtMyVideosNameiOS = "My Videos";
	String txtMySettingsIdiOS="";
	String txtAllVideosNameiOS = "ALL VIDEOS";
	String btnSubmitFeedBackIdiOS = "txtSubmitFeedBackLNP";
	String btnSettingsIdiOS = "lbSettingsLNP";
	String txtDownloadsiOS = "Download only on Wi-Fi";

	String btnLogOutIdiOS = "btnLogout";
	String txtVersioniOS = "lbVersion";
	String btnSwitchiOS = "btnSwitch";
	String btnOkPopupIdiOS = "ALLOW";
	String btnCancelPopupIdiOS = "Cancel";
	String txtMyVideosIdiOS = "txtMyVideosLNP";

	String txtRecentVideosNameiOS = "RECENT VIDEOS";
	String lstVideoIdiOS = "lbVideoName";
	String btnEditIdiOS = "btnEdit";
	String btnDeleteIdiOS = "btnDelete";
	String btnCancelIdiOS = "btnCancel";
	String cbAllSelectIdiOS = "btnSelectAllCheckBox";
	String cbVideoSelectIdiOS = "btnCheckBoxDelete";
	String btnDeletePopupIdiOS = "Delete";

	String vpVideoPlayerIdiOS = "Video";
	String btnLMSiOS = "btnLMS";
	String btnPlayPauseiOS = "btnPlayPause";
	String btnRewindiOS = "btnRewind";
	String btnSettingsiOS = "btnSettings";
	String btnFullScreenIdiOS = "btnFullScreen";
	String lbVideoHeaderIdiOS = "lbVideoTitle";

}

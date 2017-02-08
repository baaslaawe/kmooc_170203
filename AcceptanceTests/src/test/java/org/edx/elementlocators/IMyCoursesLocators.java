/**
 * 
 */
package com.nile.elementlocators;

/**
 * @author divakarpatil
 * 
 */
public interface IMyCoursesLocators {
	
	public String getFindCourseWebView();
	
	public String getFindCourseHeaderName();

	public String getMySettingsId();
	
	public String getShowingOnlyVideosName();

	public String getOkPopupId();
	
	public String getAnnouncementsName();

	public String getSettingsBtnId();

	public String getWebLinkId();

	public String getMyCoursesHeaderId();

	public String getTxtMyCourseName();

	public String getHeaderNameId();

	public String getLstDownloadId();

	public String getTxtMyVideosName();

	public String getBtnViewId();

	public String getLogoutId();

	public String getViewOnWebId();

	public String getHandoutsName();

	public String getCourseInfoName();

	public String getDownloadMessage();

	public void gotoMyCoursesView() throws Throwable;

	public String getSignInLocatorId();

	public String getPasswordLocatorId();

	public String getEmailLocatorId();

	public String getMyCoursesName();

	public String getVideoPlayerId();

	public String getCCPopUpCancelId();

	public String getSettingsPopUpId();

	public String getCCPopUpId();

	public String getVideoListId();

	public String getSectionSubsectionListId();

	public String getCourseListId();

	public String getMyCourseId();

	public String getHeaderId();

	public String getDownloadScreenCancelBtnId();

	public String getDownloadScreenId();

	public String getSectionSubsectionDownloadId();

	public String getLastAccessedBtnId();

	public boolean isAndroid();

	public String getPlayPauseId();

	public String getLMSId();

	public String getRewindId();

	public String getFullScreenId();

	public String getVideoPlayerSettings();

	public String getFindACourseBtnId();

	public String getLnkFindCourseName();

	public String getTxtLookingForChallenge();

	public String getCourseWareErrorText();

	public String getCourseWareErrorId();

	public String getCloseId();

	public String getDontSeeOneOfCoursesId();

	public String getSeekBarId();

	public String getVideoHeaderId();

	public String getVideoName();

	public String getVideoSize();

	public String getVideoLength();

	public String getTxtFindACourseName();

	public String getMyVideosId();

	/*
	 * Android Id's
	 */

	// Login Id's
	String tbEmailId = "com.nile.kmooc:id/email_et";
	String tbPasswordId = "com.nile.kmooc:id/password_et";
	String btnSigninId = "com.nile.kmooc:id/login_button_layout";
	String btnLogOutId = "com.nile.kmooc:id/logout_button";

	// Header Id's
	String btnHeaderId = "android:id/up";
	String btnHeaderNameId = "android:id/action_bar_title";

	// Find A Course id's
	String btnFindACourseId = "com.nile.kmooc:id/course_btn";
	String btnDontSeeCoursesId = "com.nile.kmooc:id/course_not_listed_tv";
	String btnCloseId = "com.nile.kmooc:id/positiveButton";
	String lnkFindACourseName = "https://www.edx.org/course-search?type=mobile";
	String txtLookingForChallengeName = "Looking for a new challenge?";

	// Navigation through the course to the video id's
	String btnCourseId = "com.nile.kmooc:id/course_row_layout";
	String btnSectionSubsectionDownloadId = "com.nile.kmooc:id/bulk_download_layout";
	String btnSectionSubsectionId = "com.nile.kmooc:id/chapter_row_layout";
	String btnCourseWareName = "Courseware";
	String btnCourseInfoName = "Course Info";
	String btnAnnouncementsName="Announcements";
	String btnHandOutsName = "View course handouts";
	String hlnkViewOnWebId = "com.nile.kmooc:id/open_in_browser_btn";
	String btnVideoId = "com.nile.kmooc:id/video_row_layout";
	String btnVideoDownloadId = "com.nile.kmooc:id/video_start_download";

	// Download Screen, Download Message Id's
	String btnDownloadScreenId = "com.nile.kmooc:id/down_arrow";
	String btnDownloadScreenCancelId = "com.nile.kmooc:id/close_btn";
	String dlgLargeDownloadsId = "com.nile.kmooc:id/dialog_layout";
	String lbVideoName = "com.nile.kmooc:id/video_title";
	String lbVideoSize = "com.nile.kmooc:id/video_size";
	String lbVideoLength = "com.nile.kmooc:id/video_playing_time";
	String btnViewId = "com.nile.kmooc:id/button_view";
	String lstDownloadVideosId = "com.nile.kmooc:id/downloads_row_layout";
	String msgDownloadId = "com.nile.kmooc:id/flying_message";
	String downloadProgressWheel = "com.nile.kmooc:id/progress_wheel";

	// Video player Id's
	String vpVideoPlayerId = "com.nile.kmooc:id/preview";
	String lbVideoNameVideoPlayerId = "com.nile.kmooc:id/video_title";
	String btnLMS = "com.nile.kmooc:id/lms_btn";
	String btnPlayPause = "com.nile.kmooc:id/pause";
	String btnRewind = "com.nile.kmooc:id/rew";
	String btnSettings = "com.nile.kmooc:id/settings";
	String btnFullScreenId = "com.nile.kmooc:id/fullscreen";
	String popupCC = "com.nile.kmooc:id/tv_closedcaption";
	String popupLanguages = "com.nile.kmooc:id/row_cc_lang";
	String txtSubtitlesId = "com.nile.kmooc:id/txtSubtitles_tv";
	String popupLanguagesCancel = "com.nile.kmooc:id/tv_cc_cancel";

	// No CourseWare available id
	String lbCourseWareId = "com.nile.kmooc:id/no_chapter_tv";
	String lbCourseWareName = "No courseware is currently available.";

	// Last Accessed button Id
	String btnLastAccessedId = "com.nile.kmooc:id/last_viewed_tv";

	// Left Navigation Panel id's
	String txtMyCourseId = "com.nile.kmooc:id/drawer_option_my_courses";
	String txtMyVideosName = "My Videos";
	String txtMySettingsId="com.nile.kmooc:id/drawer_option_my_settings";
	String txtCellularDownloadName="ALLOW CELLULAR DOWNLOAD";
	String txtCellularDownload1Name="Allow your device to download videos over your cellular connection when" 
+"Wi-Fi is not available. Data charges may apply.";
	String btnOkPopupId = "com.nile.kmooc:id/positiveButton";
	String btnSettingsId = "com.nile.kmooc:id/wifi_setting";
	
	//Find Courses
	String txtFindCourseName="Find Courses";
	String findACoursewebView="com.nile.kmooc:id/webview";
	

	/*
	 * IOS Locators id's
	 */

	// Login Locator id
	String tbEmailIdiOS = "tbUserName";
	String tbPasswordIdiOS = "tbPassword";
	String btnSigninIdiOS = "btnSignIn";
	String btnLogOutIdiOS = "btnLogout";

	// Header id
	String btnHeaderIdiOS = "btnNavigation";
	String btnHeaderNameIdiOS = "txtHeader";// Header id for all the screen
											// except the My Courses screen
	String headerMyCoursesIdiOS = "myCoursesHeader";

	// Find A Course id's
	String btnFindAMobileCourseiOS = "btnFindACourse";
	String txtDontSeeACourseiOS = "btnDontSeeCourse";
	String txtLookingForCourseiOS = "Looking for a new challenge?";
	String btnCloseIdiOS = "btnClose";

	// Navigation to the video through the course id's
	String btnCourseIdiOS = "lbCourseTitle";
	String btnSectionSubsectionIdiOS = "lbSectionSubsection";
	String btnCourseWareNameiOS = "COURSEWARE";
	String btnAnnouncementsNameiOS = "ANNOUNCEMENTS";
	String btnHandOutsNameiOS = "HANDOUTS";
	String btnViewOnWebIdiOS = "btnViewOnWeb";
	String hlnkViewOnWebIdiOS = "VIEW ON WEB";
	String btnVideoIdiOS = "lbVideoName";
	String lbNoOfVideos = "lbVideoNumbers";

	// Download Screen id's
	String btnSectionSubsectionDownloadIdiOS = "btnVideosDownload";
	String btnDownloadScreenIdiOS = "btnDownloadScreen";
	String lbVideoNameiOS = "lbVideoName";
	String lbVideoSizeiOS = "lbVideoSize";
	String lbVideoLengthiOS = "lbVideoLength";
	String btnDownloadScreenCancelIdiOS = "btnVideoDownloadCancel";
	String btnViewIdiOS = "btnDownloadView";
	String btnVideoDownloadIdiOS = "btnVideosDownload";
	String dlgLargeDownloadsIdiOS = "";
	String msgDownloadIdiOS = "floatingMessages";

	// Video player Id's
	String vpVideoPlayerIdiOS = "Video";
	String btnLMSiOS = "btnLMS";
	String btnPlayPauseiOS = "btnPlayPause";
	String btnRewindiOS = "btnRewind";
	String btnSettingsiOS = "btnSettings";
	String btnFullScreenIdiOS = "btnFullScreen";
	String popupCCiOS = "";
	String popupLanguagesiOS = "";
	String popupLanguagesCanceliOS = "";

	// Last Accessed button id
	String btnLastAccessedIdiOS = "btnLastAccessed";

	// Left Navigation Panel Id
	String txtMyVideosNameiOS = "My Videos";
	String txtMyVideosIdiOS = "myVideosHeader";
	String txtMyCourseIdiOS = "txtMyCoursesLNP";
	String btnSwitchiOS = "btnSwitch";
	String btnOkPopupIdiOS = "ALLOW";
	String txtMySettingsIdiOS="";

	/* Common Locators */
	String txtMyCourseName = "My Courses";
	String lbCourseWareIdiOS = "txtNoCourseWareAvailable";
	String txtFindACourseName = "FIND A MOBILE-FRIENDLY COURSE";
	String txtShowingOnlyVideos = "Showing only Videos";

}

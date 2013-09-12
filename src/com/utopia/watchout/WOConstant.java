package com.utopia.watchout;

/**
 * Created by Doheum on 13. 9. 3.
 */
public class WOConstant {

    public static class IntentKey {

        private static final String ACTION_PREFIX = "com.vingle.intent.action.";

        public static final String ACTION_SHOW_PARTY = ACTION_PREFIX + "SHOW_PARTY";
        public static final String ACTION_SHOW_COLLECTION = ACTION_PREFIX + "SHOW_COLLECTION";
        public static final String ACTION_SHOW_EXPLORE = ACTION_PREFIX + "SHOW_EXPLORE";
        public static final String ACTION_SHOW_USER = ACTION_PREFIX + "SHOW_USER";
        public static final String ACTION_SHOW_CARD = ACTION_PREFIX + "SHOW_CARD";
        public static final String ACTION_SHOW_HOME = ACTION_PREFIX + "SHOW_HOME";
        public static final String ACTION_SHOW_FEATURE = ACTION_PREFIX + "SHOW_FEATURE";
        public static final String ACTION_SHOW_SIGN = ACTION_PREFIX + "SHOW_SIGN";
        public static final String ACTION_SHOW_NOTIFICATION = ACTION_PREFIX + "SHOW_NOTIFICATION";
        public static final String ACTION_SHOW_SETTING = ACTION_PREFIX + "SHOW_SETTING";
        public static final String ACTION_SHOW_FIND_FRIENDS = ACTION_PREFIX + "SHOW_FIND_FRIENDS";
        public static final String ACTION_GOOGLE_PLUS_DEEP_LINK = "com.google.android.apps.plus.VIEW_DEEP_LINK";

    }// end of inner class

    public static class BundleKey {

        // Card
        public static final String EXTRA_CARD_ID = "card_id";
        public static final String EXTRA_SHOW_LOAD = "show_load";
        public static final String EXTRA_CARD_TITLE = "card_title";
        public static final String EXTRA_VIDEO_ID = "video_id";

        // Collection
        public static final String EXTRA_COLLECTION_ID = "collection_id";
        public static final String EXTRA_COLLECTION_TITLE = "collection_title";

        // Party
        public static final String EXTRA_PARTY_ID = "party_id";
        public static final String EXTRA_PARTY_TITLE = "party_title";

        // Language
        public static final String EXTRA_LANGUAGE_CODE = "language_code";
        public static final String EXTRA_LANGUAGE = "language";

        // Keyword (Category)
        public static final String EXTRA_KEYWORD_ID = "keyword_id";
        public static final String EXTRA_KEYWORD_TITLE = "keyword_title";

        // User
        public static final String EXTRA_USER_ID = "user_id";
        public static final String EXTRA_USERNAME = "username";
        public static final String EXTRA_EMAIL = "email";
        public static final String EXTRA_UID = "uid";

        // Dialog
        public static final String EXTRA_MESSAGE = "message";
        public static final String EXTRA_URL = "url";
        public static final String EXTRA_TITLE = "title";

        // GridView Header Layout Id & Header Height
        public static final String EXTRA_HEADER_ID = "header_id";
        public static final String EXTRA_HEADER_HEIGHT = "header_height";

        // Google Analytics
        public static final String EXTRA_GA_ID = "ga_id";
        public static final String EXTRA_GA_TITLE = "ga_title";
        public static final String EXTRA_GA_CHILD = "ga_child";

    }// end of inner class

    public static class UriKey {

        public static final String HOST_CARDS = "cards";
        public static final String HOST_PARTIES = "parties";
        public static final String HOST_COLLECTIONS = "collections";
        public static final String HOST_USERS = "users";
    }

    public static class SchemeKey {

        public static final String WEBVIEW = "webview";
    }

}// end of class

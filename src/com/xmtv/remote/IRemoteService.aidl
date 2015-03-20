package com.xmtv.remote;
/** XMTV Player Service Interface */
interface IRemoteService {
    String pluginVer();
    String pluginAbout();
    String GetChannelGroupsJSON();
    String GetChannelByID(String ChannelID);
    String GetChannelsByGroup(String GroipID);
    String GetAdsAPI();
    String GetChannelInfoByID(String ChannelID);
    String GetChannelEPGByID(String ChannelID);
}
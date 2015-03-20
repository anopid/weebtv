# weebtv
Android WeebTV Add-on for XMTV Player 


requirements:
XMTV Player for Android

to build your own Add-on you must do following:

1. change package name of project
2. in AndroidManifest.xml change intent filter action name with yours one, it must copy this new name to meta-data value.

 
before change:
<intent-filter>
    <action android:name="com.xmtvplayer.plugin.weebtv.service" />
</intent-filter>

<meta-data android:name="xmtvplugin" android:value="com.xmtvplayer.plugin.weebtv.service" />


after change:
<intent-filter>
    <action android:name="YOUR.PACKACGE.YOURPLUGINNAME" />
</intent-filter>

<meta-data android:name="xmtvplugin" android:value="YOUR.PACKACGE.YOURPLUGINNAME" />

3. open RemoteService.java and change follow service methods.
 
    String pluginVer();
    String pluginAbout();
    String GetChannelGroupsJSON(); -> return groups that you have
    String GetChannelByID(String ChannelID); generate playable video url 
    String GetChannelsByGroup(String GroipID); return all channels by specific group
    String GetAdsAPI(); Monetization data 
    String GetChannelInfoByID(String ChannelID); Channel info for specific channel
    String GetChannelEPGByID(String ChannelID); EPG data for specific channel
    
for more information you can contact us on our website  www.xmtvplayer.com
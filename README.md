<p># weebtv<br>
   Android WeebTV Add-on for XMTV Player <br>
 </p>
 <p>requirements:<br>
   XMTV Player for Android</p>
 <p>to build your own Add-on you must do following:</p>
 <p>1. change package name of project<br>
   2. in AndroidManifest.xml change intent filter action name with yours one, it must copy this new name to meta-data value.</p>
 <p> <br>
   before change:<br>
   &lt;intent-filter&gt;<br>
  &lt;action android:name=&quot;com.xmtvplayer.plugin.weebtv.service&quot; /&gt;<br>
  &lt;/intent-filter&gt;</p>
 <p>&lt;meta-data android:name=&quot;xmtvplugin&quot; android:value=&quot;com.xmtvplayer.plugin.weebtv.service&quot; /&gt;<br>
 </p>
 <p>after change:<br>
   &lt;intent-filter&gt;<br>
  &lt;action android:name=&quot;YOUR.PACKACGE.YOURPLUGINNAME&quot; /&gt;<br>
  &lt;/intent-filter&gt;</p>
 <p>&lt;meta-data android:name=&quot;xmtvplugin&quot; android:value=&quot;YOUR.PACKACGE.YOURPLUGINNAME&quot; /&gt;</p>
 <p>3. open RemoteService.java and change follow service methods.<br>
   <br>
   String pluginVer();<br>
   String pluginAbout();<br>
   String GetChannelGroupsJSON(); -&gt; return groups that you have<br>
   String GetChannelByID(String ChannelID); generate playable video url <br>
   String GetChannelsByGroup(String GroipID); return all channels by specific group<br>
   String GetAdsAPI(); Monetization data <br>
   String GetChannelInfoByID(String ChannelID); Channel info for specific channel<br>
   String GetChannelEPGByID(String ChannelID); EPG data for specific channel<br>
  <br>
   for more information you can contact us on our website  www.xmtvplayer.com</p>
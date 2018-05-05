package com.seniorproject.uninet.uninet.NotificationClasses;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.microsoft.windowsazure.notifications.NotificationsHandler;
import com.seniorproject.uninet.uninet.FeedFragment;
import com.seniorproject.uninet.uninet.FriendsListActivity;
import com.seniorproject.uninet.uninet.HomeActivity;
import com.seniorproject.uninet.uninet.MessagesFragment;
import com.seniorproject.uninet.uninet.ProfileInfoActivity;
import com.seniorproject.uninet.uninet.R;

public class MyHandler extends NotificationsHandler {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    Context ctx;

    @Override
    public void onReceive(Context context, Bundle bundle) {
        ctx = context;
        String nhMessage = "";
        if(bundle.getString("message") != null){
            nhMessage = bundle.getString("message");
            sendNotification("message", nhMessage);
        }
        else if(bundle.getString("post") != null){
            nhMessage = bundle.getString("post");
            sendNotification("post", nhMessage);
        }
        else if(bundle.getString("friendship") != null){
            nhMessage = bundle.getString("friendship");
            sendNotification("friendship", nhMessage);
        }

        if (HomeActivity.isVisible) {
            HomeActivity.mainActivity.ToastNotify(nhMessage);
        }
    }

    private void sendNotification(String key, String msg) {
        Intent intent = null;
        String notiTitle = "";
        if (key.equals("message")) {
            intent = new Intent(ctx, HomeActivity.class);
            intent.putExtra("startMessageFragment", "notiMessage");
            notiTitle = "New Message";
        }
        else if (key.equals("post")) {
            intent = new Intent(ctx, HomeActivity.class);
            notiTitle = "New Post";
        }
        else if (key.equals("friendship")) {
            intent = new Intent(ctx, ProfileInfoActivity.class);
            notiTitle = "New Friendship";
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        mNotificationManager = (NotificationManager)
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(ctx, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notiTitle)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setSound(defaultSoundUri)
                        .setContentText(msg)
                        .setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
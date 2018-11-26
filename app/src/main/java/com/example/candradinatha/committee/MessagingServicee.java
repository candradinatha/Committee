//package com.example.candradinatha.committee;
//
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.support.v4.app.NotificationCompat;
//
//import com.example.candradinatha.committee.view.admin.AdminMainActivity;
//import com.google.firebase.messaging.FirebaseMessagingService;
//import com.google.firebase.messaging.RemoteMessage;
//
//public class MessagingServicee extends FirebaseMessagingService {
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        String message = remoteMessage.getNotification().getBody();
//         String title = remoteMessage.getNotification().getTitle();
//
//        Intent intent = new Intent(this, AdminMainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//
//        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "my_chanel")
//                .setContentText(message)
//                .setContentTitle(title)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setSound(sound)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0, notificationBuilder.build());
//
//    }
//}

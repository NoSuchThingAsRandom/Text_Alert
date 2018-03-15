package rstudios.textnotification;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

/**
 * Created by samra on 15/03/2018.
 */
public class SmsListener extends BroadcastReceiver {

    public static String getContactName(Context context, String phoneNumber) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor == null) {
            return null;
        }
        String contactName = null;
        if(cursor.moveToFirst()) {
            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }

        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return contactName;
    }
    @Override
        public void onReceive(final Context context, Intent intent) {

        final String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        System.out.println("TEst");
        String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            System.out.println("Text recieved");
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null) {
                //---retrieve the SMS message received---
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for (int i = 0; i < msgs.length; i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        final String msgBody = getContactName(context,msg_from)+": "+msgs[i].getMessageBody();
                        System.out.println(msgBody);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                new NetworkConnection("TEXT",msgBody,context);
                            }
                        }).start();

                    }

                } catch (Exception e) {
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            System.out.println("INCOMING CALL");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new NetworkConnection("CALL",getContactName(context,number),context);
                }
            }).start();

        }
    }
}


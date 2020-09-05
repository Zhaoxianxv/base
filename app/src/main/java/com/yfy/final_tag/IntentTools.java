package com.yfy.final_tag;

import android.content.Intent;
import android.net.Uri;

public class IntentTools {
    public static Intent getTell(Uri tellUri){
        return new Intent("android.intent.action.CALL",tellUri);
    }

}

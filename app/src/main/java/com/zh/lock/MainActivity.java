package com.zh.lock;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * @author wally
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LockAgent mAgent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAgent = new LockAgent(this);
        mAgent.setOnLockListener(new LockAgent.SimpleOnLockListener() {

            @Override
            public void onLockFail(Throwable error) {
                Log.d(TAG, "Lock Fail： " + error.getMessage());
                finish();
            }

            @Override
            public void onLockPermissionCancel() {
                Log.d(TAG, "Lock Permission Cancel");
                toast(getString(R.string.request_lock_permission_fail));
                finish();
            }
        });
        mAgent.lock();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mAgent != null) {
            mAgent.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void toast(String msg) {
        Toast
                .makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
                .show();
    }
}
package com.timore.abuzeidtimore;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ABZUtils {
    public static void call(Context context, String phone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(callIntent);
    }
    public static void dial(Context context, String phNo) {
        Uri number = Uri.parse("tel:" + phNo);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        context.startActivity(callIntent);
    }public static void openLink(Context context, String url) {
        if(url!=null) {
            try {
                Intent callIntent = new Intent(Intent.ACTION_VIEW);
                callIntent.setData(Uri.parse(url));
                context.startActivity(callIntent);
            }catch (Exception e){

            }
        }else{
         Toast.makeText(context,null,Toast.LENGTH_SHORT).show();
        }
    }

    public static void customActionBar(final Activity activity, int layout) {
        View barview = activity.getLayoutInflater().inflate(layout, null);
    /*txtTitle = (TextView) barview.findViewById(R.id.actionbar_txt_title);
    btnBack = (ImageButton) barview.findViewById(R.id.actionbar_logo);
    btnBack.setImageResource(R.drawable.back_btn);
    btnBack.setOnClickListener(new OnClickListener() {

        @Override
        public void onClick(View v) {
            activity.onBackPressed();
        }
    });

    Button btnPlayStore = (Button) barview.findViewById(R.id.actionbar_Btn_storeplay);
    btnPlayStore.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });
*/
        ActionBar actionBar = activity.getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setCustomView(barview);
            actionBar.setDisplayShowHomeEnabled(false);
        }

    }

    public static void SendWhatsMsg(Activity context, String msg) {
        try {


            Intent sendIntent = new Intent();

            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, msg);
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            context.startActivity(sendIntent);

        } catch (Exception e) {
            Toast.makeText(context, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in air plan mode it will be null
        return (netInfo != null && netInfo.isConnected());

    }


    public static String saveToInternalSorage(Context context, String folderName,
                                              Bitmap bitmapImage, String name) {

        File mypath = new File(getDirStorage(folderName), name + ".jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();

            MediaScannerConnection.scanFile(context, new String[]{

                            mypath.getAbsolutePath()},

                    null, new MediaScannerConnection.OnScanCompletedListener() {

                        public void onScanCompleted(String path, Uri uri) {
                            Log.d("====", "broad cating----------------------" + path);
                        }

                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDirStorage(folderName).getAbsolutePath();
    }


    public static File getDirStorage(String folderNameNMainDir) {

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()
                + "/" + folderNameNMainDir + "/");
        Log.e("getDirStorage", ":" + dir.getAbsolutePath());
        if (!dir.isDirectory())
            dir.mkdirs();
        return dir;
    }


    public static ArrayList<String> getFilesPathFromStorage(String folderNameNMainDir) {

        ArrayList<String> images = new ArrayList<String>();
        File[] files = new File[0];

        files = getDirStorage(folderNameNMainDir).listFiles();

        if (files != null)
            for (File inFile : files) {
                try {

                    images.add(inFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        else
            Log.e("FILES IS NULL", "???????????????????????????");
//  Bitmap b = BitmapFactory.decodeStream(new FileInputStream( inFile));
        return images;
    }

    public static ArrayList<File> getFilesFromStorage(String folderNameNMainDir) {

        ArrayList<File> images = new ArrayList<File>();
        File[] files = new File[0];

        files = getDirStorage(folderNameNMainDir).listFiles();

        if (files != null)
            for (File inFile : files) {
                try {

                    images.add(inFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        else
            Log.e("FILES IS NULL", "???????????????????????????");
//  Bitmap b = BitmapFactory.decodeStream(new FileInputStream( inFile));
        return images;
    }


    /**
     * Showing google speech input dialog
     */
    public static void promptSpeechInput(Activity activity, int REQ_CODE_SPEECH_INPUT) {


        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, activity.getString(R.string.speech_prompt));
        try {
            activity.startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(activity,
                    activity.getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Receiving speech input
     */


    public static void ReceivingSpeech(EditText etSearch, Intent data) {
        ArrayList<String> result = data
                .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        etSearch.setText(result.get(0));
        Log.e("SEARCH", ":" + result.get(0));

    }


    /**
     * Opens dialog picker, so the user can select image from the gallery. The
     * result is returned in the method <code>onActivityResult()</code>
     */
    public static void selectImageFromGallery(Activity context, int REQUEST_ID) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(
                Intent.createChooser(intent, "Select Picture"), REQUEST_ID);
    }

    public static Bitmap selectImageFromGalleryResult(Activity activity,
                                                      int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = activity.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            return decodeFile(picturePath);

        }
        return null;
    }

    public static Bitmap decodeFile(String filePath) {

        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
        return bitmap;
    }

    public static String bitmap2String(String ext, Bitmap bitmap) {

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] byte_arr = stream.toByteArray();

			/*
             * if (ext.equalsIgnoreCase("png"))
			 * bitmap.compress(Bitmap.CompressFormat.PNG, 75, stream); else if
			 * (ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("jpeg"))
			 * bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
			 */
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
            // Base64.encodeToString(byte_arr, Base64.DEFAULT);
            return Base64.encodeToString(byte_arr, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     *
     * @param context                    to work with assets
     * @param defaultFontNameToOverride  for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */

    public static void overrideFont(Context context,
                                    String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(
                    context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class
                    .getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            // Log.e("Can not set custom font " + customFontFileNameInAssets +
            // " instead of " + defaultFontNameToOverride);
        }
    }

    public static void CopyStream(InputStream is, OutputStream os) {
        final int buffer_size = 1024;
        try {
            byte[] bytes = new byte[buffer_size];
            for (; ; ) {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void askForInternet(final Context context) {
        Builder builder = new Builder(context);
        builder.setTitle(context.getString(R.string.net_chkr));
        builder.setMessage(context.getString(R.string.no_net));
        builder.setNegativeButton(context.getString(R.string.ok),
                new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static boolean isConnecting(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static String uploadDate(String url, List<NameValuePair> nameValuePairs) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        StringBuilder builder = new StringBuilder();
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            HttpResponse response = httpclient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String result;
            while ((result = bufferedReader.readLine()) != null) {
                builder.append(result);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void showDateDialog(Context contex, final TextView v) {
        // Process to get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(contex,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        ++monthOfYear;
                        String month = String.valueOf(monthOfYear);
                        if (monthOfYear < 10)
                            month = "0" + monthOfYear;

                        String day = String.valueOf(dayOfMonth);
                        if (dayOfMonth < 10)
                            day = "0" + dayOfMonth;


                        // Display Selected date in textbox
                        String da = year + "-" + month + "-"
                                + day;
                        v.setText(da);

                    }
                }, mYear, mMonth, mDay);
        dpd.show();
    }

    public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            int color = 0xff424242;
            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 50, 50);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(50, 50, 50, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }

    public static void showTimeDialog(Context context, final TextView v, int mHour, int mMinute) {


        // Launch Time Picker Dialog
        TimePickerDialog tpd = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        // Display Selected time in textbox
                        String h = String.valueOf(hourOfDay), m = String.valueOf(minute);
                        if (hourOfDay < 10)
                            h = "0" + hourOfDay;

                        if (minute < 10)
                            m = "0" + minute;

                        v.setText(h + ":" + m);

                    }
                }, mHour, mMinute, true);

        tpd.show();

    }


    public static void readLog(String fname) {

        StringBuilder log = null;
        try {
            Process process = Runtime.getRuntime().exec("logcat -e");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            log = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }

        } catch (IOException e) {
        }

        // convert log to string
        final String logString = log.toString();

        // create text file in SDCard
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/myLogcat");
        dir.mkdirs();
        File file = new File(dir, fname);

        try {
            // to write logcat in text file
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.write(logString);
            osw.flush();
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void shareFacebook(Activity activity, String text) {

        try {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            List<ResolveInfo> resInfo = activity.getPackageManager()
                    .queryIntentActivities(share, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(Intent.ACTION_SEND);
                    targetedShare.setType("text/plain"); // put here your mime
                    // type
                    if (info.activityInfo.packageName.contains("facebook")
                            || info.activityInfo.name.contains("facebook")) {
                        targetedShare.putExtra(Intent.EXTRA_SUBJECT,
                                activity.getString(R.string.find_us_google));
                        targetedShare.putExtra(Intent.EXTRA_TEXT, text);
                        // targetedShare.putExtra(name, value)
                        targetedShare.setPackage(info.activityInfo.packageName);
                        targetedShareIntents.add(targetedShare);
                    }
                }
                Intent chooserIntent = new Intent(
                        targetedShareIntents.remove(0));

                if (targetedShareIntents.size() > 0)
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                            targetedShareIntents.toArray(new Parcelable[]{}));
                activity.startActivity(chooserIntent);

            } else
                Toast.makeText(activity,
                        activity.getString(R.string.no_fb_app),
                        Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.v("VM",
                    "Exception while sending image on" + "face" + " "
                            + e.getMessage());
        }
    }

    public static void share(Activity act, String shareTitle, String shareBody) {
        shareCustom(act, shareTitle, shareBody);

    }

    public static void shareForAll(Context act, String shareTitle,
                                   String shareBody) {
        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        intentShare.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        intentShare.putExtra(Intent.EXTRA_TEXT, shareBody);
        intentShare.setType("text/plain");
        act.startActivity(Intent.createChooser(intentShare,
                act.getString(R.string.share_with)));

    }

    public static void shareCustom(Activity activity, String shTitle,
                                   String text) {


        try {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            List<ResolveInfo> resInfo = activity.getPackageManager()
                    .queryIntentActivities(share, 0);
            if (!resInfo.isEmpty()) {
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(Intent.ACTION_SEND);
                    targetedShare.setType("text/plain"); // put here your mime
                    // type
                    if (info.activityInfo.packageName.contains("facebook")
                            || info.activityInfo.name.contains("facebook")) {
                        targetedShare.putExtra(Intent.EXTRA_SUBJECT, shTitle);
                        targetedShare.putExtra(Intent.EXTRA_TEXT, text);
                        // targetedShare.putExtra(name, value)
                        targetedShare.setPackage(info.activityInfo.packageName);

                        targetedShareIntents.add(targetedShare);
                    } else if (info.activityInfo.packageName
                            .contains("com.twitter")
                            || info.activityInfo.name.contains("twitter")) {
                        targetedShare.putExtra(Intent.EXTRA_SUBJECT, shTitle);
                        targetedShare.putExtra(Intent.EXTRA_TEXT, text);
                        // targetedShare.putExtra(name, value)
                        targetedShare.setPackage(info.activityInfo.packageName);

                        targetedShareIntents.add(targetedShare);
                    } else {
                    }

                }


                // sms

                Uri uri = Uri.parse("smsto:");
                Intent sendIntent = new Intent(Intent.ACTION_SENDTO, uri);
                sendIntent.putExtra("sms_body", text);
                targetedShareIntents.add(sendIntent);

                // MAIL ONLUY


                Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, shTitle);
                emailIntent.putExtra(Intent.EXTRA_TEXT, text);

                targetedShareIntents.add(emailIntent);


                Intent chooserIntent = Intent.createChooser(
                        targetedShareIntents.remove(0),
                        activity.getString(R.string.share_with));
                if (targetedShareIntents.size() > 0)
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                            targetedShareIntents.toArray(new Parcelable[]{}));
                try {
                    activity.startActivity(chooserIntent);
                    activity.overridePendingTransition(android.R.anim.slide_in_left,
                            android.R.anim.slide_out_right);
                } catch (Exception e) {
                }
            } else
                Toast.makeText(activity,
                        activity.getString(R.string.no_fb_app),
                        Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.v("VM",
                    "Exception while sending image on" + "face" + " "
                            + e.getMessage());
        }
    }

    public static String extractYoutubeId(String videoUrl) {
        String video_id = "";
        if (videoUrl != null && videoUrl.trim().length() > 0) {
            String expression = "^.*((youtu.be"
                    + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = videoUrl;
            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
            }
        }
        return video_id;
    }

    public static void scaleImage(ImageView view, int boundBoxInDp) {
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        view.setImageBitmap(scaledBitmap);

    }


    public static void scaleImage(ImageView view, int boundBoxInDp,
                                  boolean rounded) {
        Drawable drawing = view.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawing).getBitmap();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float xScale = ((float) boundBoxInDp) / width;
        float yScale = ((float) boundBoxInDp) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        if (rounded)
            view.setImageBitmap(roundCornerImage(scaledBitmap, 20));
        else
            view.setImageBitmap(scaledBitmap);

    }

    private static Bitmap roundCornerImage(Bitmap src, float round) {
        int width = src.getWidth();
        int height = src.getHeight();

        Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        if (width <= 0 || height <= 0)
            return result;

        Canvas canvas = new Canvas(result);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        final Rect rect = new Rect(0, 0, width, height);
        final RectF rectF = new RectF(rect);

        canvas.drawRoundRect(rectF, round, round, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(src, rect, rect, paint);
        return result;
    }

    public static void hideSoftKey(Activity activity, EditText view) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public static void setImageResolution(Activity act, ImageView iv) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels / 3;
        iv.getLayoutParams().height = height;
    }

    public static void setFullHeightRes(Activity act, ImageView iv) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        iv.getLayoutParams().height = height;
    }

    public static void setFullWidthRes(Activity act, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.getLayoutParams().width = displayMetrics.widthPixels;
    }

    public static void setDialogFullWidthRes(Dialog d,int gravity) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = d.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = gravity;
        window.setAttributes(lp);
    }
public static void setDialogFullWidthRes(Dialog d,int gravity,int height) {
    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    Window window = d.getWindow();
    lp.copyFrom(window.getAttributes());
    //This makes the dialog take up the full width
    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
    lp.height =height;
    lp.gravity = gravity;
    window.setAttributes(lp);
    }

    public static void mailto(Context context, String t, String body) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO,
                    Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, t);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            context.startActivity(emailIntent);
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.no_email_app),
                    Toast.LENGTH_LONG).show();
        }
    }

    public static void sendSms(Context context, String phone, String body) {
        Uri uri = Uri.parse("smsto:" + phone);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", body);
        context.startActivity(it);
    }


    public void shareImage(Activity activity, String title, String path) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_TITLE, title);
        // Make sure you put example png image named myImage.png in your
        // directory
        String imagePath = Environment.getExternalStorageDirectory()
                + "/myImage.png";// path
        File imageFileToShare = new File(imagePath);
        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, uri);

        activity.startActivity(Intent.createChooser(share, "Share Image!"));
    }

    private void sendSmsMngr(Context context, String phoneNumber,
                             final String smsBody) {

        SmsManager smsManager = SmsManager.getDefault();
        String ACTION = "ACTION_SEND_SMS";
        Intent intent = new Intent(ACTION);
        PendingIntent sentPI = PendingIntent
                .getBroadcast(context, 0, intent, 0);
        context.registerReceiver(new BroadcastReceiver() {
            String res = "";

            @Override
            public void onReceive(Context arg0, Intent i) {
                Log.d("onReceive", i.getExtras().getInt("rq") + ":");

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        res = "SMS sent";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        res = "the message was not sent, please try again later";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        res = "No service";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        res = "the message was not sent, please try again later";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        res = "the message was not sent, please try again later";
                        break;
                }

            }
        }, new IntentFilter(ACTION));
        smsManager.sendTextMessage(phoneNumber, null, smsBody, sentPI, null);
    }


    public static void image(int imageview, int progress, String url) {

    }

    public static void image(ImageView imageView, ProgressBar progressBar, String url) {

    }
}
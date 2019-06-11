package longnd.thesis.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import longnd.thesis.R;
import longnd.thesis.data.entity.User;

public class Utils {
    private static final String TAG = "Utils";
    private static final int MAX_SCORE = 4;

    /**
     * used to get typeface
     *
     * @param context
     * @param nameType
     * @return
     */
    public static Typeface getTypeFace(Context context, String nameType) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + nameType);
        return typeface;
    }

    /**
     * used to save data customer in SharedPrefs
     *
     * @param email
     * @param pass
     */
    public static void onSaveCustomerInSharedPrefs(String email, String pass) {
        byte[] encodePass = Base64.encode(pass.getBytes(), Base64.DEFAULT);
        SharedPrefs.getInstance().putString(Fields.KEY_EMAIL, email);
        SharedPrefs.getInstance().putString(Fields.KEY_PASS, new String(encodePass));
    }

    /**
     * used to get email and pass if user is logged in
     *
     * @return user
     */
    public static User getUser() {
        User user = new User();
        String email = SharedPrefs.getInstance().getString(Fields.KEY_EMAIL, Fields.DEFAULT_VALUE);
        String pass = SharedPrefs.getInstance().getString(Fields.KEY_PASS, Fields.DEFAULT_VALUE);
        if (!email.equals(Fields.DEFAULT_VALUE) && !pass.equals(Fields.DEFAULT_VALUE)) {
            user.setEmail(email);

            byte[] data = Base64.decode(pass.getBytes(), Base64.DEFAULT);
            user.setPass(new String(data));
        }
        return user;
    }

    /**
     * used to get title type question
     *
     * @param type
     * @return
     */
    public static String getTitleQuestion(int type) {
        String title;
        switch (type) {
            case Define.Question.TYPE_NEO:
                title = "Nhân cách NEO";
                break;
            case Define.Question.TYPE_RIASEC:
                title = "Hứng thú nghề nghiệp RIASEC";
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                title = "Sàng lọc khó khăn tâm lý";
                break;
            default:
                title = "Nhân cách NEO";
                break;
        }
        return title;
    }

    /**
     * used to get detail type question
     *
     * @param context
     * @param type
     * @return
     */
    public static String getDetailQuestion(Context context, int type) {
        String detail;
        switch (type) {
            case Define.Question.TYPE_NEO:
                detail = context.getResources().getString(R.string.detail_neo);
                break;
            case Define.Question.TYPE_RIASEC:
                detail = context.getResources().getString(R.string.detail_riasec);
                break;
            case Define.Question.TYPE_PSY_POCHOLIGICAL:
                detail = context.getResources().getString(R.string.detail_psy);
                break;
            default:
                detail = context.getResources().getString(R.string.detail_neo);
                break;
        }
        return detail;
    }

    /**
     * used to hide keyboard
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * @return true folder packing exists and file nomedia exists
     */
    public static boolean existsPackingFolder() {
        try {
            File packingFolder = new File(Fields.ROOT_FOLDER);
            File noMedia = new File(Fields.ROOT_FOLDER + "/.nomedia");
            if (!packingFolder.exists()) {
                return packingFolder.mkdir() && noMedia.createNewFile();
            } else {
                if (!noMedia.exists()) {
                    return noMedia.createNewFile();
                } else {
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "existsPackingFolder: ", e);
        }
        return false;
    }

    /**
     * method used to save images
     *
     * @param srcFile : file input
     * @param dstFile : file output
     * @return
     */
    public static boolean onSaveImageFileToMemory(File srcFile, File dstFile) {
        double fileSizeInBytes = srcFile.length();
        double fileSizeInKB = fileSizeInBytes / 1024;
        if (fileSizeInKB >= 300) {
            return reduceSizeImageFile(srcFile, dstFile);
        } else {
            return copyImage(srcFile, dstFile);
        }
    }

    /**
     * used to resize bitmap images
     *
     * @param srcFile
     * @param dstFile
     * @return
     */
    public static boolean reduceSizeImageFile(File srcFile, File dstFile) {
        float rotate90 = 90.0f;
        float rotate180 = 180.0f;
        float rotate270 = 270.0f;
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;

            FileInputStream inputStream = new FileInputStream(srcFile);
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            // rotate
            Matrix matrix = new Matrix();
            ExifInterface exifInterface = new ExifInterface(srcFile.getAbsolutePath());
            int orientation = exifInterface.getAttributeInt(exifInterface.TAG_ORIENTATION,
                    exifInterface.ORIENTATION_UNDEFINED);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(rotate90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(rotate180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(rotate270);
                    break;
                default:
                    break;
            }
            inputStream = new FileInputStream(srcFile);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            selectedBitmap = Bitmap.createBitmap(selectedBitmap, 0, 0, selectedBitmap.getWidth(),
                    selectedBitmap.getHeight(), matrix, true);
            inputStream.close();

            File newFile = dstFile;
            newFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(newFile);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "reduceSizeImageFile: ", e);
        }
        return false;
    }

    /**
     * use to copy files
     *
     * @param src : source file
     * @param dst : destination file
     */
    public static boolean copyImage(File src, File dst) {
        new CopyImage().execute(src, dst);
        return true;
    }

    private static class CopyImage extends AsyncTask<File, Void, Void> {
        @Override
        protected Void doInBackground(File... files) {
            File src = files[0];
            File dst = files[1];
            try {
                InputStream in = new FileInputStream(src);
                OutputStream out = new FileOutputStream(dst);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } catch (IOException e) {
                Log.e(TAG, "doInBackground: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voids) {
            super.onPostExecute(voids);
        }
    }

    public static String getPathFromUri(Context context, Uri uri) {
        String path = null;
        if (Build.VERSION.SDK_INT < 11) {
            path = RealPathUtils.getRealPathFromURI_BelowAPI11(context, uri);
        } else if (Build.VERSION.SDK_INT < 19) {
            // SDK >= 11 && SDK < 19
            path = RealPathUtils.getRealPathFromURI_API11to18(context, uri);
        } else {
            // SDK > 19 (Android 4.4)
            path = RealPathUtils.getRealPathFromURI_API19(context, uri);
        }
        return path != null ? path : "";
    }

    /**
     * used to get current time format dd/MM/yyyy HH:mm
     *
     * @return
     */
    public static String getCurrentTimeStamp() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();
        return formatDate.format(now);
    }

    /**
     * used to convert long time to string
     *
     * @param longTime
     * @return
     */
    public static String convertLongTimeIntoString(long longTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss EEE, MMM d, yyyy");
        Date resultdate = new Date(longTime);
        return sdf.format(resultdate);
    }

    /**
     * used to convert string with base64
     *
     * @param value
     * @return
     */
    public static String encodeSrting(String value) {
        byte[] data = Base64.encode(value.getBytes(), Base64.DEFAULT);
        return new String(data);
    }

    /**
     * @return true folder packing exists and file nomedia exists
     */
    public static boolean existsPsyFolder() {
        try {
            File psyFolder = new File(Fields.ROOT_FOLDER);
            File noMedia = new File(Fields.ROOT_FOLDER + "/.nomedia");
            if (!psyFolder.exists()) {
                return psyFolder.mkdir() && noMedia.createNewFile();
            } else {
                if (!noMedia.exists()) {
                    return noMedia.createNewFile();
                } else {
                    return true;
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "existsPsyFolder: ", e);
        }
        return true;
    }

    /**
     * used to check file image exists
     *
     * @param path
     * @return
     */
    public static boolean existsPathImage(String path) {
        return new File(path).exists();
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * used to reverse score
     *
     * @param score
     * @return
     */
    public static int reverseScore(int score) {
        return MAX_SCORE - score;
    }

    /**
     * Thoả mãn điều kiện:
     * - Có ký tự số : 0 -> 9
     * - Có ký tự thường : a -> z
     * - Có ký tự hoa : A -> Z
     * - Có ký tự đặc biệt: !.#$@_+,?-
     * - Có độ dài từ 6 đến 50 ký
     */
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!.#$@_+,?-]).{6,50})";

    /**
     * Use check the validity of the password string
     *
     * @param pass password for validation
     * @return true valid password, false invalid password
     */
    public static Boolean isValidPassword(String pass) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
}
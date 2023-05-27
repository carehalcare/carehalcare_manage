package carehalcare.carehalcare_manage.Feature_carereport.Walk;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import carehalcare.carehalcare.Feature_write.ErrorModel;
import carehalcare.carehalcare.Feature_write.Meal.Meal_API;
import carehalcare.carehalcare.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Walk_form  extends AppCompatActivity {
    ImageView imgtest;
    Button btn_save, btn_nono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_form);

        imgtest = (ImageView)findViewById(R.id.iv_walk_form);
        btn_save = (Button) findViewById(R.id.btn_save_walk_form);
        btn_nono = (Button) findViewById(R.id.btn_nono_walk_form);

        Uri output = getIntent().getParcelableExtra("uri");
        String[] bitmaps = UriBitmap_walk(output);
        String contentpath = bitmaps[0];
        String absopath = bitmaps[1];

        Log.e("content경로는 : ",""+contentpath);
        Log.e("absolute경로는 : ",""+absopath);

        imgtest.setImageURI(Uri.parse(absopath));
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uris = Uri.parse(contentpath);

                Intent intent = new Intent();
                intent.putExtra("uris",uris);
                setResult(2888, intent);

                Map<String, RequestBody> map = new HashMap<>();

                RequestBody userId = RequestBody.create(MediaType.parse("text/plain"),"userId1");
                RequestBody puserId = RequestBody.create(MediaType.parse("text/plain"),"puserId1");

                map.put("userId", userId);
                map.put("puserId", puserId);

                //filepath는 String 변수로 갤러리에서 이미지를 가져올 때 photoUri.getPath()를 통해 받아온다.
                File file = new File(absopath);
                if (!file.exists()) {       // 원하는 경로에 폴더가 있는지 확인
                    file.mkdirs();    // 하위폴더를 포함한 폴더를 전부 생성
                }
                InputStream inputStream = null;
                try {
                    inputStream = getApplicationContext().getContentResolver().openInputStream(Uri.parse(absopath));
                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                ArrayList<MultipartBody.Part> names = new ArrayList<>();

                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("images", file.getName() ,requestBody);
                names.add(uploadFile);

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Meal_API.URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                Walk_API walk_api = retrofit.create(Walk_API.class);
                walk_api.postDataWalk(map,names).enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(@NonNull Call<Long> call, @NonNull Response<Long> response) {
                        if(response.isSuccessful()){
                            if (response.body()!=null){
                                Toast.makeText(getApplicationContext(),"저장이 완료되었습니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Converter<ResponseBody, ErrorModel> converter = retrofit.responseBodyConverter(ErrorModel.class, new java.lang.annotation.Annotation[0]);
                            ErrorModel errorModel = null;
                            try {
                                errorModel = converter.convert(response.errorBody());
                                Log.e("error code???",""+errorModel.toString());
                                Log.e("error code???",""+response.body());
                                Log.e("error code???",""+response.message());
                                Log.e("YMC", "stringToJson msg: 실패" + response.code());
                                Toast.makeText(Walk_form.this, errorModel.toString(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Log.e("통신에러","+"+t.toString());
                        Toast.makeText(getApplicationContext(), "통신에러", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        });
        btn_nono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                setResult(3000, intent);
                finish();
            }
        });

    }

    public String[] UriBitmap_walk(Uri imageuris) {
        String[] twopath = new String[2];
        String contentpath = "";
        String absolutePath = "";
        Bitmap bm = null;
        String dateText = "";
        String timeText = "";
        Canvas cs;
        Paint pt = new Paint();
        float heightda = pt.measureText("yY");

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(200);

        Paint strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setTextSize(200);
        strokePaint.setColor(Color.BLACK);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(14);

        Date today_date = Calendar.getInstance().getTime();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy년 M월 dd일", Locale.KOREAN);
        SimpleDateFormat tformat = new SimpleDateFormat("HH시 MM분 SS초", Locale.KOREAN);

        dateText = dformat.format(today_date);
        timeText = tformat.format(today_date);
        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                bm = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(), imageuris)).copy(Bitmap.Config.ARGB_8888, true);

            } else {
                bm = MediaStore.Images.Media.getBitmap(getContentResolver(), imageuris).copy(Bitmap.Config.ARGB_8888, true);
            }
            cs = new Canvas(bm);
            cs.drawText(dateText, 20f, heightda+300f, strokePaint);
            cs.drawText(dateText, 20f, heightda+300f, textPaint);
            cs.drawText(timeText, 20f, heightda+500f, strokePaint);
            cs.drawText(timeText, 20f, heightda+500f, textPaint);
            imgtest.setImageBitmap(bm);

        } catch (IOException e) {
            e.printStackTrace();
        }
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "timeyen"+".JPG");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.IS_PENDING, 1);
        }
        ContentResolver contentResolver = getContentResolver();
        Uri item = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);
        try {
            //TODO [쓰기 모드 지정]
            ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(item, "w", null);

            if (pdf == null) {
            } else {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                //Bitmap bitmap= BitmapFactory.decodeFile(filePath, options);
                Bitmap bitmap = ((BitmapDrawable) imgtest.getDrawable()).getBitmap();

                //TODO [리사이징 된 파일을 비트맵에 담는다]
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);

                byte[] imageInByte = baos.toByteArray();

                //TODO [이미지를 저장하는 부분]
                FileOutputStream outputStream = new FileOutputStream(pdf.getFileDescriptor());
                outputStream.write(imageInByte);
                outputStream.close();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear();
                    values.put(MediaStore.Images.Media.IS_PENDING, 0);
                    contentResolver.update(item, values, null, null);
                }

                //TODO [경로 저장 실시]
                contentpath = String.valueOf(item);
                //[콘텐츠 : 이미지 경로 저장]
                //S_Preference.setString(getApplication(), "saveCameraScopeContent", String.valueOf(item));

                //[절대 : 이미지 경로 저장]
                Cursor c = getContentResolver().query(Uri.parse(String.valueOf(item)), null,null,null,null);
                c.moveToNext();
                absolutePath = c.getString(c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                readFile(imgtest, absolutePath);

                twopath[0] = contentpath;
                twopath[1] = absolutePath;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twopath;



    }
    private void readFile(ImageView view, String path) {
        Log.d("---","---");
        Log.d("//===========//","================================================");
        Log.d("","\n"+"[A_Camera > readFile() 메소드 : MediaStore 파일 불러오기 실시]");
        Log.d("","\n"+"[콘텐츠 파일 경로 : "+String.valueOf(path)+"]");
        Log.d("//===========//","================================================");
        Log.d("---","---");
        Uri externalUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.MIME_TYPE
        };

        Cursor cursor = getContentResolver().query(externalUri, projection, null, null, null);

        if (cursor == null || !cursor.moveToFirst()) {
            Log.d("---","---");
            Log.e("//===========//","================================================");
            Log.d("","\n"+"[A_Camera > readFile() 메소드 : MediaStore 파일 불러오기 실패]");
            Log.d("","\n"+"[원인 : "+String.valueOf("Cursor 객체 null")+"]");
            Log.e("//===========//","================================================");
            Log.d("---","---");
            return;
        }

        //TODO [특정 파일 불러오기 실시]
        String contentUrl = path;
        try {
            InputStream is = getContentResolver().openInputStream(Uri.parse(contentUrl));
            if(is != null){
                // [선택한 이미지에서 비트맵 생성]
                Bitmap img = BitmapFactory.decodeStream(is);
                is.close();

                // [이미지 뷰에 이미지 표시]
                view.setImageBitmap(img);
                Log.d("---","---");
                Log.w("//===========//","================================================");
                Log.d("","\n"+"[A_Camera > readFile() 메소드 : MediaStore 파일 불러오기 성공]");
                Log.d("","\n"+"[콘텐츠 파일 경로 : "+String.valueOf(contentUrl)+"]");
                Log.w("//===========//","================================================");
                Log.d("---","---");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

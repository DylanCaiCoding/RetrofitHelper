package com.dylanc.retrofit.helper.sample.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.dylanc.retrofit.helper.RetrofitHelper;
import com.dylanc.retrofit.helper.rxjava.Transformers;
import com.dylanc.retrofit.helper.sample.R;
import com.dylanc.retrofit.helper.sample.api.TestApi;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public class JavaActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void requestBaiduNews(View view) {
    RetrofitHelper.create(TestApi.class)
        .getBaiduNews()
        .compose(Transformers.io2mainThread())
        .subscribe(this::onNext, this::onError);
  }

  public void requestGankData(View view) {
    RetrofitHelper.create(TestApi.class)
        .getGankData()
        .compose(Transformers.io2mainThread())
        .subscribe(this::onNext, this::onError);
  }

  public void requestLogin(View view) {
    RetrofitHelper.create(TestApi.class)
        .login()
        .compose(Transformers.io2mainThread())
        .subscribe(result -> Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show(), this::onError);
  }

  public void onNext(String json) {
    Toast.makeText(JavaActivity.this, json, Toast.LENGTH_SHORT).show();
  }

  public void onError(Throwable e) {
    Toast.makeText(JavaActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
  }

  public void download(View view) {
//    final String pathname = Environment.getExternalStorageDirectory().getPath() + "/test.png";
//    new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//        .flatMap((Function<Boolean, ObservableSource<File>>) granted -> {
//          if (granted) {
//              return DownloadTask.with(DOWNLOAD_URL).toFile(pathname);
//          }
//          throw new NullPointerException("");
//        })
//        .subscribe(this::onNext);
  }
}
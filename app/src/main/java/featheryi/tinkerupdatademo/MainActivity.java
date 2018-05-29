package featheryi.tinkerupdatademo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
======================
    Tinker 已知問題
======================
    。Tinker 不支持修改 AndroidManifest.xml，
        Tinker 不支持添加四大組件；
    。由於 Google Play 的開發者條款限制，
        不建議在GP渠道動態更新代碼；
    。在 Android N(7.0) 上，補丁對應用啟動時間有輕微的影響；
    。不支持部分三星 android-21 機型，加載補丁時會主動拋出
        TinkerRuntimeException:checkDexInstall failed；
    。由於各個廠商的加固實現並不一致，在1.7.6以及之後的版本，
        tinker 不再支持加固的動態更新；
    。對於資源替換，不支持修改 remoteView。
        例如 transition動畫，notification icon 以及 桌面圖標。
--------------------------------------------------------------------------------------------------------------------------------
======================
    基礎配置
======================
    1.  gradle.properties 配置 TINKER、TINKERPATCH 版本
        TINKER_VERSION=1.9.5
        TINKERPATCH_VERSION=1.2.2

    2.  bulid.gradle(project)
        classpath("com.tinkerpatch.sdk:tinkerpatch-gradle-plugin:${TINKERPATCH_VERSION}") {changing = true }

    3.  複製 tinkerpatch.gradle 至項目
        tinker-sample-android

    4.  bulid.gradle(app)
        。签名配置（一定要簽名不然不能安裝）
            ．先產生簽名
                Build/Generate Signed APK/產生 Untitled
            ．設定配置
                File/Project structure/Module>app>signing/新增項目>填入簽名訊息(StoreFile 為 Untitled)
            ．建立連結
                File/Project structure/Module>app>buildTypes>SigningConfig/選擇剛設定項目
            ．ok > 自動產生 build.gradle 的配置文件
        。資源掛載
            dependencies{
                implementation "com.android.support:multidex:1.0.2"
                //若使用annotation需要单独引用,对于tinker的其他库都无需再引用
                annotationProcessor("com.tinkerpatch.tinker:tinker-android-anno:${TINKER_VERSION}") { changing = true }
                compileOnly("com.tinkerpatch.tinker:tinker-android-anno:${TINKER_VERSION}") { changing = true }
                implementation("com.tinkerpatch.sdk:tinkerpatch-android-sdk:${TINKERPATCH_VERSION}") { changing = true }
            }
            apply from: 'tinkerpatch.gradle'

    5.  線上申請添加 app 資訊
        http://tinkerpatch.com/
        獲取 appKey、設置 appVersion 版本

    6.  更換 tinkerpatch.gradle 內容
        appKey、appVersion

    7.  設置新文件
        FetchPatchHandler用於檢測是否更新（剛打開時會檢測一次）
        SampleApplication 入口文件 初始化SDK(reflectApplication = true 的情況)

    8.  配置權限
        <uses-permission android:name="android.permission.INTERNET" /> <!-- 網路 -->
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> <!-- 寫入 -->
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" /> <!-- 讀取 -->
--------------------------------------------------------------------------------------------------------------------------------
======================
    創建基礎包
======================
    ＊一定要配置簽名
    1.右邊 Gradle > 專案名/Tasks/build/
        。assembleDebug   測試版
        。assembleRelease 正式版
     最後出現 BUILD SUCCESSFUL 即成功

    2.app/build/bakApk/app-版本-時間（命名）/release/含apk及R.txt
        (產生包內的 apk 不可更名 一定要使用預設 app-release.apk
        否則補丁包無法撈取，需額外複製更名)

    # 將“全部檔案”(app-版本-時間(命名))備份，打補丁包的時候需要用到 #
    # 如果發佈新的全量包 tinkerpatch.gradle/appVersion 一定要更新 #
--------------------------------------------------------------------------------------------------------------------------------
======================
    創建補丁包
======================
    1.修改內容
    2.打開 tinkerpatch.gradle 修改 baseInfo 成對應的文件名
        baseInfo ＝ 基礎包 build/bakApk 產生名稱 (app-版本-時間(命名))
        (產生包內的 apk 不可更名 一定要使用預設 app-release.apk
        否則補丁包無法撈取，需額外複製更名)
    3.右邊 Gradle > 專案名/Tasks/tinker/
        。tinkerPatchDebug   測試版
        。tinkerPatchRelease 正式版
        最後出現 BUILD SUCCESSFUL 即成功
    4.補丁包將位於 build/outputs/apk/tinkerPatch/release 中 patch_signed_7zip.apk
    5.上傳補丁包
        http://tinkerpatch.com/
        選擇對應 app 對應版本 上傳補丁文件
    6.測試
        需開啟 app 等待下載補丁
        關閉 app 後二次啟動才生效

    ＊補丁文件必須額外存檔備份
        否則會被資料夾覆蓋遺失
*/

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

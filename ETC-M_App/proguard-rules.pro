# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Android_SDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-optimizationpasses 7
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontoptimize
-verbose
-ignorewarnings
-dontskipnonpubliclibraryclassmembers
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#apk 包内所有 class 的内部结构
-dump class_files.txt
#未混淆的类和成员
-printseeds seeds.txt
#列出从 apk 中删除的代码
-printusage unused.txt
#混淆前后的映射
-printmapping mapping.txt

-keep class com.common.** { *; }
-keep class com.views.** { *; }
-keep class com.commonsware.** { *; }
-keep class java.lang.** { *; }
-keep class android.** { *; }
-keep class java.** { *; }


-keep class io.realm.annotations.RealmModule
-keep @io.realm.annotations.RealmModule class *
-keep class io.realm.internal.Keep
-keep @io.realm.internal.Keep class * { *; }
-dontwarn javax.**
-dontwarn io.realm.**
-keep public class * implements com.bumptech.glide.module.GlideModule


-keep class com.alibaba.fastjson.** { *; }
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * implements com.bumptech.glide.module.GlideModule

-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}

-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-dontwarn rx.*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.** { *; }
#
#-keep class cn.sharesdk.**{*;}
#-keep class com.sina.**{*;}
#-keep class **.R$* {*;}
#-keep class **.R{*;}
#-keep class com.mob.**{*;}
#-dontwarn com.mob.**
#-dontwarn cn.sharesdk.**
#-dontwarn **.R$*

#-keep class com.baidu.** {*;}
#-keep class vi.com.** {*;}
#-dontwarn com.baidu.**

#
#-keep class com.akexorcist.** {*;}
#-keep class com.loopj.** {*;}
#-keep class com.chad.** {*;}
#-keep class me.relex.** {*;}
#-keep class com.mikhaellopez.** {*;}
#-keep class com.karumi.** {*;}
#-keep class de.greenrobot.** {*;}
#-keep class com.zhy.** {*;}
#-keep class com.flyco.** {*;}
#-keep class com.bumptech.** {*;}
#-keep class org.hamcrest.** {*;}
#-keep class cz.msebera.** {*;}
#-keep class com.squareup.** {*;}
#-keep class javax.** {*;}
#-keep class com.socks.** {*;}
#-keep class com.nineoldandroids.** {*;}
#-keep class com.android.** {*;}
#-keep class okio.** {*;}
#-keep class android.** {*;}
#-keep class org.apache.** {*;}
#-keep class uk.co.** {*;}
#-keep class com.bigkoo.** {*;}
#-keep class com.github.** {*;}
#-keep class com.akexorcist.** {*;}
#-keep class rx.** {*;}
#-keep class com.sevenheaven.** {*;}
#-keep class com.transitionseverywhere.** {*;}
#-keep class in.srain.** {*;}
#-keep class com.wx.** {*;}

-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
   long producerIndex;
   long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

-dontwarn com.squareup.okhttp3.**
-keep class com.squareup.okhttp3.** { *;}
-dontwarn okio.**

##个推
#-dontwarn com.igexin.**
#-keep class com.igexin.** { *; }
#-keep class org.json.** { *; }
#
#
#
#-keep public class android.webkit.**
#
#
#
##友盟
#-keepclassmembers class * {
#   public <init>(org.json.JSONObject);
#}
#
#-keep class com.umeng.**
#
#-keep public class com.idea.fifaalarmclock.app.R$*{
#    public static final int *;
#}
#
#-keep public class com.umeng.fb.ui.ThreadView {
#}
##友盟
#-dontwarn com.umeng.**
#
#-dontwarn org.apache.commons.**
#
#-keep public class * extends com.umeng.**
#
#-keep class com.umeng.** {*; }
#
##支付宝
##-libraryjars libs/alipaySdk-20161009.jar
#
#-keep class com.alipay.android.app.IAlixPay{*;}
#-keep class com.alipay.android.app.IAlixPay$Stub{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
#-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
#-keep class com.alipay.sdk.app.PayTask{ public *;}
#-keep class com.alipay.sdk.app.AuthTask{ public *;}
#
##微信
#-keep class com.tencent.mm.sdk.** {
#   *;
#}
#
#


-keepattributes Signature

-keepattributes *Annotation*

#-keep public class * extends android.support.v4.**
-keep public class * extends android.support.**
-keep public class * extends android.app.**
-keep public class * extends android.**
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}


-keepclassmembers class **.R$* {
    public static <fields>;
}
-keep class **.R$* {
 *;
}
-keepnames class * implements java.io.Serializable


-keepclassmembers class * implements java.io.Serializable {
   static final long serialVersionUID;
   private static final java.io.ObjectStreamField[] serialPersistentFields;
   !static !transient <fields>;
   private void writeObject(java.io.ObjectOutputStream);
   private void readObject(java.io.ObjectInputStream);
   java.lang.Object writeReplace();
   java.lang.Object readResolve();
}

-keepattributes SourceFile,LineNumberTable
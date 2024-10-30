# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

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

-dontnote okhttp3.internal.Platform

-if class androidx.credentials.CredentialManager
-keep class androidx.credentials.playservices.** {
  *;
}

-dontwarn org.bouncycastle.jsse.BCSSLParameters
-dontwarn org.bouncycastle.jsse.BCSSLSocket
-dontwarn org.bouncycastle.jsse.provider.BouncyCastleJsseProvider
-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE


# Keep native methods
    -keepclassmembers class * {
        native <methods>;
    }

    -dontwarn okio.**
    -dontwarn com.squareup.okhttp.**
    -dontwarn javax.annotation.**
    -dontwarn com.android.volley.toolbox.**

    -ignorewarnings
    -keep class * {
        public private *;
    }
# Keep native methods

# Google
    -dontwarn com.google.android.material.**
    -keep class com.google.android.material.** { *; }

    -dontwarn androidx.**
    -keep class androidx.** { *; }
    -keep interface androidx.** { *; }
# Google

# Retrofit 2.X
    ## https://square.github.io/retrofit/ ##
    -dontwarn retrofit2.**
    -keep class retrofit2.** { *; }
    -keep class okhttp3.** { *; }
    -keepattributes Signature
    -keepattributes Exceptions

    -keepclasseswithmembers class * {
        @retrofit2.http.* <methods>;
    }

# Firebase Crashlytics
    -keepattributes SourceFile,LineNumberTable

# Glide
    -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep class * extends com.bumptech.glide.module.AppGlideModule {
    <init>(...);
    }
    -keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
        **[] $VALUES;
        public *;
    }
    -keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
        *** rewind();
    }

# Uncomment for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# Picasso
### OKHTTP

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote okhttp3.internal.Platform


### OKIO

# java.nio.file.* usage which cannot be used at runtime. Animal sniffer annotation.
-dontwarn okio.Okio
# JDK 7-only method which is @hide on Android. Animal sniffer annotation.
-dontwarn okio.DeflaterSink
-dontwarn com.squareup.okhttp.**
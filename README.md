# okhttp-logging
- okhttp 网络请求日志工具。根据`com.squareup.okhttp3:logging-interceptor`修改。
- 对于部分网络接口请求头没有返回字符类型的情况通过分析内容文本格式得到正确字符集，避免日志乱码，例如中文小说网站。

### 使用
- 项目根目录build.gradle添加存储库
```groovy
allprojects {
    repositories {
        ...
        maven {
            url "https://raw.githubusercontent.com/SJJ-dot/repo/master/repository"
        }
    }
}
```
- 使用的module添加依赖
```groovy
dependencies {
    ...
    implementation 'com.sjianjun:okhttp-logging:0.0.1'
    //。。。
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.google.code.gson:gson:2.8.6'
}
```
- 代码中
```
    val test = Retrofit.Builder()
        .baseUrl("http://www.xbiquge.la")
        .client(
            OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor {
                    Log.e(it)
                }.setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .addConverterFactory(ScalarsConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(CoroutineScheduler.IO))
        .build()
```

# 字符集检测
网上copy的代码。具体来源已经忘记了。主要使用的第三方的jar包
- 添加存储库**同上**
- 添加依赖
```groovy
dependencies {
    ...
    implementation 'com.sjianjun:charset-detector:0.0.1'
}
```
- 代码中
```
String charsetStr = CharsetDetector.detectCharset(buffer.inputStream());
```

# retrofit类型转换
- 添加存储库**同上**
- 添加依赖
```groovy
dependencies {
    ...
    implementation 'com.sjianjun:retrofit-converter:0.0.1'
    //...
    implementation 'com.squareup.retrofit2:retrofit:2.6.1'
    implementation 'com.google.code.gson:gson:2.8.6'
}
```
- 代码中
```
    val test = Retrofit.Builder()
        .addConverterFactory(GsonCharsetCompatibleConverter.create())
```

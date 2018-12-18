# StateLayout

## 概述
[![](https://jitpack.io/v/Brook007/StateLayout.svg)](https://github.com/Brook007/StateLayout)
[![](https://img.shields.io/badge/Platform-Android-brightgreen.svg)](https://github.com/Brook007/StateLayout)
[![](https://img.shields.io/badge/API_Live-14+-brightgreen.svg)](https://github.com/Brook007/StateLayout)
[![](https://img.shields.io/badge/License-Apache_2-brightgreen.svg)](https://github.com/Brook007/StateLayout/blob/master/LICENSE)
[![](https://img.shields.io/badge/%E4%BD%9C%E8%80%85-Brook007-orange.svg)](https://github.com/Brook007)


## 引入依赖
### Gradle方式--适合Android Studio用户
在根项目的build.gradle中添加下面的代码
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

然后在需要使用的模块的build.gradle中添加以下代码
```groovy
dependencies {
    implementation 'com.github.Brook007:StateLayout:1.0.0'
}
```
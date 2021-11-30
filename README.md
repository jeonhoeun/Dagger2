# Dagger2
안드로이드 Dagger2 상황별 정리

# 프로젝트에 Dagger 설정하기

## 1. app/build.gradle
```

plugins{
    id 'kotlin-kapt'
}

...

dependencies {
    implementation 'com.google.dagger:dagger-android:버전'
    implementation 'com.google.dagger:dagger-android-support:버전'
    kapt 'com.google.dagger:dagger-android-processor:버전'
    kapt 'com.google.dagger:dagger-compiler:버전'
    
    //아래는 test코드에서도 사용하는경우  
    testImplementation 'com.google.dagger:dagger-android:버전'
    testImplementation 'com.google.dagger:dagger-android-support:버전'
    kaptTest 'com.google.dagger:dagger-android-processor:버전'
    kaptTest 'com.google.dagger:dagger-compiler:버전'
    
}


//빌드 오류시 오류 라인 500으로 설정

android{
    gradle.projectsEvaluated{
        tasks.withType(JavaCompile){
            options.compilerArgs << "-Xmaxerrs" << "500"
        }
    }
}

```


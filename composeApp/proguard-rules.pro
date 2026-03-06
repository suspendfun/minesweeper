# Kotlin
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }

# Kotlin Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# Keep @Composable functions and their metadata
-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
}

# Lifecycle ViewModel
-keep class * extends androidx.lifecycle.ViewModel { *; }

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

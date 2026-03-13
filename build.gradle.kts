plugins {
    kotlin("multiplatform") version "1.9.24" apply false
    kotlin("jvm") version "1.9.24" apply false
    // Ezt a kettőt adjuk hozzá az Android miatt:
    id("com.android.application") version "8.2.0" apply false
    id("com.android.library") version "8.2.0" apply false
}
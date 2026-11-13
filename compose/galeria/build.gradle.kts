import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    kotlin("plugin.compose")
    id("org.jetbrains.compose")
}

kotlin { compilerOptions { jvmTarget.set(JvmTarget.JVM_17) } }
java { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }

dependencies {
    implementation(project(":nokylabs-ui"))
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
}

// ./gradlew :galeria:run  → abre a Galeria numa janela
compose.desktop {
    application { mainClass = "com.nokylabs.ui.galeria.MainKt" }
}

// ./gradlew :galeria:capturar  → gera as imagens da Galeria em compose/capturas/ (sem abrir janela)
tasks.register<JavaExec>("capturar") {
    group = "nokylabs"
    description = "Gera PNGs da Galeria (temas escuro e claro)"
    mainClass.set("com.nokylabs.ui.galeria.CapturasKt")
    classpath = sourceSets["main"].runtimeClasspath
    args(rootProject.file("capturas").absolutePath)
}

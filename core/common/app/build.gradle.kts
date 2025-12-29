import com.codingfeline.buildkonfig.compiler.FieldSpec
import ir.amirroid.mafiauto.buildSrc.AppInfo

plugins {
    alias(libs.plugins.local.kotlin)
    alias(libs.plugins.local.android.library)
    alias(libs.plugins.buildKonfig)
}

buildkonfig {
    packageName = AppInfo.namespace
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "APP_VERSION", rootProject.version.toString())
        buildConfigField(FieldSpec.Type.STRING, "FLAVOR", "default")
    }
    defaultConfigs("bazaar") {
        buildConfigField(FieldSpec.Type.STRING, "APP_VERSION", rootProject.version.toString())
        buildConfigField(FieldSpec.Type.STRING, "FLAVOR", "bazaar")
    }
}
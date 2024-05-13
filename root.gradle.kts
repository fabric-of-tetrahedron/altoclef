plugins {
    id("fabric-loom") version "1.6-SNAPSHOT" apply false
    id("com.replaymod.preprocess") version "c2041a3"
}

subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://libraries.minecraft.net/")
        maven("https://repo.spongepowered.org/repository/maven-public/")
        maven("https://github.com/jitsi/jitsi-maven-repository/raw/master/releases/")
        maven("https://maven.fabricmc.net/")
        maven("https://jitpack.io")
    }
}

preprocess {
    val mc12004 = createNode("1.20.4", 12004, "yarn")
//    val mc12002 = createNode("1.20.2", 12002, "yarn")
    val mc12001 = createNode("1.20.1", 12001, "yarn")

//    mc12004.link(mc12002)
    mc12004.link(mc12001)
}
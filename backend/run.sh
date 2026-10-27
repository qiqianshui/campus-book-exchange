#!/usr/bin/env bash
# 一键启动后端（Git Bash / WSL 用）。已假设你按说明装好 JDK17 在 C:/Users/ASUS/tools/jdk17.0.21_0
export JAVA_HOME="C:/Users/ASUS/tools/jdk17.0.21_0"
export MAVEN_HOME="C:/Users/ASUS/tools/apache-maven-3.9.9"
export PROJ="$(cd "$(dirname "$0")" && pwd)"
exec "$JAVA_HOME/bin/java" \
  -classpath "$MAVEN_HOME/boot/plexus-classworlds-2.8.0.jar" \
  "-Dclassworlds.conf=$MAVEN_HOME/bin/m2.conf" \
  "-Dmaven.home=$MAVEN_HOME" \
  "-Dmaven.multiModuleProjectDirectory=$PROJ" \
  org.codehaus.plexus.classworlds.launcher.Launcher \
  -f "$PROJ/pom.xml" spring-boot:run

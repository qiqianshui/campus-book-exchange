@echo off
REM 一键启动后端（Windows 双击即可）。已假设你按说明装好 JDK17 在 C:\Users\ASUS\tools\jdk17.0.21_0
set JAVA_HOME=C:\Users\ASUS\tools\jdk17.0.21_0
set PATH=%JAVA_HOME%\bin;%PATH%
C:\Users\ASUS\tools\apache-maven-3.9.9\bin\mvn.cmd spring-boot:run

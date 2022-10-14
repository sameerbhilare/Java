REM Runs on Windows
javac --module-path mods --module-source-path "./*/src/main/java" -d out --module com.demo.movieflix.fe
jar --create --file mods/movieflix-fe.jar --main-class com.demo.movieflix.fe.App -C out/com.demo.movieflix.fe/ .
jar --create --file mods/movieflix-be.jar -C out/com.demo.movieflix.be/ .
java --module-path mods --module com.demo.movieflix.fe
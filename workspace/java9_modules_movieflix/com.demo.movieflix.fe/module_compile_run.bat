REM Runs on Windows
javac -d target/classes^
 src/main/java/com/demo/movieflix/fe/recommendation/MovieRecommendationController.java^
 src/main/java/com/demo/movieflix/fe/App.java^
 src/main/java/module-info.java

jar -c -f target/movie_fe.jar --main-class com.demo.movieflix.fe.App^
 -C target/classes/ .

RMDIR "target/classes" /S /Q

java -p target --module com.demo.movieflix.fe
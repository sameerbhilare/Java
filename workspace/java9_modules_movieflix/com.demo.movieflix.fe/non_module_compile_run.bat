REM Runs on Windows
javac -d target/classes^
 src/main/java/com/demo/movieflix/fe/recommendation/MovieRecommendationController.java^
 src/main/java/com/demo/movieflix/fe/App.java

jar -c -f target/movie_fe.jar -C target/classes/ .

RMDIR "target/classes" /S /Q

java -cp target/movie_fe.jar com.demo.movieflix.fe.App
@echo off
cd ..
cd..
javac -cp "bin" -d "bin" -sourcepath "src" ./src/game/*.java
javac -cp "bin" -d "bin" -sourcepath "src" ./src/netwrk/*.java
javac -cp "bin" -d "bin" -sourcepath "src" ./src/sound/*.java
javac -cp "bin" -d "bin" -sourcepath "src" ./src/ui/*.java
javac -cp "bin" -d "bin" -sourcepath "src" ./src/ui/frames/*.java
javac -cp "bin" -d "bin" -sourcepath "src" ./src/ui/panels/*.java
pause
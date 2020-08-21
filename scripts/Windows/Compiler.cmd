@echo off
cd ..
cd..
javac -cp "bin" -d "bin" ./src/game/*.java
javac -cp "bin" -d "bin" ./src/netwrk/*.java
javac -cp "bin" -d "bin" ./src/sound/*.java
javac -cp "bin" -d "bin" ./src/ui/*.java
javac -cp "bin" -d "bin" ./src/ui/frames/*.java
javac -cp "bin" -d "bin" ./src/ui/panels/*.java
pause
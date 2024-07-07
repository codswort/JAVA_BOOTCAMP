# Удаление папок target и lib

rm -r ImagesToChar/target ImagesToChar/lib


# Создание папок target и lib

mkdir ImagesToChar/target ImagesToChar/lib


# Загрузка jcommander и JCDP

wget https://repo1.maven.org/maven2/com/beust/jcommander/1.78/jcommander-1.78.jar
wget https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar


# Перемещение jcommander и JCDP в папку ImagesToChar/lib/

mv *.jar ImagesToChar/lib/


# компиляция

javac -d ImagesToChar/target -sourcepath ImagesToChar/src/java -cp ImagesToChar/lib/\* ImagesToChar/src/java/edu/school21/printer/app/Main.java ImagesToChar/src/java/edu/school21/printer/logic/*.java


# Копирование необходимых файлов
# cp -R [путь, откуда нужно копировать] [путь, куда нужно копировать]

cp -R ImagesToChar/src/resources ImagesToChar/target


# Извлечение файлов библиотек JCDP и jcommander из jar-архивов в ImagesToChar/lib
cd ImagesToChar/target
jar xf ../lib/JCDP-4.0.0.jar
jar xf ../lib/jcommander-1.78.jar
cd ../..

# Создание jar-архива
# jar cvfm [путь и имя будущего jar-архива] [путь к манифест-файлу] -С [путь, откуда нужно включать файлы в jar-архив]

jar cvfm ImagesToChar/target/myJar.jar ImagesToChar/src/manifest.txt -C ImagesToChar/target .


# запуск программы с параметрами
# java -jar [путь, где находится jar-архив] [2 аргумента:
            первый - на какой цвет перекрасить белый цвет на изображении;
            второй - на какой цвет перекрасить черный цвет на изображении, Например: --white=RED --black=GREEN]

java -jar ImagesToChar/target/myJar.jar --white=RED --black=GREEN

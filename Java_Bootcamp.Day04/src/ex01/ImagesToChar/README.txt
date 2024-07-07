# Удаление содержимого папки target

rm -r ImagesToChar/target


# Создание папки target

mkdir ImagesToChar/target


# компиляция
# javac -d [путь, куда скомпилированные классы должны быть сохранены] [путь к исходному файлу, который нужно скомпилировать]

javac -d ImagesToChar/target ImagesToChar/src/java/edu/school21/printer/app/Main.java


# Копирование необходимых файлов
# cp -R [путь, откуда нужно копировать] [путь, куда нужно копировать]

cp -R ImagesToChar/src/resources ImagesToChar/target


# Создание jar-архива
# jar cvfm [путь и имя будущего jar-архива] [путь к манифест-файлу] -С [путь, откуда нужно включать файлы в jar-архив]

jar cvfm ImagesToChar/target/myJar.jar ImagesToChar/src/manifest.txt -C ImagesToChar/target .


# запуск программы с параметрами
# java -jar [путь, где находится jar-архив] [2 аргумента:
            первый - знак белого цвета, т.е. какой знак будет отображаться вместо белого цвета на изображении;
            второй - знак черного цвета, т.е. какой знак будет отображаться вместо черного цвета на изображении]

java -jar ImagesToChar/target/myJar.jar . o


# Удаление содержимого папки target

rm -r ImagesToChar/target


# Создание папки target

mkdir ImagesToChar/target


# компиляция
# javac -d [путь, куда скомпилированные классы должны быть сохранены] [путь к исходному файлу, который нужно скомпилировать]

javac -d ImagesToChar/target ImagesToChar/src/java/edu/school21/printer/app/Main.java


# запуск программы с параметрами
# java -cp [путь, где находятся скомпилированные классы] [путь к исходному файлу] [3 аргумента:
            первый - знак белого цвета, т.е. какой знак будет отображаться вместо белого цвета на изображении;
            второй - знак черного цвета, т.е. какой знак будет отображаться вместо черного цвета на изображении;
            третий  - полный путь к двухцветному изображению]

java -cp ImagesToChar/target/ ImagesToChar/src/java/edu.school21.printer.app.Main . i /home/codswort/projects/java/Java_Bootcamp.Day04-1/src/ex00/it.bmp
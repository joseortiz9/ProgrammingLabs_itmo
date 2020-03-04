# Pro-Lab5-ITMO-2020

**Variant**: 313271

**Command**: 
* java -jar target/Lab5-1.0-jar-with-dependencies.jar data.xml - with file
* java -jar target/Lab5-1.0-jar-with-dependencies.jar - no args

Реализовать консольное приложение, которое реализует управление коллекцией объектов в интерактивном режиме. В коллекции необходимо хранить объекты класса `Dragon`, описание которого приведено ниже.

### Разработанная программа должна удовлетворять следующим требованиям:

* Класс, коллекцией экземпляров которого управляет программа, должен реализовывать сортировку по умолчанию.
* Все требования к полям класса (указанные в виде комментариев) должны быть выполнены.
* Для хранения необходимо использовать коллекцию типа `java.util.HashMap`
* При запуске приложения коллекция должна автоматически заполняться значениями из файла.
* Имя файла должно передаваться программе с помощью: аргумент командной строки.
* Данные должны храниться в файле в формате xml
* Чтение данных из файла необходимо реализовать с помощью класса `java.io.BufferedInputStream`
* Запись данных в файл необходимо реализовать с помощью класса `java.io.FileOutputStream`
* Все классы в программе должны быть задокументированы в формате `javadoc`.
* Программа должна корректно работать с неправильными данными (ошибки пользовательского ввода, отсутсвие прав доступа к файлу и т.п.).


### В интерактивном режиме программа должна поддерживать выполнение следующих команд:

* *help:* вывести справку по доступным командам
* *info:* вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
* *show:* вывести в стандартный поток вывода все элементы коллекции в строковом представлении
* *insert key {element}:* добавить новый элемент с заданным ключом
* *update id {element}:* обновить значение элемента коллекции, id которого равен заданному
* *remove_key key:* удалить элемент из коллекции по его ключу
* *clear:* очистить коллекцию
* *save:* сохранить коллекцию в файл
* *execute_script file_name:* считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
* *exit:* завершить программу (без сохранения в файл)
* *replace_if_lowe key {element}:* заменить значение по ключу, если новое значение меньше старого
* *remove_greater_key key:* удалить из коллекции все элементы, ключ которых превышает заданный
* *remove_lower_key key:* удалить из коллекции все элементы, ключ которых меньше, чем заданный
* *filter_contains_name name:* вывести элементы, значение поля name которых содержит заданную подстроку
* *filter_starts_with_name name:* вывести элементы, значение поля name которых начинается с заданной подстроки
* *print_descending:* вывести элементы коллекции в порядке убывания


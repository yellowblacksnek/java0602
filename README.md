Описание алгоритма:

1. Фильтрация дублирующихся строк путем добавления всех строк в HashSet.
2. Поиск подходящей группы для строки путем прохода по всем элементам строки и поиска уже существующих элементов в маппинге позиция_элемента->значение->группа.
3. Если группа не найдена, создать новую группу и добавить ее в список групп.
4. Найти конечную* группу найденной группы.
5. Добавить строку в список строк группы.
6. Обновить маппинги позиция->значение->группа для всех непустых элементов.
7. Если маппинг позиция->значение->группа уже существует и не равен текущей группе:
   + Получить конечную группу.
   + Добавить строки этой группы в текущую.
   + Пометить группу как поглощенную*.

Конечная группа - группа, которая не была поглощена и не имеет ссылку на поглотившую ее группу. 
Поиск такой осуществляется проходом по ссылкам на поглотившие группы до встречи null значения.

Поглощенная группа - группа уже не является настоящей группой (была поглощена) и имеет ссылку на группу, которая ее поглотила.

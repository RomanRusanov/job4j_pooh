# job4j_pooh
[![Build Status](https://travis-ci.org/RomanRusanov/job4j_pooh.svg?branch=main)](https://travis-ci.org/github/RomanRusanov/job4j_pooh)
[![codecov](https://codecov.io/gh/RomanRusanov/job4j_pooh/branch/main/graph/badge.svg)](https://codecov.io/gh/RomanRusanov/job4j_pooh)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/301ec3c0c009403ba544634c72c93fe7)](https://www.codacy.com/gh/RomanRusanov/job4j_pooh/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=RomanRusanov/job4j_pooh&amp;utm_campaign=Badge_Grade)

В этом проекте мы сделаем аналог асинхронной очереди RabbitMQ.
Приложение запускает Socket и ждем клиентов.
Клиенты могут быть двух типов: отправители (client), получатели (subscriver).
В качестве протокола будет использовать HTTP. Сообщения в формате JSON.
Существуют два режима: queue, topic.

Queue. 
Отправитель посылает сообщение с указанием очереди.
Получатель читает первое сообщение и удаляет его из очереди. 
Если приходят несколько получателей, то они читают из одной очереди. 
Уникальное сообщение может быть прочитано, только одним получателем.

Пример запросов.
POST /queue
{
  "queue" : "weather",
  "text" : "temperature +18 C"
}

GET /queue/weather
{
  "queue" : "weather",
  "text" : "temperature +18 C"
}

Topic.
Отправить посылает сообщение с указанием темы.
Получатель читает первое сообщение и удаляет его из очереди. 
Если приходят несколько получателей, то они читают отдельные очереди.
 POST /topic
{
  "topic" : "weather",
  "text" : "temperature +18 C"
}

GET /topic/weather
{
  "topic" : "weather",
  "text" : "temperature +18 C"
}
## Задание
*   Создайте репозиторий job4j_pooh
*  Напишите код согласно техническому заданию
*  В коде не должно быть синхронизации все нужно сделать на Executors и concurrent коллекциях
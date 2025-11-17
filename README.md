# Weather SDK

Weather SDK — это Java-библиотека для получения данных о погоде из OpenWeatherMap (или аналогичных API).  
SDK предоставляет удобный способ получать погодные данные по названию города с поддержкой кэширования и двух режимов работы.

---

## Описание

SDK предоставляет:

- Доступ к погодным данным по названию города.
- Кэширование последних 10 городов для снижения количества запросов.
- Два режима работы:
  - **ON_DEMAND** — обновление данных только при запросе.
  - **POLLING** — автоматическое обновление данных всех кэшированных городов с определенным промежутком (по дефолту раз в 5 минут).
- Обработку ошибок API через специализированные исключения (`CityNotFoundException`, `InvalidApiKeyException`, `WeatherSdkException`).
- Создание экземпляров через WeatherFactory. На один ключ один объект

---

## Установка

### Maven

Соберите SDK в JAR:

```bash
mvn clean install
```
Добавьте зависимость из локального репозитория в pom.xml

```bash
      <dependency>
          <groupId>com.hh</groupId>
          <artifactId>WeatherApplication</artifactId>
          <version>1.0</version>
      </dependency>
```

ЛИБО же можно воспользоваться jitpack и скачать зависимость напрямую оттуда добавив в pom.xml

```bash
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
```

а так же

```bash
      <dependency>
          <groupId>com.github.Sanek3011</groupId>
          <artifactId>weatherApp</artifactId>
          <version>ceac5d4c36</version>
      </dependency>
```

Инициализация и пример использования
```java
String apiKey = "ваш ApiKey";
WeatherClient client = WeatherClientFactory.createClient(String apiKey, WorkMode workmode); // Доступны два режима работы WorkMode.ON_DEMAND и WorkMode.Polling
void WeatherClientFactory.remove(String apiKey) // Удаление клиента. Так же будет остановлен процесс обновления polling при удалении объекта.
boolean WeatherClientFactory.contains(String apikey) // Создан ли уже клиент с таким ключом
WeatherData data = client.getWeather(String cityName); // получаем готовую отформатированную ДТО без лишних полей. Далее можно пользоваться нужными нам полями
WeatherResponse response = client.getRawData(String cityName); // получаем сырой json объект который присылает нам сервер
client.stopPolling(); // на случай, если нам нужно остановить обновление данных
client.changeIntervalPolling(long time, TimeUnit unit) // на случай если нам нужно изменить время обновления данных в кеше. По дефолту установлено 5 минут
```






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
  - **POLLING** — автоматическое обновление данных всех кэшированных городов каждые 30 секунд.
- Обработку ошибок API через специализированные исключения (`CityNotFoundException`, `InvalidApiKeyException`, `WeatherSdkException`).
- Null-safe DTO `WeatherData` для удобного использования.

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
WeatherClient client = new WeatherClient(apiKey, WorkMode.ON_DEMAND); // Доступны два режима работы WorkMode.ON_DEMAND и WorkMode.Polling
WeatherData data = client.getWeather("Moscow"); // получаем готовую отформатированную ДТО без лишних полей. Далее можно пользоваться нужными нам полями
```

Кеш на 10 последних городов с данными не старее, чем 10 минутами ранее.

Режим по запросу. Кеш обновляется только вручную и при запросе вами очередной погоды в городе
```
WorkMode.ON_DEMAND
```
Режим постоянного обновления. Кеш обновляется с периодичностью 5 минут, освежая данные погоды городов, находящихся в данный момент в кеше.
```
WorkMode.Polling
```



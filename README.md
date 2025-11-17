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
  - **POLLING** — автоматическое обновление данных всех кэшированных городов каждые 5 минут.
- Обработку ошибок API через специализированные исключения (`CityNotFoundException`, `InvalidApiKeyException`, `WeatherSdkException`).

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



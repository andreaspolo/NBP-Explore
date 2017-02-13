# NBP-Explore

Aplikacja internetowa służąca do przeglądania oraz analizy kursów walut. Aplikacja zasilana jest danymi udostępnionymi przez API Narodowego Banku Polskiego api.nbp.pl. Podzielona jest na 3 moduły:

- Kurs walut  - moduł udostępnia kursy dostępnych walut na konkretny dzień. Standardowo pobiera ostatni kurs.
- Analiza kursu walutowego – moduł udostępnia informacje historyczne o konkretnej walucie. Dane są pobierane wg zadanego interwału czasu, standardowo ostatnie 3 miesiące. Moduł udostępnia również wykres na podstawie pobranych danych.
- Porównanie kursu walut – moduł umożliwia pobranie danych historycznych kilku walut i wyświetlenie danych do porównania. Moduł udostępnia również wykres na podstawie pobranych danych.

Aplikacja udostępnia 2 wersje językowe: 
- język polski
- język angielski
- język niemiecki


NBP Explore został stworzony w celu poszerzania doświadczenia oraz wiedzy w tworzeniu aplikacji w języku Java. Podczas tworzenia zostału użyte następujące technologie:

- spring-boot
- spring-web-mvc
- jersey - biblioteka Java do obsługi formatu JSON.
- jquery - biblioteka javascipt
- bootstrap - framework do tworzenia interfejsu stron internetowych.
- bootstrap-datepicker - biblioteka do tworzenia onka wyboru daty dla bootstrap
- chartjs - biblioteka do tworzenia wykresów.
- Spring Tools Suit – IDE oparte na Elipse.


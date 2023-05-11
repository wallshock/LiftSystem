
# O środowisku
Zadanie było realizowane przy pomocy zintegrowanego środowisko programistycznego IntelliJ IDEA, do uruchomienia używamy sbt.

# Wymagania

- IntelliJ IDEA 2023.1
- Scala 3.3.2
- Amazon Corretto 17.0.4 SDK
- sbt 1.8.2

# Opis rozwiązania
- Zdecydowałem się zaimplementować algorytm LOOK, W przypadku algorytmu LOOK, winda porusza się tylko w jednym kierunku do maksymalnego lub minimalnego piętra na liście zgłoszeń pasażerów, a następnie zmienia kierunek, gdy osiągnie jedno z tych pięter. W ten sposób, pasażerowie na piętrach po drodze do docelowego piętra nie zostaną zignorowani. Dodałem klasę Simulator oraz proste GUI które pozwala testować różne parametry, wybieramy ilość pięter oraz wind. Symulator możemy wyłączać, a także dodawać zgłoszenia manualnie 

- Aby zwiększać częstotliwość dodawania zgłoszeń w symulacji wymagana jest zmiana następującego kodu, gdzie nalezy zmienic randomInt na dowolną wartość x, x>=3
```
  def run(): Unit = {
    init()
    while(true) {
      if(simulate) {
        val randomInt = Random.nextInt(5)
        if (randomInt == 1) randomFloorPanelPickup(AVSysytemHq)
        else if (randomInt == 2) randomElevatorPanelTarget(AVSysytemHq)
      }
      observer.updateGuiBefore()
      AVSysytemHq.elevatorSystem.step()
      observer.updateGuiAfter()
    }

  }
```
# Co można było zrobić lepiej

- Projekt zdecydowanie cierpi na tym, że brakuje w nim testów,z powodu ograniczonego czasu na realizację projektu, nie udało mi się napisać automatycznych testów jednostkowych dla mojego kodu. Jednakże, w celu zapewnienia jakości i sprawności kodu, przetestowałem aplikację manualnie poprzez ręczne testowanie różnych przypadków użycia. 

- Multithreading: W symulatorze systemu wind, lepiej byłoby mieć każdą windę na osobnym wątku z kilku powodów. Przede wszystkim, korzyścią wynikającą z tego rozwiązania jest poprawa wydajności systemu, dzięki równoległemu przetwarzaniu żądań dotyczących poruszania windami. Ponadto, osobny wątek dla każdej windy ułatwia kontrolowanie i zarządzanie każdą windą oddzielnie, co z kolei wpływa na łatwiejszą obsługę oraz lepszą skalowalność systemu. Dodatkowo, rozwiązanie to minimalizuje ryzyko awarii, ponieważ błędy w jednej windzie nie wpłyną na działanie pozostałych.

- Przy projektowaniu i implementacji kodu, starałem się zastosować najlepsze praktyki programistyczne i dostosować się do standardów. Niestety, ze względu na ograniczenia czasowe, nie udało mi się zaplanować optymalnej struktury kodu od samego początku. W efekcie, w niektórych przypadkach, struktura kodu może wydawać się nie do końca przemyślana. Jestem świadomy, że kod można było zaplanować bardziej spójnie i czytelnie, a w niektórych fragmentach można było uniknąć powtórzeń.

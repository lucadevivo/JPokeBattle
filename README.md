# JPokeBattle - Simulatore di Battaglie Pokémon in Java

JPokeBattle è un simulatore di combattimenti Pokémon ispirato ai titoli classici per GameBoy. Originariamente sviluppato in ambiente Eclipse, il progetto è stato aggiornato per supportare lo sviluppo moderno con VS Code e la gestione delle build tramite Maven.

## Descrizione del progetto

Il software ricrea le dinamiche di lotta Pokémon, includendo una fase di esplorazione su mappa e un sistema di combattimento a turni completo. Sono presenti meccaniche di collisione, gestione di NPC, apprendimento di mosse ed evoluzioni.

## Requisiti di sistema

- Java JDK 17 o superiore.
- Maven (necessario per la compilazione da riga di comando).
- VS Code con "Extension Pack for Java" (opzione consigliata per lo sviluppo).

## Compilazione ed Esecuzione

### Utilizzo con VS Code
Il progetto include le configurazioni necessarie nella cartella `.vscode`. È sufficiente aprire la root del progetto con VS Code e premere `F5` per avviare il gioco.

### Generazione del pacchetto eseguibile (JAR)
Tramite Maven è possibile generare un file JAR che include tutte le risorse multimediali necessarie (immagini, suoni e mappe):
```bash
mvn clean package
```
Il file generato sarà disponibile in `target/JPokeBattle-1.0-SNAPSHOT.jar` e potrà essere eseguito con:
```bash
java -jar target/JPokeBattle-1.0-SNAPSHOT.jar
```

## Organizzazione dei file

- `src/`: Codice sorgente Java suddiviso nei package `battle` e `map`.
- `res/`: Asset multimediali (immagini, suoni, font e file di testo per le mappe).
- `pom.xml`: Configurazione Maven per dipendenze e build.

---
Sviluppato da: Luca De Vivo, Lorenzo Gentilezza.

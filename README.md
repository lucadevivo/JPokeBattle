# JPokeBattle - Java Pokemon Battle Simulator

JPokeBattle è un simulatore di battaglie Pokémon ispirato ai classici titoli per GameBoy (Pokémon Rosso/Blu). Sviluppato originariamente in Eclipse, il progetto è stato modernizzato per supportare **VS Code** e **Maven**.

## 🚀 Caratteristiche

- **Sistema di Battaglia**: Implementazione accurata delle meccaniche di combattimento Pokémon.
- **Esplorazione**: Mappa interattiva con gestione delle collisioni e NPC.
- **Grafica Retro**: Asset originali e interfaccia fedele allo stile 8-bit.
- **Audio**: Effetti sonori e colonne sonore originali del gioco.
- **Evoluzioni e Livellamento**: Sistema di crescita dei Pokémon e apprendimento nuove mosse.

## 🛠️ Requisiti

- **Java JDK 17** o superiore.
- **Maven** (opzionale, per la build da riga di comando).
- **VS Code** (consigliato) con l'estensione "Extension Pack for Java".

## 💻 Come Giocare / Sviluppare

### Con VS Code (Consigliato)
1. Apri la cartella del progetto in VS Code.
2. Installa l'estensione **Extension Pack for Java**.
3. Premi `F5` per avviare il gioco (la configurazione è già presente in `.vscode/launch.json`).

### Con Maven (Per generare l'eseguibile)
Per creare un file JAR eseguibile che includa tutte le risorse:
```bash
mvn clean package
```
Il file generato si troverà nella cartella `target/JPokeBattle-1.0-SNAPSHOT.jar`. Puoi eseguirlo con:
```bash
java -jar target/JPokeBattle-1.0-SNAPSHOT.jar
```

## 📂 Struttura del Progetto
- `src/`: Codice sorgente Java organizzato in package (`battle`, `map`).
- `res/`: Risorse multimediali (immagini, suoni, font, mappe).
- `pom.xml`: Configurazione Maven per la gestione delle dipendenze e build.
- `.vscode/`: Configurazioni per lo sviluppo ottimale in Visual Studio Code.

---
Sviluppato da **Luca De Vivo**.

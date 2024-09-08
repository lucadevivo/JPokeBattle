package battle.logic;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestoreProfili {

    private static final String FILE_PROFILI = "profili.dat";

    public static void salvaProfilo(ProfiloUtente profilo) throws IOException {
        List<ProfiloUtente> profili = caricaProfili();
        profili.add(profilo);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PROFILI))) {
            oos.writeObject(profili);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<ProfiloUtente> caricaProfili() {
        File file = new File(FILE_PROFILI);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<ProfiloUtente>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

package tools;

import java.io.*;

public class GameFile {
    private final String ruta;

    public GameFile(String ruta) {
        this.ruta = ruta;
        // ensure folder exists
        File f = new File(ruta).getParentFile();
        if (f != null && !f.exists()) f.mkdirs();
    }

    public void guardar(Progreso p) throws IOException {
        try (FileWriter fw = new FileWriter(ruta)) {
            fw.write("puntaje=" + p.puntaje + "\n");
            fw.write("jugador=" + p.nombre + "\n");
            fw.write("vidas=" + p.nLives + "\n");
            fw.write("level=" + (p.idxLevel) + "\n");
        }
    }

    public Progreso cargar() throws IOException {
        File file = new File(ruta);
        if (!file.exists()) return null;
        int puntaje = 0;
        String nombre = "player";
        int nLives = 5;
        int idxLevel = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("puntaje=")) puntaje = Integer.parseInt(line.split("=")[1].trim());
                if (line.startsWith("jugador=")) nombre = line.split("=")[1].trim();
                if (line.startsWith("vidas=")) nLives = Integer.parseInt(line.split("=")[1].trim());
                if (line.startsWith("level=")) idxLevel = Integer.parseInt(line.split("=")[1].trim());
            }
        }
        return new Progreso(puntaje, nombre,nLives, idxLevel);
    }

    public static class Progreso {
        public int puntaje;
        public String nombre;
        public int nLives;
        public int idxLevel;
        public Progreso(int puntaje, String nombre,int nLives, int idxLevel) {
            this.puntaje = puntaje; this.nombre = nombre;this.idxLevel = idxLevel;this.nLives = nLives;
        }
    }
}

JuegoPlataformaFX - Versión adaptada a JavaFX

Estructura:
- src/: archivos .java (sin paquete para facilidad en VS Code)
- assets/images: imágenes usadas por el juego (player, enemies)
- datos/progreso.txt: archivo de persistencia usado por ArchivoJuego

Cómo compilar y ejecutar (recomendado en VS Code con JavaFX SDK):
1. Instala Java 11+ y JavaFX SDK.
2. Compila:
   javac --module-path C:/Users/ROG/Downloads/openjfx-25.0.1_windows-x64_bin-sdk/javafx-sdk-25.0.1/lib --add-modules javafx.controls -d out src/*.java
3. Ejecuta:
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls -cp out Main

Nota: Alternativamente, crea un proyecto en VS Code y configura el 'module-path' en la configuración de lanzamiento.

Controles:
- Flechas izquierda/derecha: mover
- Barra espaciadora: saltar
- S: guardar progreso (datos/progreso.txt)

Archivos generados automáticamente como placeholders: images en assets/images.

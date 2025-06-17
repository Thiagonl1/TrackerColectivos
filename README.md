# TrackerColectivos

**Aplicación Android** que permite visualizar la ubicación en tiempo real de un colectivo en un mapa utilizando coordenadas enviadas desde otra app hacia un servidor.
Funcionalidades

- Visualización en mapa con OpenStreetMap
- Envío y recepción de coordenadas desde base de datos remota
- Actualización automática de la ubicación del colectivo
- Visualización de horarios en formato PDF
- Interfaz sencilla con opción de seguir el recorrido
  

Tecnologías utilizadas

- Java (Android)
- OpenStreetMap (OSMDroid)
- MySQL
- PHP (para conexión con servidor)
- Volley (para peticiones HTTP)
- Android Studio

Estructura del Proyecto

- `MainActivity.java`: actividad principal, gestiona navegación
- `HomeFragment.java`: muestra botones e inicia seguimiento
- `MapsActivity.java`: mapa interactivo con marcador (colectivo) actualizado
- `MuestraMenu.java`: menú personalizado de navegación
- `Conexion.php`: archivo PHP que recibe/enlaza las coordenadas


## Cómo correr el proyecto

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Thiagonl1/TrackerColectivos.git
2. Abrir el proyecto en Android Studio.

3. Configurar permisos de localización en el AndroidManifest.xml.

4. Asegurarse de tener un servidor con PHP + MySQL corriendo para obtener los datos.

5. Reemplazar las URLs del backend si es necesario.

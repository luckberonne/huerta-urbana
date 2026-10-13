# Huerta Urbana

App Android (Kotlin + Jetpack Compose + Room) con una lista de "qué hacer hoy" para una huerta urbana: riego cada 2 días y cosecha a los 45 días, con vista de calendario. Los datos se guardan en el teléfono.

## Requisitos
- JDK 21
- Android SDK (plataforma 37) y un emulador o dispositivo Android 8.0+ (minSdk 26)
- Archivo `local.properties` con la ruta del SDK, por ejemplo `sdk.dir=/home/usuario/Android/Sdk` (no se sube al repositorio)

## Ejecutar
```
./gradlew installDebug
```
Después abrir **Huerta Urbana** en el emulador o dispositivo.

## Tests
```
./gradlew testDebugUnitTest
```

## Documentación de planificación
En `devpost/`: `scope.md`, `prd.md`, `spec.md`, `checklist.md` y `app-map.html`.

---
doc: spec
status: approved
---

# Huerta Urbana — Technical Spec

## How This Works, In Plain Language
La app es una aplicación Android que corre en el celular (en el emulador para el video de demo). Tiene tres cosas:
- **Pantallas**: la lista de hoy, el calendario y el formulario para cargar una siembra.
- **Una pequeña base de datos en el teléfono** (Room): guarda las siembras y los riegos que marcaste. Así, al cerrar y abrir la app, todo sigue ahí. No hay cuentas ni servidor.
- **Una regla simple**: dada la fecha de siembra, riega cada 2 días y cosecha a los 45 días. Con eso la app calcula qué toca hoy y qué mostrar en el calendario.

Es lo más simple que cumple el PRD: nada sale del teléfono.

## The Core Journey Through the System
PRD ref: `prd.md > The Core Journey`.
1. Abrís la app → se lee la base de datos → la regla calcula las tareas de hoy → ves la lista (o el mensaje de lista vacía).
2. Tocás "Agregar siembra", elegís la fecha y guardás → se guarda en la base de datos → la lista y el calendario se actualizan.
3. Marcás "Regar lechuga" como hecha → se guarda ese riego → la tarea figura como hecha.
4. Abrís el calendario → la regla calcula riegos y cosecha del mes → ves las marcas.
5. A los 45 días de la siembra, la regla agrega "Cosechar lechuga" a la lista de ese día.
6. Cerrás y volvés a abrir → los datos se leen de nuevo de la base de datos.

## Stack
- **Kotlin + Jetpack Compose** (interfaz): [Compose](https://developer.android.com/jetpack/compose). Estándar de Android, corre en el emulador ya instalado.
- **Room** (datos locales): [Room](https://developer.android.com/training/data-storage/room). Hace que la app recuerde lo cargado sin servidor.
- **Navigation Compose**: [Navigation](https://developer.android.com/jetpack/compose/navigation). Moverse entre lista y calendario.
- **Gradle wrapper** para compilar: se genera con el proyecto. Los números de versión se verifican en el primer paso del build; no están confirmados todavía.
- Tradeoff aceptado: hay que instalar un JDK (JDK 21) y el primer build de Gradle es lento.

## Where It Runs and How Someone Tries It
- Corre en el emulador de Android Studio ya instalado en `~/Android/Sdk`, con aceleración KVM. Requiere JDK 21 (instalación pendiente: `sudo pacman -S --needed jdk21-openjdk`).
- Comando de arranque (se confirma en el build): compilar con `./gradlew installDebug` y abrir la app en el emulador. Se graba el video de demo desde el emulador.
- Entrega del hackathon: video corto y repo público en GitHub. No se despliega nada.

## Look and Feel
PRD ref: `prd.md > Look and Feel`. Dirección del aprendiz: natural y verde. Detalles de abajo son suposición aprobada junto con el PRD.
- Fondo claro cálido, acentos en verdes suaves, detalles en tonos tierra.
- Tipografía redondeada y legible, esquinas redondeadas, estilo limpio y cálido.
- Textos de la interfaz en español, cortos y amables.
- Todos los colores salen del tema de la app, no valores sueltos.

## Components

### Pantalla Lista de hoy
Muestra las tareas de hoy y el botón "Agregar siembra"; permite marcar tareas como hechas. Muestra el mensaje de lista vacía.
PRD ref: `prd.md > Features and Behavior > Lista de hoy`, `prd.md > States and Boundaries`.

### Pantalla Formulario de siembra
Elegir la fecha de siembra de la lechuga y guardar. No guarda sin fecha.
PRD ref: `prd.md > Features and Behavior > Cargar una siembra`.

### Pantalla Calendario
Vista de mes con marcas de riego y de cosecha.
PRD ref: `prd.md > Features and Behavior > Calendario`.

### Reglas de la lechuga
Función que, dadas las siembras y los riegos hechos, devuelve las tareas de cada día: riego cada 2 días, cosecha a los 45, riego atrasado pendiente.
PRD ref: `prd.md > Features and Behavior > Reglas de la lechuga`.

### Almacenamiento (Room)
Guarda siembras y riegos hechos. Lo usan las tres pantallas.
PRD ref: `prd.md > States and Boundaries`.

## Data Model
- **Siembra**: id, fecha de siembra. (La planta es siempre lechuga en este POC.)
- **Riego hecho**: id, id de siembra, fecha del riego.
- Viven en la base local del teléfono. Al volver a abrir la app se leen de nuevo y se recalcula todo.

## File Structure
```
huerta-urbana/
├── app/src/main/java/.../
│   ├── MainActivity.kt        # entrada de la app y navegación
│   ├── data/                  # Room: entidades, DAO, base de datos
│   ├── domain/LettuceRules.kt # regla: riego cada 2 días, cosecha a los 45
│   └── ui/
│       ├── today/             # pantalla Lista de hoy
│       ├── sowing/            # formulario de siembra
│       ├── calendar/          # pantalla Calendario
│       └── theme/             # colores, tipografía, formas
├── devpost/                   # planificación del hackathon
└── build.gradle.kts, settings.gradle.kts, gradlew
```

## External Services and Dependencies
Ningún servicio externo. Solo las librerías de Android de la sección Stack.

## Important Failure Modes
- **Fecha de siembra vacía o inválida** → no se guarda y se muestra un mensaje.
- **Se olvidó regar ayer** → la tarea sigue pendiente hoy, marcada como atrasada.
- **Compilación o emulador no arrancan** → se revisan JDK y KVM antes de seguir (primer paso del build).

## What Was Simplified and Why
- **Una sola planta fija (lechuga)** en lugar de un catálogo — alcanza para probar el ciclo; el catálogo queda para después.
- **Lista del día en lugar de notificaciones** — decisión del aprendiz; las notificaciones suman permisos y tareas en segundo plano.
- **Todo local, sin cuentas ni servidor** — la app es personal.
- **Calendario propio con componentes simples** — evita sumar una librería.

## Decisions and Open Issues
- Android nativo (Kotlin + Compose + Room) — recomendación mía, **aceptada por el aprendiz** ("si, acepto"). Tradeoff: instalar JDK y build lento.
- El aprendiz delegó detalles visuales y reglas finas; están marcados como suposición en el PRD y quedaron aprobados con él.
- Incertidumbre del aprendiz: no se identificó una concreta; no se inventó ninguna.
- Pendiente: instalar JDK 21 (lo hace el aprendiz, requiere sudo). Versiones de librerías por verificar en el primer paso.

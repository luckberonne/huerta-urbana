---
doc: checklist
status: approved
---

# Build Checklist

Build mode: fast

## Slices

- [x] **1. Cargás una siembra y ves "Regar lechuga" en la lista de hoy**
  Becomes usable: Una app Android corriendo en el emulador, con tema natural y verde. Tocás "Agregar siembra", elegís una fecha, y la lista de hoy muestra "Regar lechuga" cuando toca (cada 2 días) y "Cosechar lechuga" a los 45 días. Todavía no se guarda al cerrar.
  Why now: Es el núcleo (la lista del día calculada desde la fecha de siembra) y lo más riesgoso es el entorno Android. Probamos todo el camino de punta a punta desde el primer paso.
  PRD ref: `prd.md > The Core Journey` (pasos 1–4, 7), `prd.md > Features and Behavior > Lista de hoy`, `prd.md > Features and Behavior > Cargar una siembra`, `prd.md > Features and Behavior > Reglas de la lechuga`
  Spec ref: `spec.md > Stack`, `spec.md > Components`, `spec.md > Look and Feel`, `spec.md > File Structure`
  Build: Crear el proyecto Android (Kotlin + Compose) según la estructura del spec, el tema verde, la regla de la lechuga, la pantalla de lista de hoy, el formulario de siembra y el estado en memoria.
  Verify (mechanical): Tests unitarios de la regla de la lechuga (riego cada 2 días, cosecha a los 45 días). `./gradlew assembleDebug` sin errores. Instalar en el emulador, abrir la app y confirmar con una captura de pantalla.
  Learner check: Abrí la app, tocá "Agregar siembra", poné una fecha de hoy y confirmá que ves "Regar lechuga". Decime si el aspecto es el natural y verde que imaginabas.
  Commit: `Add today list, sowing form and lettuce rules`

- [x] **2. Marcás el riego como hecho y ves el riego atrasado**
  Becomes usable: Marcar "Regar lechuga" como regada y ver que queda hecha. Si ayer no regaste, la tarea sigue pendiente hoy y aparece como atrasada.
  Why now: Cierra el bucle central de uso diario antes de sumar persistencia, y deja la regla completa.
  PRD ref: `prd.md > The Core Journey` (paso 5), `prd.md > States and Boundaries` (riego atrasado)
  Spec ref: `spec.md > Components > Reglas de la lechuga`, `spec.md > Important Failure Modes`
  Build: Agregar el riego hecho al estado, la acción de marcar en la lista y el caso de riego atrasado en la regla.
  Verify (mechanical): Tests unitarios del riego marcado y del atrasado. Compilar, instalar y confirmar con captura que la tarea queda hecha.
  Learner check: Marcá "Regar lechuga" como regada y confirmá que queda hecha. Cargá una siembra con una fecha de hace 3 días y mirá que el riego aparece como atrasado.
  Commit: `Add mark as watered and overdue watering`

- [x] **3. La app se acuerda de lo cargado al cerrarla**
  Becomes usable: Cerrás y abrís la app y las siembras y los riegos marcados siguen ahí.
  Why now: Es el primer lugar donde puede fallar el modelo de datos y conviene descubrirlo temprano.
  PRD ref: `prd.md > States and Boundaries` (persistencia), `prd.md > The Core Journey` (paso 8)
  Spec ref: `spec.md > Data Model`, `spec.md > Components > Almacenamiento (Room)`
  Build: Agregar Room con las tablas de siembra y riego hecho; leer al abrir y escribir al guardar o marcar. La pantalla no cambia.
  Verify (mechanical): Test de la base de datos. Cargar una siembra, cerrar y reabrir la app en el emulador y confirmar con captura que sigue ahí.
  Learner check: Cargá una siembra, cerrá la app del todo, volvé a abrirla y confirmá que todo sigue.
  Commit: `Persist sowings and waterings with Room`

- [x] **4. Ves los riegos y la cosecha en el calendario**
  Becomes usable: Una pantalla de calendario del mes con las marcas de riego y el día de cosecha, y navegación entre lista y calendario.
  Why now: Es la segunda vista y depende de que los datos y las reglas ya estén firmes.
  PRD ref: `prd.md > Features and Behavior > Calendario`, `prd.md > The Core Journey` (paso 6), `prd.md > Screens and Layout`
  Spec ref: `spec.md > Components > Pantalla Calendario`, `spec.md > Stack` (Navigation Compose)
  Build: Calendario de mes con componentes propios, navegación entre las dos vistas y las marcas calculadas con la regla.
  Verify (mechanical): Tests de las fechas marcadas para una siembra de ejemplo. Compilar, instalar y confirmar con captura las marcas de riego y de cosecha.
  Learner check: Abrí el calendario y confirmá que ves los riegos cada 2 días y el día de cosecha de la siembra que cargaste.
  Commit: `Add calendar view with watering and harvest days`

## Hands-on Checkpoints

- [x] Early usable behavior explored — después del paso 1 (lista de hoy y formulario)
- [x] Final kick-the-tires exploration and feedback completed

## Final Review

- [x] Final review complete — feedback resolved and learner confirms ready to ship

Resultado: el aprendiz probó la app y no pidió cambios (“todo bien, sigamos”). Otros cultivos: investigados, diferidos fuera del POC.

## Code Tour and App Map

- [ ] Learning activity complete — guided route, focused alternative, prior practice connected, or brief recap
- [ ] Optional edit and transfer reflection addressed — offered/declined/already covered/not applicable as appropriate
- [x] `devpost/app-map.html` generated from finished code, checked, and shown, including a project-grounded practice to reuse

Activity and evidence: recap basado en evidencia: la verificación en el emulador detectó que `MainActivity.kt` había quedado vacío (compilaba y los 9 tests pasaban, pero la app se cerraba al abrir).
Route and stops: ruta de referencia en el mapa (no recorrida en vivo): `domain/LettuceRules.kt > tasksFor`, `ui/today/TodayViewModel.kt > markWatered`, `ui/today/TodayScreen.kt > TaskCard`
Edit outcome: not applicable
Reflection: pending
Activity mode: recap

## Revisions

- Tarjetas de tarea con fecha de siembra y número de día, y app en español (es-AR) — feedback del checkpoint temprano: el selector de fecha salía en inglés y las tarjetas no distinguían siembras.
- Navegación entre Hoy y Calendario con dos pestañas simples (estado en memoria) en lugar de Navigation Compose — con solo dos pantallas no justifica sumar la dependencia; el spec la listaba en Stack.

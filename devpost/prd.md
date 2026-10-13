---
doc: prd
status: approved
---

# Huerta Urbana — Product Requirements

Una app personal que le dice a su dueño qué hacer hoy en su huerta urbana (regar, cosechar) para no olvidarse de nada.
Source: `scope.md > The Unique Kernel`, `scope.md > The Core Loop`.

## The Core Journey
1. Abro la app y veo la lista de hoy. (`scope.md > The Core Loop`)
2. Si todavía no cargué nada, veo la lista vacía con un mensaje y el botón para agregar una siembra.
3. Toco el botón "Agregar siembra", completo un formulario con la fecha de siembra de la lechuga y lo guardo.
4. La lista de hoy muestra "Regar lechuga" cuando toca (cada 2 días desde la siembra).
5. Riego y marco la tarea como regada; queda hecha y deja de figurar como pendiente.
6. Abro el calendario y veo los días de riego y el día de cosecha.
7. A los 45 días de la siembra, la lista muestra "Cosechar lechuga".
8. Si cierro la app y vuelvo, todo lo cargado sigue ahí.

## Screens and Layout
Dos vistas, con navegación simple entre ellas:
- **Lista de hoy** (pantalla principal): las tareas de hoy y el botón "Agregar siembra".
- **Calendario**: una vista de mes donde cada día muestra sus riegos y la cosecha.
- **Formulario de siembra**: se abre desde el botón de la lista (modal o pantalla corta).

## Look and Feel
- Dirección del aprendiz: **natural y verde**. El resto lo delegó.
- Suposición (mía): verdes suaves de fondo y de acento, tonos tierra para detalles, tipografía redondeada y legible, esquinas redondeadas, estilo limpio y cálido.

## Features and Behavior

### Lista de hoy
- Muestra las tareas de hoy: "Regar lechuga" y "Cosechar lechuga".
- Cada tarea se puede marcar como hecha.
- Hay un botón "Agregar siembra" siempre visible.
  - [ ] Con una siembra cargada, el día que toca regar aparece "Regar lechuga".
  - [ ] Al marcarla como regada, la tarea figura como hecha.
  - [ ] Al llegar al día 45 desde la siembra aparece "Cosechar lechuga".

### Cargar una siembra
- Formulario con la fecha de siembra de la lechuga (planta fija para el POC).
  - [ ] Al guardar, la siembra aparece en la lista y en el calendario.
  - [ ] Sin fecha, no se puede guardar.

### Calendario
- Vista de mes con marcas de riego (cada 2 días) y de cosecha (día 45).
  - [ ] Cargando una siembra, los días de riego y el día de cosecha quedan marcados.

### Reglas de la lechuga
- Riego cada 2 días desde la fecha de siembra. Cosecha a los 45 días.

## States and Boundaries
- **Primer uso** — lista vacía con mensaje que invita a agregar una siembra.
- **Sin tareas hoy** — mensaje tipo "Nada para hoy", sin pantalla en blanco.
- **Persistencia** — las siembras y los riegos marcados se conservan al cerrar y volver a abrir la app.
- **Riego atrasado** (suposición) — si ayer no regaste, la tarea de riego sigue pendiente hoy, marcada como atrasada.
- **Varias siembras** (suposición) — se pueden cargar varias siembras de lechuga; cada una genera sus propias tareas.
- **Fecha inválida** — no se guarda y se muestra un mensaje.

## Product Decisions
- Pantalla principal es la lista de hoy, no el calendario — es lo que resuelve el olvido de regar.
- Calendario como segunda vista, con riegos y cosecha — para ver qué viene.
- Lista de "qué hacer hoy" en lugar de notificaciones — decisión suya; más simple para el POC.
- Solo lechuga, riego cada 2 días, cosecha a los 45 días — datos elegidos por el aprendiz.
- Carga de siembra con botón y formulario — su forma de cargar la fecha.
- Que la app recuerde lo cargado — pidió persistencia.
- Estilo natural y verde.

## What We're Building
- Lista de hoy con tareas de regar y cosechar la lechuga.
- Marcar como regada.
- Botón y formulario para cargar la fecha de siembra.
- Calendario con riegos y cosecha.
- Persistencia de lo cargado.

## Deferred From the POC
- Otras plantas: una alcanza para probar el ciclo completo.
- Notificaciones: se eligió la lista del día.
- Cuentas y sincronización entre dispositivos: la app es personal.

## Possible Later Enhancements
- Más plantas (rábano, tomate cherry, albahaca, perejil), cada una con su riego y su cosecha.
- Ajustar riego según clima.

## Non-Goals
- Cuentas de usuario y compartir: la app es de uso personal.
- Datos agronómicos precisos: los números son simples y elegidos por el aprendiz.

## Open Questions
- Las suposiciones de arriba (riego atrasado, varias siembras, detalles visuales) las delegó el aprendiz; se confirman en la revisión. Pueden resolverse antes de `4-spec`.
- Tipo de dispositivo (celular, web): se decide en `4-spec`.

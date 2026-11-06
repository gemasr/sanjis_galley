# Journey: Ramen: añadir faltantes a la compra

Ejecutado sobre datos de ejemplo recién cargados (`am force-stop` + `android run`). Resultado global: **✅ todas las acciones pasan**.

## Results

### Action: Verify that the app is on the "Recetas" screen and the card "Ramen del Baratie" is visible ✅
- **Commands**:
  - `android layout --flat`
  - `android screen capture -o /private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/j1.png`
- **Comment**: La lista muestra las 6 tarjetas; "Ramen del Baratie" con la etiqueta "Faltan 2 ingredientes".

### Action: Tap the card "Ramen del Baratie" ✅
- **Commands**:
  - `android input tap "text=Ramen del Baratie"`

### Action: Verify that the recipe detail of "Ramen del Baratie" is shown, with the label "Faltan 2 ingredientes" and the button "Añadir faltantes a la compra" ✅
- **Screenshot**: [j3.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/j3.png)
- **Comment**: Aparecen el título, "Faltan 2 ingredientes", `Fideos de trigo · 200 g (faltan 200 g)`, `Caldo de pescado · 500 ml (faltan 500 ml)` y el botón.

### Action: Tap the button "Añadir faltantes a la compra" ✅
- **Commands**:
  - `android input tap "text=Añadir faltantes a la compra"`

### Action: Verify that the notice "Añadido a la lista de la compra" is visible ✅
- **Screenshot**: [j5.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/j5.png)
- **Comment**: Confirmado en el layout y visualmente (Snackbar sobre la barra inferior). Desaparece solo a los pocos segundos, así que la comprobación debe hacerse justo tras pulsar.

### Action: Tap the "Compra" tab in the bottom navigation bar ✅
- **Commands**:
  - `android input tap "text=/^Compra$/"`
- **Comment**: El selector `text=Compra` coincide también con el botón "Añadir faltantes a la compra" (subcadena), por lo que hay que usar la expresión regular exacta.

### Action: Verify that the text "Fideos de trigo · 200 g" is visible ✅
- **Screenshot**: [j7.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/j7.png)
- **Comment**: Primera línea de la lista, sin marcar.

### Action: Verify that the text "Caldo de pescado · 500 ml" is visible ✅
- **Screenshot**: [j7.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/j7.png)
- **Comment**: Segunda línea de la lista, sin marcar. La lista contiene exactamente esas dos líneas.

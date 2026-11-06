# Journey: Mellorine: añadir nata deja la receta disponible

Ejecutado sobre datos de ejemplo recién cargados (`am force-stop` + `android run`). Resultado global: **✅ todas las acciones pasan**.

Nota: una primera ejecución se descartó a mitad (acción 5) por un selector mío defectuoso (`text=Nata`, sin coincidencia en el momento del toque). No hubo cambios en la despensa; se reinició la app y se repitió el journey completo con selectores exactos. Los resultados de abajo son de la segunda ejecución.

## Results

### Action: Scroll down the list of recipes until the card "Mellorine para Nami y Robin" is fully visible ✅
- **Commands**:
  - `android input scroll "scrollable=true" --dir=down --amount=0.9`
- **Screenshot**: [n1.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/n1.png)
- **Comment**: Con `--until="text=Mellorine…"` solo asoma el título, tapado por la barra inferior; hace falta un scroll mayor para ver la tarjeta entera.

### Action: Verify that the card "Mellorine para Nami y Robin" shows the label "Falta 1 ingrediente" ✅
- **Screenshot**: [n1.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/n1.png)

### Action: Tap the "Despensa" tab in the bottom navigation bar ✅
- **Commands**:
  - `android input tap "text=/^Despensa$/"`

### Action: Tap the button "Añadir ingrediente" ✅
- **Commands**:
  - `android input tap "clz=Button"`
- **Comment**: El texto del botón flotante no aparece en `android layout`, por lo que `text=Añadir ingrediente` no lo encuentra; se selecciona por clase (es el único `Button` de la pantalla).

### Action: Tap the "Ingrediente" field and choose "Nata" from the list ✅
- **Commands**:
  - `android input tap "clz=EditText" --match first`
  - `android input tap "text=/^Nata$/"`
- **Comment**: El desplegable lista los 15 ingredientes; tras elegir, el campo muestra "Nata".

### Action: Tap the "Cantidad" field and type 250 ✅
- **Commands**:
  - `android input tap "text=Cantidad"`
  - `android input text "250" "clz=EditText,focused=true"`
- **Screenshot**: [n2.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/n2.png)
- **Comment**: El campo muestra 250 con el sufijo "ml".

### Action: Tap the button "Guardar" ✅
- **Commands**:
  - `android input tap "text=Guardar"`

### Action: Verify that the pantry list shows the text "Nata · 250 ml" ✅
- **Screenshot**: [n3.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/n3.png)
- **Comment**: `Nata · 250 ml` aparece como décima línea; el resto de la despensa no cambia.

### Action: Tap the "Recetas" tab in the bottom navigation bar ✅
- **Commands**:
  - `android input tap "text=/^Recetas$/"`

### Action: Scroll down the list of recipes until the card "Mellorine para Nami y Robin" is fully visible ✅
- **Commands**:
  - `android input scroll "scrollable=true" --dir=down --amount=0.9`

### Action: Verify that the card "Mellorine para Nami y Robin" shows the label "Disponible" and no longer "Falta 1 ingrediente" ✅
- **Screenshot**: [n4.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/n4.png)
- **Comment**: La etiqueta pasa a "Disponible". El resto de tarjetas no cambia (Luffy "Falta 1 ingrediente", Ramen "Faltan 2", Guiso "Faltan 3").

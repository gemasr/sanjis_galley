# Journey: Caldo de pescado: faltantes de Ramen y Guiso se suman en una línea

Ejecutado sobre datos de ejemplo recién cargados (`am force-stop` + `android run`). Resultado global: **✅ todas las acciones pasan**.

## Results

### Action: Tap the card "Ramen del Baratie" ✅
- **Commands**:
  - `android input tap "text=Ramen del Baratie"`

### Action: Tap the button "Añadir faltantes a la compra" ✅
- **Commands**:
  - `android input tap "text=Añadir faltantes a la compra"`

### Action: Verify that the notice "Añadido a la lista de la compra" is visible ✅
- **Screenshot**: [c1.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/c1.png)
- **Comment**: Confirmado en el layout justo tras pulsar (el aviso desaparece solo a los pocos segundos).

### Action: Tap the back arrow with the description "Volver" ✅
- **Commands**:
  - `android input tap "desc=Volver"`

### Action: Scroll down the list of recipes until the card "Guiso del Thousand Sunny" is visible ✅
- **Commands**:
  - `android input scroll "scrollable=true" --dir=down --amount=0.9`

### Action: Tap the card "Guiso del Thousand Sunny" ✅
- **Commands**:
  - `android input tap "text=Guiso del Thousand Sunny"`
- **Comment**: El detalle muestra "Faltan 3 ingredientes": Cerdo (faltan 400 g), Zanahoria (faltan 3 ud) y Caldo de pescado (faltan 300 ml).

### Action: Tap the button "Añadir faltantes a la compra" ✅
- **Commands**:
  - `android input tap "text=Añadir faltantes a la compra"`

### Action: Verify that the notice "Añadido a la lista de la compra" is visible ✅
- **Screenshot**: [c2.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/c2.png)

### Action: Tap the "Compra" tab in the bottom navigation bar ✅
- **Commands**:
  - `android input tap "text=/^Compra$/"`
- **Comment**: `text=Compra` coincide también con el botón "Añadir faltantes a la compra"; se usa la expresión regular exacta.

### Action: Verify that the text "Caldo de pescado · 800 ml" is visible ✅
- **Screenshot**: [c3.png](/private/tmp/claude-501/-Users-gemasr-Code-SetSail-sanjis-galley-sanjis-galley/9f1fd626-bdea-416b-9a90-64a80f694858/scratchpad/c3.png)
- **Comment**: 500 ml (Ramen) + 300 ml (Guiso) = 800 ml.

### Action: Verify that the text "Caldo de pescado · 800 ml" appears in exactly one line of the list and there is no other line with "Caldo de pescado" ✅
- **Commands**:
  - `android layout --flat` (conteo de textos que contienen "Caldo de pescado": 1)
- **Comment**: La lista completa es `Fideos de trigo · 200 g`, `Caldo de pescado · 800 ml`, `Cerdo · 400 g`, `Zanahoria · 3 ud`. Sin duplicados.

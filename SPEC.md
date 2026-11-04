# Sanji's Galley: "¿Qué puedo cocinar hoy?"

Una app de cocina para el cocinero del Thousand Sunny. Muestra lo que hay en la despensa, qué recetas se pueden preparar con lo que hay y construye una lista de la compra con lo que falta. Los datos viven en memoria y se cargan desde los datos de ejemplo al arrancar la app.

> Para el agente: este documento es la fuente de verdad para los datos, las reglas y los textos. Implementa solo lo que se te pida en cada momento; no adelantes trabajo de otros pasos. Los textos visibles de la app están en español; el código (clases, variables, paquetes) sigue en inglés según AGENTS.md.

## Modelo de datos

| Entidad | Campos |
|---|---|
| `Ingredient` | `id`, `name`, `unit` (`g`, `ml` o `ud`) |
| `PantryItem` | `ingredient`, `quantity` (número > 0) |
| `Recipe` | `id`, `name`, `description` (una línea), `steps` (lista ordenada), `ingredients` (ingrediente + cantidad necesaria) |
| `ShoppingItem` | `ingredient`, `quantity` (a comprar), `purchased` (sí/no) |

## Reglas de negocio
- Ingrediente disponible para una receta: la cantidad en la despensa es mayor o igual que la cantidad necesaria (si no está en la despensa, es 0).
- Receta disponible: todos sus ingredientes están disponibles.
- Faltante de un ingrediente: cantidad necesaria menos cantidad en la despensa, solo si es positivo.
- Añadir faltantes a la compra: por cada ingrediente con faltante, añade un ShoppingItem con esa cantidad. Si el ingrediente ya está en la lista (sin comprar), suma la cantidad a esa línea en vez de crear una duplicada.
- La despensa no puede tener dos líneas del mismo ingrediente: añadir uno existente suma a su cantidad.

## Textos de la UI (exactos)

| Elemento | Texto |
|---|---|
| Destinos de navegación | `Despensa`, `Recetas`, `Compra` |
| Añadir a la despensa | `Añadir ingrediente`, `Guardar` |
| Etiquetas del formulario | `Ingrediente`, `Cantidad` |
| Etiqueta de disponible | `Disponible` |
| Etiqueta de faltantes | `Faltan N ingredientes` / `Falta 1 ingrediente` |
| Botón del detalle | `Añadir faltantes a la compra` |
| Aviso tras añadir | `Añadido a la lista de la compra` |
| Estados vacíos | `La despensa está vacía`, `No hay nada que comprar` |
| Secciones del detalle de receta | `Ingredientes`, `Pasos` |
| Accesibilidad | `Volver` (descripción de la flecha de volver) |
| Opcional | `Pasar comprados a la despensa` |

Formato de cantidades: `Nombre · cantidad unidad`, por ejemplo `Huevo · 6 ud` o `Arroz · 1000 g`. En el detalle de una receta, un ingrediente que falta se muestra como `Pollo · 500 g (faltan 500 g)`.

## Datos de ejemplo

### Ingredientes
| id | Nombre | Unidad |
|---|---|---|
| egg | Huevo | ud |
| rice | Arroz | g |
| nori | Alga nori | ud |
| salmon | Salmón | g |
| chicken | Pollo | g |
| pork | Cerdo | g |
| onion | Cebolla | ud |
| carrot | Zanahoria | ud |
| potato | Patata | ud |
| soy | Salsa de soja | ml |
| noodles | Fideos de trigo | g |
| stock | Caldo de pescado | ml |
| milk | Leche | ml |
| cream | Nata | ml |
| sugar | Azúcar | g |

### Despensa inicial
| Ingrediente | Cantidad |
|---|---|
| Huevo | 6 |
| Arroz | 1000 |
| Alga nori | 10 |
| Salmón | 200 |
| Cebolla | 3 |
| Patata | 4 |
| Salsa de soja | 200 |
| Leche | 1000 |
| Azúcar | 500 |

(No hay pollo, cerdo, zanahoria, fideos, caldo de pescado ni nata.)

### Recetas

**1. Onigiri de salmón de Sanji**: *Bolas de arroz rellenas de salmón, perfectas para el camino.*
- Ingredientes: Arroz 300 g, Salmón 150 g, Alga nori 3 ud
- Pasos: 1) Cuece el arroz y deja que se temple. 2) Dora el salmón en la sartén y desmenúzalo. 3) Forma las bolas de arroz con un poco de salmón en el centro. 4) Envuélvelas con el alga nori.

**2. Tortilla del Going Merry**: *Tortilla jugosa de patata y cebolla.*
- Ingredientes: Huevo 4 ud, Patata 2 ud, Cebolla 1 ud
- Pasos: 1) Pela la patata y la cebolla y córtalas en láminas finas. 2) Pocha la patata y la cebolla a fuego lento. 3) Bate los huevos y mézclalos con las patatas. 4) Cuaja la tortilla por los dos lados.

**3. Carne a lo grande de Luffy**: *Pollo glaseado en abundancia, como le gusta al capitán.*
- Ingredientes: Pollo 500 g, Salsa de soja 50 ml, Cebolla 1 ud
- Pasos: 1) Marina el pollo con la salsa de soja. 2) Dora el pollo con la cebolla picada. 3) Cocina a fuego medio hasta que quede glaseado.

**4. Ramen del Baratie**: *Ramen con caldo de pescado y huevo.*
- Ingredientes: Fideos de trigo 200 g, Caldo de pescado 500 ml, Huevo 2 ud, Cebolla 1 ud, Salsa de soja 20 ml
- Pasos: 1) Calienta el caldo con la salsa de soja. 2) Cuece los fideos aparte y escúrrelos. 3) Hierve los huevos 7 minutos y pélalos. 4) Monta el bol con los fideos, el caldo, el huevo y la cebolla picada.

**5. Guiso del Thousand Sunny**: *Guiso de cerdo, patata y zanahoria para toda la tripulación.*
- Ingredientes: Cerdo 400 g, Patata 2 ud, Zanahoria 3 ud, Cebolla 1 ud, Caldo de pescado 300 ml
- Pasos: 1) Dora el cerdo picado con la cebolla. 2) Añade la patata y la zanahoria en dados. 3) Cubre con el caldo de pescado. 4) Cocina a fuego lento hasta que todo esté tierno.

**6. Mellorine para Nami y Robin**: *Helado cremoso, servido con una pose de Sanji.*
- Ingredientes: Leche 500 ml, Nata 250 ml, Azúcar 100 g, Huevo 3 ud
- Pasos: 1) Calienta la leche con la nata sin que llegue a hervir. 2) Bate las yemas con el azúcar y mézclalas con la leche caliente. 3) Cocina removiendo hasta que espese y deja enfriar. 4) Congela, removiendo cada 30 minutos.

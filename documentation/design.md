# Diseño de la Base de Datos

## Esquema de la Base de Datos

La aplicación utiliza una base de datos Room para almacenar información sobre viajes y sus itinerarios.

### Tabla `trips`

| Columna     | Tipo de Datos | Descripción                               |
| ----------- | ------------- | ----------------------------------------- |
| id          | INTEGER       | Identificador único del viaje (clave primaria, autogenerado) |
| destination | TEXT          | Destino del viaje                         |
| startDate   | TEXT          | Fecha de inicio del viaje (formato: dd-MM-yyyy) |
| endDate     | TEXT          | Fecha de finalización del viaje (formato: dd-MM-yyyy) |

### Tabla `itineraryItems`

| Columna     | Tipo de Datos | Descripción                               |
| ----------- | ------------- | ----------------------------------------- |
| id          | INTEGER       | Identificador único del elemento del itinerario (clave primaria, autogenerado) |
| title       | TEXT          | Título del elemento del itinerario        |
| tripId      | INTEGER       | Identificador del viaje al que pertenece el elemento (clave externa, referencia a `trips.id`) |
| description | TEXT          | Descripción del elemento del itinerario   |
| date        | TEXT          | Fecha del elemento del itinerario (formato: dd-MM-yyyy) |

## Relaciones

* La tabla `itineraryItems` tiene una relación de clave externa con la tabla `trips`. Esto significa que cada elemento del itinerario está asociado a un viaje específico.

## Uso de la Base de Datos

### Inserción de un Viaje

Se utiliza la función `addTrip` del `TripDao` para insertar un nuevo viaje en la tabla `trips`.

### Consulta de Viajes

Se utiliza la función `getTrips` para obtener todos los viajes.

### Eliminación de un Viaje

Se utiliza la función `deleteTrip`  para eliminar un viaje por su ID.

### Actualización de un Viaje

Se utiliza la función `updateTrip`   para actualizar un viaje existente.

### Inserción de un Elemento de Itinerario

Se utiliza la función  `addItineraryItem` para insertar un nuevo elemento de itinerario.

### Consulta de Elementos de Itinerario por Viaje

Se utiliza la función `getItineraryItemsByTripId` para obtener los elementos de itinerario asociados a un viaje específico.

### Eliminación de un Elemento de Itinerario

Se utiliza la función  `deleteItineraryItem`  para eliminar un elemento de itinerario por su ID.

### Actualización de un Elemento de Itinerario

Se utiliza la función `updateItineraryItem`  para actualizar un elemento de itinerario existente
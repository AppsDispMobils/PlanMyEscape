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

### Tabla `users`

| Columna     | Tipo de Datos | Descripción                               |
| ----------- | ------------- | ----------------------------------------- |
| id          | TEXT       | Identificador único del usuario (clave primaria) |
| email       | TEXT          | Correo electrónico del usuario.       |
| username | TEXT          | Nombre de usuario único.   |
| birthdate      | TEXT       | Fecha de nacimiento (formato: dd-MM-yyyy).. |
| address | TEXT          | Dirección del usuario. |
| country        | TEXT          | País del usuario. |
| phoneNumber | INTEGER          | Número de teléfono del usuario.   |
| receiveEmails        | BOOLEAN          | 	Indica si el usuario acepta recibir correos electrónicos. |


## Relaciones

* La tabla `itineraryItems` tiene una relación de clave externa con la tabla `trips`. Esto significa que cada elemento del itinerario está asociado a un viaje específico.
* la tabla `users` no tiene inguna relación directa. El campo id puede ser usado como clave foránea en otras tablas, como la de trips, para asociar los viajes con un usuario específico.
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


### Inserción de un usuario: 
Se utiliza la función insertUser() en el UserDao para insertar un nuevo usuario.

### Consulta de un usuario por id: 
Se utiliza la función getUserById(id: String) en el UserDao para obtener un usuario por su id.

### Actualización de un usuario: 
Se utiliza la función updateUser(user: UserEntity) en el UserDao para actualizar la información de un usuario existente.

### Eliminación de un usuario: 
Se utiliza la función deleteUser(user: UserEntity) en el UserDao para eliminar un usuario.

### Validación de Datos en la Base de Datos**

1. **Validación de destino duplicado para un viaje**:
    - Antes de agregar un nuevo viaje, se valida que el **destino** no esté registrado previamente para el mismo usuario. Si ya existe un viaje con el mismo destino, el sistema lanza un error.
    

2. **Validación de nombre de viaje duplicado**:
    - Se asegura de que el nombre de un viaje no sea duplicado para un mismo usuario. Si ya existe un viaje con el mismo destino para el mismo usuario, se lanza un error.
    

3. **Validación de fechas**:
    - Se valida que la **fecha de inicio** no sea posterior a la **fecha de finalización** de un viaje. Si esto ocurre, se lanza un error para que el usuario ingrese las fechas correctamente.


4. **Validación de horas en itinerarios**:
    - Se asegura de que la **hora de inicio** del itinerario sea anterior a la **hora de finalización**. Si la validación falla, el sistema lanzará un error.


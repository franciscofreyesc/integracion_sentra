# integracion_sentra
Repo prueba Sentra

Dentro de la raíz de este repositorio se encuentran los siguientes elementos:

data.sql -> Base de datos embebida (H2)
PruebaSentra.drawio.png -> Diagrama de secuencia de la api desarrollada
registrousuario-0.0.1-SNAPSHOT.jar -> empaquetado de la api para probar
PruebaSentra.postman_collection.json -> Archivo de coleccion para probar desde aplicativo Postman

## Para probar el desarrollo lo primero que se debe hacer es descargar el repo y luego ubicar la ruta:

./integracion_sentra/

## Una vez localizada la ruta, se debe ejecutar por CMD

java -jar registrousuario-0.0.1-SNAPSHOT.jar

## Postman

Abrir la aplicación Postman e importar el archivo PruebaSentra.postman_collection.json

Dentro, se encuentran las pruebas con los endpoints locales, donde primero se debe ejecutar el login para obtener el token de acceso

`login`

POST localhost:8080/login  
email : francisco@mail.cl  
pass: 12345  

Una vez obtenido el token podemos realizar la inserción de datos mediante

`registro`  

POST localhost:8080/registroUsuario  
con la siguiente estructura en el payload  

```
{
    "name": "Superman Segura",
    "email": "superman@mail.cl",
    "password": "Clavee55",
    "phones": [
        {
            "number": "654832928",
            "citycode": "2",
            "countrycode": "13"
        }
    ]
}

```  
Para consultar el registro agregado  

`consulta`  
GET localhost:8080/obtenerUsuario/{id Usuario} <- el id de usuario se obtiene en la respuesta del registro usado anteriormente  


`consulta todos`  
También se pueden consultar todos los registros con el siguiente endpoint   
GET localhost:8080/obtenerUsuario/  

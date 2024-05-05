# TribunalesTFG

Proyecto que usa spring y vaadin para facilitar la generación de tribunales

## Lanzar la aplicación

El proyecto es un proyecto Maven estándar. Para ejecutarlo desde la línea de comandos,
escribe mvnw (Windows), o ./mvnw (Mac & Linux), luego abre http://localhost:8080 en tu navegador.

You can also import the project to your IDE of choice as you would with any
Maven project. Read more on [how to import Vaadin projects to different IDEs](https://vaadin.com/docs/latest/guide/step-by-step/importing) (Eclipse, IntelliJ IDEA, NetBeans, and VS Code).

## Implementar en producción

Para crear una compilación de producción, llama a mvnw clean package -Pproduction (Windows),
o ./mvnw clean package -Pproduction (Mac & Linux).
Esto creará un archivo JAR con todas las dependencias y recursos front-end,
listos para ser implementado. El archivo se encontrará en la carpeta target después de completar la compilación.

Se puede lanzar el jar usando
`java -jar target/flowcrmtutorial-1.0-SNAPSHOT.jar`

## Estructura

- `MainLayout.java` in `src/main/java` Contiene la navegación [App Layout](https://vaadin.com/docs/components/app-layout).
- El paquete `views` en src/main/java contiene las vistas Java del lado del servidor de la aplicación.
- La carpeta `views` en frontend/ contiene las vistas JavaScript del lado del cliente de la aplicación.
- La carpeta `themes` en frontend/ contiene los estilos CSS personalizados.

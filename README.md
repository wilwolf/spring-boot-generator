<b># spring-boot-generator</b>
Generate Dao, Rest-Services, Repositoryan ListItems based on a model
Generador de codigo Spring Boot a partir del Modelo
EsTructura del Proyecto::::<br/>
  com<br/>
  -proyect<br/>
  --spring<br/>
  ---dao<br/>
  ---listitem<br/>
  ---model (carpeta donde se deben copiar los modelos)<br/>
  ---repositories<br/>
  ---rest<br/>
  -resources<br/>
  --dao.tmpl<br/>
  --listitem.tmpl<br/>
  --repository.tmpl<br/>
  --rest.service.tmpl<br/>
  
  Se ejecuta el archivo App.java este genera el dao, listitem, repositories y rest en base a los templates que se
  encuentra en la carpeta resources
  
  Saludos:::wilwolf

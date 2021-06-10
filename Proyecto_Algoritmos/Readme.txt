Administrador:
usuario: admin , contraseña: 123

---------------------------------------------------
Recomendacion:
Las distintas funciones estan relacionadas, refiriendose a que algunas requieren de archivos previos para poder funcionar,
esta relacion esta dada de la siguiente forma:

Para registrar un estudiante se requiere almenos una carrera.
Para registrar un curso se requiere almenos una carrera.
Para registrar un horario se requiere de almenos un curso.
para realizar una matricula se requiere almenos un curso con horario y un estudiante que estudie la carrera del curso
Para retirar un curso se riquiere haber matriculado almenos un curso.

Para modificar cualquier aspecto, se require almenos haber un registrado del mismo tipo.

Para borrar un estudiante se requiere que no tenga una matricula.
Para borrar un curso se requiere que no tenga un horario.
Para borrar un horario se requiere que el curso del mismo no tenga un matricula.
Para retirar una matricula se requiere almenos haber realizado una matricula.
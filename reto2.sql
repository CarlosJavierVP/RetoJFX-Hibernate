create database reto2;
use reto2;

CREATE TABLE Usuario (
id int auto_increment primary key,
nombre_usuario varchar(255),
password varchar(255),
is_admin tinyint(1) null
);

CREATE TABLE Pelicula(
id int auto_increment primary key,
titulo varchar(255),
genero varchar(255),
año int,
descripcion varchar(255),
director varchar(255),
image_url varchar(50)
);

CREATE TABLE Copia(
id int auto_increment primary key,
estado enum ('bueno', 'dañado'),
soporte enum ('DVD', 'Blu-ray')
);

ALTER TABLE Copia ADD COLUMN id_pelicula int, 
ADD CONSTRAINT `fk_id_pelicula` 
FOREIGN KEY (id_pelicula) 
REFERENCES Pelicula(id);

ALTER TABLE Copia ADD COLUMN id_usuario int,
ADD constraint `fp_id_usuario`
foreign key (id_usuario)
REFERENCES Usuario(id);


INSERT INTO Usuario (nombre_usuario, password) VALUES
('juanperez','password123'),
('mariagonzalez','mypassword');

INSERT INTO Pelicula (titulo, genero, año, descripcion, director) VALUES
('Origen', 'Sci-Fi',2010,'Un ladrón que roba secretos 
corporativos a través del uso de la tecnología de compartir 
sueños recibe una oportunidad para borrar su historial criminal.','Christopher Nolan'),
('Matrix', 'Acción', 1999,'Un hacker de ordenadores 
aprende de rebeldes misteriosos sobre la verdadera naturaleza 
de su realidad y su papel en la guerra contra sus controladores.','Lana Wachowski'),
('Interstellar', 'Sci-Fi', 2014,'Un equipo de exploradores viaja 
a través de un agujero de gusano en el espacio en un intento de 
asegurar la supervivencia de la humanidad.', 'Christopher Nolan'),
('Star Wars: Episodio IV - Una nueva esperanza', 'Sci-Fi', 1977, 'Un joven granjero se une a una 
rebelión contra un imperio galáctico tiránico.', 'George Lucas'),
('The Godfather', 'Crimen', 1972, 'La historia de la familia Corleone, 
una poderosa familia mafiosa en Nueva York.', 'Francis Ford Coppola'),
('Pulp Fiction', 'Crimen', 1994, 'Historias entrelazadas de crimen 
y redención en Los Ángeles.', 'Quentin Tarantino'),
('Titanic', 'Romance', 1997, 'Una joven pareja de diferentes clases 
sociales se enamora durante el fatídico viaje del Titanic.', 'James Cameron'),
('The Dark Knight', 'Acción', 2008, 'Batman lucha contra el Joker, un caótico criminal que amenaza Gotham City.', 'Christopher Nolan'),
('Forrest Gump', 'Drama', 1994, 'La vida extraordinaria de un hombre 
con un bajo coeficiente intelectual que logra cosas increíbles.', 'Robert Zemeckis'),
('Gladiator', 'Acción', 2000, 'Un general romano se convierte en gladiador 
para vengar la muerte de su familia y emperador.', 'Ridley Scott'),
('Schindler\'s List', 'Drama', 1993, 'La historia real de Oskar Schindler, 
un empresario que salvó la vida de más de mil judíos durante el Holocausto.', 'Steven Spielberg'),
('Inception', 'Ciencia Ficción', 2010, 'Un ladrón que roba secretos a través de la 
tecnología de sueños compartidos recibe una misión casi imposible.', 'Christopher Nolan'),
('Fight Club', 'Drama', 1999, 'Un hombre desilusionado con su vida 
forma un club de lucha clandestino.', 'David Fincher'),
('The Lord of the Rings: The Fellowship of the Ring', 'Fantasía', 2001, 'Un joven hobbit se embarca en una peligrosa misión 
para destruir un anillo que puede controlar el destino del mundo.', 'Peter Jackson'),
('The Shawshank Redemption', 'Drama', 1994, 'Un banquero es condenado a cadena perpetua y entabla una amistad con otro 
prisionero mientras planea escapar.', 'Frank Darabont'),
('Parasite', 'Drama', 2019, 'Una familia pobre se infiltra en la vida de una familia rica, 
lo que desencadena una serie de eventos imprevistos.', 'Bong Joon-ho'),
('La Vida de Brian', 'Humor', 1979, 'Brian nace el mismo día que Jesús de Nazaret, 
y se dan una serie de tronchantes equívocos', 'Terry Jones' );


INSERT INTO Copia (id_pelicula, id_usuario, estado, soporte) VALUES
(1,1,'bueno','DVD'),
(1,1,'bueno','Blu-ray'),
(2,2,'dañado','DVD'),
(3,1,'bueno','Blu-ray'),
(4,2,'bueno','DVD'),
(4,1,'bueno','Blu-ray');


-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-05-2024 a las 19:48:49
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tribunales`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `dni` varchar(15) NOT NULL,
  `fecha_ingreso` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`dni`, `fecha_ingreso`) VALUES
('00000000E', '2018-08-28'),
('11111111A', '2019-05-15'),
('11111111B', '2023-02-24'),
('11111111C', '2018-02-14'),
('11111111D', '2015-12-17'),
('11111111E', '2016-01-07'),
('11111111F', '2016-01-08'),
('11111111G', '2014-10-09'),
('11111111H', '2014-10-17'),
('11111111I', '2016-01-07'),
('11111111J', '2016-01-21'),
('11111111K', '2022-02-07'),
('11111111L', '2014-10-10'),
('11111111M', '2014-10-10'),
('11111111N', '2014-10-10'),
('11111111Ñ', '2016-01-29'),
('11111111O', '2014-10-10'),
('11111111P', '2014-10-10'),
('11111111Q', '2017-03-12'),
('11111111R', '2018-08-17'),
('11111111S', '2018-08-23'),
('11111111T', '2019-11-05'),
('11111111U', '2019-10-18'),
('11111111V', '2017-04-29'),
('11111111W', '2018-08-25'),
('11111111X', '2014-10-05'),
('11111111Y', '2017-02-13'),
('11111111Z', '2018-06-23'),
('11111112A', '2020-05-23'),
('30245316V', '2023-01-18');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `convocatoria`
--

CREATE TABLE `convocatoria` (
  `curso` int(11) NOT NULL,
  `numero` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `convocatoria`
--

INSERT INTO `convocatoria` (`curso`, `numero`) VALUES
(2023, 1),
(2023, 2),
(2024, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `defensa`
--

CREATE TABLE `defensa` (
  `id_defensa` int(11) NOT NULL,
  `calidad` double NOT NULL,
  `adquisicion` double NOT NULL,
  `presentacion` double NOT NULL,
  `defensa` double NOT NULL,
  `id_tribunal` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `defensa`
--

INSERT INTO `defensa` (`id_defensa`, `calidad`, `adquisicion`, `presentacion`, `defensa`, `id_tribunal`) VALUES
(5, 5.86, 7.23, 8.51, 8.22, 21),
(6, 3, 4, 5, 6, 112),
(7, 7, 6, 8, 7, 113);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `docente`
--

CREATE TABLE `docente` (
  `fecha_ingreso` date NOT NULL,
  `dni` varchar(15) NOT NULL,
  `categoria` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `docente`
--

INSERT INTO `docente` (`fecha_ingreso`, `dni`, `categoria`) VALUES
('2014-05-13', '00000000A', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2022-01-04', '00000000B', 'CATEDRATICO/A DE UNIVERSIDAD'),
('2007-02-16', '00000000C', 'CATEDRATICO/A DE UNIVERSIDAD'),
('2016-02-22', '00000000D', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2021-01-14', '00000000E', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2018-02-01', '00000000F', 'PROFESOR/A TITULAR ESCUELA UNIVERSITARIA'),
('2018-01-22', '00000000G', 'PROFESOR/A TITULAR ESCUELA UNIVERSITARIA'),
('2020-06-01', '00000000H', 'PROFESOR/A VISITANTE LOU'),
('2023-02-17', '00000000I', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2020-11-26', '00000000J', 'CATEDRATICO/A DE UNIVERSIDAD'),
('2018-02-18', '00000000K', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2015-01-05', '00000000L', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2020-03-07', '00000000M', 'PROFESOR/A CONTRATADO DOCTOR'),
('2021-10-08', '00000000N', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2012-11-01', '00000000Ñ', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2013-01-02', '00000000O', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2010-03-10', '00000000P', 'CATEDRATICO/A DE UNIVERSIDAD'),
('2020-01-10', '00000000Q', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2017-12-06', '00000000R', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2023-03-31', '00000000S', 'PROFESOR/A SUSTITUTO INTERINO'),
('2020-07-17', '00000000T', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2017-10-19', '00000000U', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2009-12-24', '00000000V', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2019-08-01', '00000000W', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2021-11-04', '00000000X', 'PROFESOR/A VISITANTE LOU'),
('2014-11-26', '00000000Y', 'PROFESOR/A TITULAR DE UNIVERSIDAD'),
('2019-04-16', '00000000Z', 'PROFESOR/A TITULAR DE UNIVERSIDAD');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `dni` varchar(15) NOT NULL,
  `nombre` varchar(400) NOT NULL,
  `apellidos` varchar(400) NOT NULL,
  `contrasena` varchar(100) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `usuario` varchar(50) NOT NULL,
  `correo_electronico` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`dni`, `nombre`, `apellidos`, `contrasena`, `telefono`, `usuario`, `correo_electronico`, `fecha_nacimiento`) VALUES
('00000000A', 'Domingo Savio', 'Rodríguez Baena', '', 666666666, 'drodbae', 'dsrodbae@upo.es', '2005-02-15'),
('00000000B', 'Roberto', 'Ruíz Sánchez', '', 666666666, 'rruisan', 'rruisan1@upo.es', '2013-04-11'),
('00000000C', 'Norberto', 'Díaz Díaz', '', NULL, 'ndiadia', 'ndiaz@upo.es', '2009-03-12'),
('00000000D', 'Raúl', 'Giráldez Rojo', '', NULL, 'rgirroj', 'rgirroj@upo.es', '2015-02-11'),
('00000000E', 'Miguel', 'García Torres', '', NULL, 'mgartor', 'mgarciat@upo.es', NULL),
('00000000F', 'Francisco Antonio', 'Gomez Vela', '', NULL, 'fgomvel', 'fgomez@upo.es', NULL),
('00000000G', 'Federico', 'Divina', '', NULL, 'fdivdiv', 'fdiv@upo.es', NULL),
('00000000H', 'Francisco Javier', 'Gil Cumbreras', '', NULL, 'fgilcum', 'fjgilcum@upo.es', NULL),
('00000000I', 'Feliciano', 'Soto Borrero', '', NULL, 'fsotbor', 'fcsotbor@upo.es', NULL),
('00000000J', 'Luis', 'Merino Cabañas', '', NULL, 'lmercab', 'lmercab@upo.es', NULL),
('00000000K', 'Fernando', 'Caballero Benítez', '', NULL, 'fcabben', '', NULL),
('00000000L', 'Ángel Francisco', 'Tenorio Villalón', '', NULL, 'atenvil', '', NULL),
('00000000M', 'Francisco', 'Martínez Álvarez', '', NULL, 'fmaralv', '', NULL),
('00000000N', 'Jose Francisco', 'Torres Maldonado', '', NULL, 'jtormal', '', NULL),
('00000000Ñ', 'Sergio', 'Bermudo Navarrete', '', NULL, 'sbernav', '', NULL),
('00000000O', 'Beatriz', 'Gavira Aguilar', '', NULL, 'bgavagu', '', NULL),
('00000000P', 'Jesús', 'Aguilar Ruíz', '', NULL, 'jagurui', '', NULL),
('00000000Q', 'Carlos Alberto', 'Rodríguez Parrales', '', NULL, 'crodpar', '', NULL),
('00000000R', 'David', 'De Vega Rodríguez', '', NULL, 'ddevrod', '', NULL),
('00000000S', 'María Remedios', 'Sillero Denamiel', '', NULL, 'msilden', '', NULL),
('00000000T', 'José Antonio', 'Lagares Rodríguez', '', NULL, 'jlagrod', '', NULL),
('00000000U', 'Manuel', 'Béjar Domínguez', '', NULL, 'mbejdom', '', NULL),
('00000000V', 'José Antonio', 'Cobano Suárez', '', NULL, 'jcobsua', '', NULL),
('00000000W', 'Jesús Antonio', 'Carrillo Castrillo', '', NULL, 'jcarcas', '', NULL),
('00000000X', 'José Eduardo', 'Navarro Molina', '', NULL, 'jnavmol', '', NULL),
('00000000Y', 'Germán', 'Pérez Morales', '', NULL, 'gpermor', '', NULL),
('00000000Z', 'Carlos', 'Barranco González', '', NULL, 'cbargon', '', '2019-04-12'),
('11111111A', 'Carlos', 'Vega Molina', '', NULL, 'cvegmol', '', NULL),
('11111111B', 'Victor', 'Ballesteros Leon', '', NULL, 'vballeo', '', NULL),
('11111111C', 'Alberto', 'Balsera López', '', NULL, 'aballop', '', NULL),
('11111111D', 'Jaime', 'Baquerizo Delgado', '', NULL, 'jbaqdel', '', NULL),
('11111111E', 'Jose Antonio', 'Barrera Carmona', '', NULL, 'jbarcar', '', NULL),
('11111111F', 'Carlos', 'Bedmar Moreno', '', NULL, 'cbedmor', '', NULL),
('11111111G', 'Joaquin', 'Bono Pineda', '', NULL, 'jbonpin', '', NULL),
('11111111H', 'David', 'Caballos Reina', '', NULL, 'dcabrei', '', NULL),
('11111111I', 'Jesus', 'Cívico Lobato', '', NULL, 'jcivlob', '', NULL),
('11111111J', 'Alejandro', 'Gamez Collantes De Teran', '', NULL, 'agamcol', '', NULL),
('11111111K', 'Andrea', 'García Castillo', '', NULL, 'agarcas', '', NULL),
('11111111L', 'Alejandro', 'García Conde', '', NULL, 'agarcon', '', NULL),
('11111111M', 'Guillermo', 'Garrido Contreras', '', NULL, 'ggarcon', '', NULL),
('11111111N', 'Ignacio', 'Lopez Muñoyerro', '', NULL, 'ilopmun', '', NULL),
('11111111Ñ', 'Maria Del Carmen', 'Lozano Espejo', '', NULL, 'mlozesp', '', NULL),
('11111111O', 'Juan', 'Luengo Rodriguez', '', NULL, 'jluerod', '', NULL),
('11111111P', 'Miguel Angel', 'Martin Millan', '', NULL, 'mmarmil', '', NULL),
('11111111Q', 'Antonio Manuel', 'Mérida Borrero', '', NULL, 'amerbor', '', NULL),
('11111111R', 'Manuel', 'Muñoz Marin', '', NULL, 'mmunmar', '', NULL),
('11111111S', 'Luis Maria', 'Ortiz Aguera', '', NULL, 'lortagu', '', NULL),
('11111111T', 'Miguel', 'Pavon Rodriguez', '', NULL, 'mpavrod', '', NULL),
('11111111U', 'Santiago', 'Perez Roldan', '', NULL, 'sperrol', '', NULL),
('11111111V', 'Sergio', 'Ramos Uncala', '', NULL, 'sramunc', '', NULL),
('11111111W', 'Victor Jesus', 'Reina Lopez', '', NULL, 'vreilop', '', NULL),
('11111111X', 'Jesus Maria', 'Sena Franconetti', '', NULL, 'jsenfra', '', NULL),
('11111111Y', 'Jose Maria', 'Torres Cansino', '', NULL, 'jtorcan', '', NULL),
('11111111Z', 'Alejandro', 'Vazquez Rodriguez', '', NULL, 'avazrod', '', NULL),
('11111112A', 'Sanz Guijarro, Miguel Angel', 'Sanz Guijarro', '', NULL, 'ssangui', '', '2019-02-15'),
('30245316V', 'Adrian', 'Escoz Fuentesal', '$2a$10$X3hRlZ5ibi.OsvUu/lAqy.Z69VavUjgD4buMZ.KeYqLzBivHdBMYi', 666242424, 'aescfue', '', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `codigo` varchar(50) NOT NULL,
  `usuario` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`codigo`, `usuario`) VALUES
('ADMIN', 'aescfue');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tfg`
--

CREATE TABLE `tfg` (
  `codigo` varchar(50) NOT NULL,
  `nombre` varchar(4000) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `curso_conv` int(6) NOT NULL,
  `numero_conv` int(2) NOT NULL,
  `dni_docente1` varchar(15) NOT NULL,
  `dni_docente2` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tfg`
--

INSERT INTO `tfg` (`codigo`, `nombre`, `dni`, `curso_conv`, `numero_conv`, `dni_docente1`, `dni_docente2`) VALUES
('20-21-C16', 'TÉCNICAS ESTADÍSTICA Y BIG DATA', '11111111Ñ', 2023, 1, '00000000S', NULL),
('21-22-C14', 'HERRAMIENTA DIGITAL DE EVALUACIÓN PARA LA F.P. DUAL', '11111111Y', 2023, 1, '00000000X', NULL),
('22-23-C12', 'HERRAMIENTAS DIDÁCTICAS PARA CONFECCIONAR ACTIVIDADES EDUCATIVAS', '11111111M', 2023, 1, '00000000Q', '00000000R'),
('22-23-C13', 'TRATAMIENTO ALGORÍTMICO DE PROBLEMAS DE COLABORACIÓN EN GRAFOS', '11111111E', 2023, 1, '00000000L', NULL),
('22-23-C2', 'SISTEMAS PARA LA PREVISIÓN DE DEMANDA APLICADO A LA DOCENCIA', '11111111X', 2023, 1, '00000000Z', NULL),
('22-23-C9', 'SISTEMAS INFORMÁTICO PARA LA GESTIÓN DE RECURSOS, HORARIOS DE USO Y TURNOS DE ESPERA', '11111111Q', 2023, 2, '00000000T', NULL),
('23-24-C1', 'VISUALIZACIÓN GRÁFICA USANDO SOFTWARE COMPUTACIONAL Y APLICACIONES', '11111111K', 2023, 1, '00000000O', NULL),
('23-24-C10', 'HERRAMIENTAS PARA EL DESARROLLO Y EVALUACIÓN DE MÉTODOS DE NAVEGACIÓN DE ROBOTS MÓVILES EN ENTORNOS CON PERSONAS', '11111111B', 2023, 1, '00000000J', '00000000K'),
('23-24-C11', 'LOCALIZACIÓN DE ROBOTS BASADA SENSOR RADAR', '11111111Z', 2023, 1, '00000000J', '00000000K'),
('23-24-C12', 'IDENTIFICACIÓN DE ATRIBUTOS INFORMATIVOS SEGÚN SU DISTRIBUCIÓN', '11111111G', 2023, 1, '00000000B', NULL),
('23-24-C13', 'GENERACIÓN DIFERENCIAL DE REDES DE GENES CON TÉCNICAS ENSEMBLE', '11111111V', 2023, 1, '00000000F', NULL),
('23-24-C14', 'SISTEMA DE INFORMACIÓN DE REPRESENTACIÓN Y ALMACENAMIENTO MASIVO DE GRAFOS DE ALTO RENDIMIENTO', '11111112A', 2023, 1, '00000000F', NULL),
('23-24-C15', 'DESARROLLO DE UNA APP PARA LA DETECCIÓN DE TALENTO EN EL ÁMBITO DEL FÚTBOL', '11111111J', 2023, 1, '00000000C', NULL),
('23-24-C16', 'SISTEMA PREDICTIVO DE ALERTAS BASADO EN REDES SOCIALES', '11111111W', 2023, 1, '00000000W', NULL),
('23-24-C17', 'SISTEMA PARA LA ELABORACIÓN AUTOMÁTICA DE TRIBUNALES DE TFG PARA UN GRADO UNIVERSITARIO', '30245316V', 2023, 1, '00000000A', NULL),
('23-24-C19', 'DESARROLLO DE UNA APP PARA LA GESTIÓN DE COMPETICIONES DEPORTIVAS', '11111111R', 2023, 1, '00000000C', NULL),
('23-24-C2', 'SOLUCIÓN WEB PARA LA GESTIÓN Y ORGANIZACIÓN DE FUNCIONES DEPARTAMENTALES', '11111111I', 2023, 1, '00000000Ñ', NULL),
('23-24-C20', 'SISTEMA DE GESTIÓN DE ADQUISICIÓN Y RENOVACIÓN DE EQUIPAMIENTO INFORMÁTICO DE LA UPO', '11111111D', 2023, 1, '00000000D', NULL),
('23-24-C25', 'SISTEMA DE INFORMACIÓN PARA GESTIÓN DE PROYECTOS', '11111111N', 2023, 1, '00000000H', NULL),
('23-24-C27', 'DESARROLLO DE SISTEMA DE INTERACIÓN HUMANO-ROBOT CON EL ROBOT TIAGO', '11111111P', 2023, 1, '00000000J', NULL),
('23-24-C3', 'ANÁLISIS ESTRUCTURAL DE TÉCNICAS DE PREPROCESADO EN DATA ANALYTICS', '11111111S', 2023, 1, '00000000P', NULL),
('23-24-C4', 'ANÁLISIS DE RENDIMIENTO PROBABILÍSTICO DE CLASIFICACIÓN DE DATA ANALYTICS', '11111111O', 2023, 1, '00000000P', NULL),
('23-24-C5', 'IDENTIFICACIÓN DE OUTLIERS USANDO TÉCNICAS DE ESTIMACIÓN DE DENSIDAD', '11111111L', 2023, 1, '00000000P', NULL),
('23-24-C6', 'APLICACIÓN MULTIPLATAFORMA PARA LA CONSULTA DE DATOS DEL INSTITUTO NACIONAL DE ESTADÍSTICA', '11111111A', 2023, 1, '00000000Y', NULL),
('23-24-C7', 'INTEGRACIÓN DEL SISTEMA GRAVITATORIO DE N CUERPOS MEDIANTE REDES NEURONALES ARTIFICIALES', '11111111H', 2023, 1, '00000000I', NULL),
('23-24-C8', 'DESARROLLO DE ALGORITMOS PARA LA EXTRACCIÓN DE INFORMACIÓN SEMÁNTICA A PARTIR DE MODELOS DE EDIFICIOS', '11111111U', 2023, 1, '00000000V', NULL),
('23-24-C9', 'DESARROLLO DE ALGORITMOS ROBÓTICOS DE PLANIFICACIÓN DE MOVIMIENTO PARA DRONES', '11111111T', 2023, 1, '00000000U', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tribunal`
--

CREATE TABLE `tribunal` (
  `id_tribunal` int(15) NOT NULL,
  `fecha` date DEFAULT NULL,
  `codigoTFG` varchar(50) NOT NULL,
  `dni_1` varchar(15) NOT NULL,
  `dni_2` varchar(15) NOT NULL,
  `dni_3` varchar(15) NOT NULL,
  `curso_conv` int(11) NOT NULL,
  `numero_conv` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `tribunal`
--

INSERT INTO `tribunal` (`id_tribunal`, `fecha`, `codigoTFG`, `dni_1`, `dni_2`, `dni_3`, `curso_conv`, `numero_conv`) VALUES
(21, '2024-05-08', '23-24-C17', '00000000J', '00000000Y', '00000000I', 2023, 1),
(112, '2024-05-20', '22-23-C9', '00000000C', '00000000E', '00000000H', 2023, 1),
(113, '2024-05-13', '22-23-C9', '00000000C', '00000000D', '00000000G', 2023, 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `convocatoria`
--
ALTER TABLE `convocatoria`
  ADD PRIMARY KEY (`curso`,`numero`);

--
-- Indices de la tabla `defensa`
--
ALTER TABLE `defensa`
  ADD PRIMARY KEY (`id_defensa`),
  ADD KEY `defensa_tribunal_fk` (`id_tribunal`);

--
-- Indices de la tabla `docente`
--
ALTER TABLE `docente`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`dni`),
  ADD UNIQUE KEY `usuario_unique` (`usuario`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`codigo`,`usuario`),
  ADD KEY `usuarioFK` (`usuario`);

--
-- Indices de la tabla `tfg`
--
ALTER TABLE `tfg`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `tfg_dni_FK` (`dni`),
  ADD KEY `tfg_convocatoria_FK` (`curso_conv`,`numero_conv`),
  ADD KEY `tfg_dni_docente2_FK` (`dni_docente2`),
  ADD KEY `tfg_dni_docente1_FK` (`dni_docente1`);

--
-- Indices de la tabla `tribunal`
--
ALTER TABLE `tribunal`
  ADD PRIMARY KEY (`id_tribunal`),
  ADD KEY `trib_cod_tfg_FK` (`codigoTFG`) USING BTREE,
  ADD KEY `trib_dni_1_FK` (`dni_1`),
  ADD KEY `trib_dni_2_FK` (`dni_2`),
  ADD KEY `trib_dni_3_FK` (`dni_3`),
  ADD KEY `trib_conv_FK` (`curso_conv`,`numero_conv`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `defensa`
--
ALTER TABLE `defensa`
  MODIFY `id_defensa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `tribunal`
--
ALTER TABLE `tribunal`
  MODIFY `id_tribunal` int(15) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=164;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `alu_dni_fk` FOREIGN KEY (`dni`) REFERENCES `persona` (`dni`);

--
-- Filtros para la tabla `defensa`
--
ALTER TABLE `defensa`
  ADD CONSTRAINT `defensa_tribunal_fk` FOREIGN KEY (`id_tribunal`) REFERENCES `tribunal` (`id_tribunal`);

--
-- Filtros para la tabla `docente`
--
ALTER TABLE `docente`
  ADD CONSTRAINT `dni_fk` FOREIGN KEY (`dni`) REFERENCES `persona` (`dni`);

--
-- Filtros para la tabla `rol`
--
ALTER TABLE `rol`
  ADD CONSTRAINT `usuarioFK` FOREIGN KEY (`usuario`) REFERENCES `persona` (`usuario`);

--
-- Filtros para la tabla `tfg`
--
ALTER TABLE `tfg`
  ADD CONSTRAINT `tfg_convocatoria_FK` FOREIGN KEY (`curso_conv`,`numero_conv`) REFERENCES `convocatoria` (`curso`, `numero`),
  ADD CONSTRAINT `tfg_dni_FK` FOREIGN KEY (`dni`) REFERENCES `alumno` (`dni`),
  ADD CONSTRAINT `tfg_dni_docente1_FK` FOREIGN KEY (`dni_docente1`) REFERENCES `docente` (`dni`),
  ADD CONSTRAINT `tfg_dni_docente2_FK` FOREIGN KEY (`dni_docente2`) REFERENCES `docente` (`dni`);

--
-- Filtros para la tabla `tribunal`
--
ALTER TABLE `tribunal`
  ADD CONSTRAINT `trib_cod_tfg` FOREIGN KEY (`codigoTFG`) REFERENCES `tfg` (`codigo`),
  ADD CONSTRAINT `trib_conv_FK` FOREIGN KEY (`curso_conv`,`numero_conv`) REFERENCES `convocatoria` (`curso`, `numero`),
  ADD CONSTRAINT `trib_dni_1_FK` FOREIGN KEY (`dni_1`) REFERENCES `docente` (`dni`),
  ADD CONSTRAINT `trib_dni_2_FK` FOREIGN KEY (`dni_2`) REFERENCES `docente` (`dni`),
  ADD CONSTRAINT `trib_dni_3_FK` FOREIGN KEY (`dni_3`) REFERENCES `docente` (`dni`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

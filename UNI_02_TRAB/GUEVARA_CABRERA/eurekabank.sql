-- phpMyAdmin SQL Dump
-- version 4.3.11
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-11-2015 a las 08:53:50
-- Versión del servidor: 5.6.24
-- Versión de PHP: 5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `eurekabank`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `ConsultarCuenta`(IN `Ncuenta` VARCHAR(50))
    NO SQL
select c.chr_cuencodigo, c.chr_monecodigo, c.chr_sucucodigo, c.chr_emplcreacuenta, c.dec_cuensaldo, c.dtt_cuenfechacreacion, c.vch_cuenestado, c.int_cuencontmov, c.chr_cuenclave, cl.chr_cliecodigo, cl.vch_cliepaterno, cl.vch_cliematerno, cl.vch_clienombre, cl.chr_cliedni, cl.vch_clieciudad, cl.vch_cliedireccion, cl.vch_clietelefono, cl.vch_clieemail,mo.vch_monedescripcion
from cuenta c 
inner join cliente cl on c.chr_cliecodigo=cl.chr_cliecodigo 
inner join moneda mo on mo.chr_monecodigo=c.chr_monecodigo
where c.chr_cuencodigo=Ncuenta$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `HacerDeposito`(IN `p_cuenta` VARCHAR(50), IN `p_empleado` VARCHAR(50), IN `p_importe` FLOAT)
    NO SQL
begin 
DECLARE moneda char(2);
	DECLARE costoMov decimal(12,2);
	DECLARE cont int;

	DECLARE EXIT HANDLER FOR SQLEXCEPTION, SQLWARNING, NOT FOUND
	BEGIN
		-- Cancela la transacción
		rollback; 
		-- Retorna el estado
		select -1 as state, 'Error en el proceso de actualización.' as message;
	END;

	-- Iniciar Transacción
	start transaction;
	
	-- Tabla Cuenta
	select int_cuencontmov, chr_monecodigo into cont, moneda
	from cuenta where chr_cuencodigo = p_cuenta;
	
	select dec_costimporte into costoMov
	from costomovimiento
	where chr_monecodigo = moneda;
	
	-- Registrar el deposito
	update cuenta
		set dec_cuensaldo = dec_cuensaldo + p_importe - costoMov,
			int_cuencontmov = int_cuencontmov + 2
		where chr_cuencodigo = p_cuenta;

	-- Registrar el movimiento
	set cont := cont + 1;	
	insert into movimiento(chr_cuencodigo,int_movinumero,dtt_movifecha,
		chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia)
		values(p_cuenta,cont,current_date,p_empleado,'003',p_importe,null);

	-- Registrar el costo del movimiento
	set cont := cont + 1;
	insert into movimiento(chr_cuencodigo,int_movinumero,dtt_movifecha,
		chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia)
		values(p_cuenta,cont,current_date,p_empleado,'010',costoMov,null);
		
	-- Confirma Transacción
	commit;

	-- Retorna el estado
	select 1 as state, 'Proceso ok' as message;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `HacerRetiro`(IN `p_cuenta` VARCHAR(50), IN `p_empleado` VARCHAR(50), IN `p_importe` FLOAT)
BEGIN
DECLARE moneda char(2);
	DECLARE costoMov decimal(12,2);
	DECLARE cont int;
	DECLARE saldo decimal(12,2);


	DECLARE EXIT HANDLER FOR SQLEXCEPTION, SQLWARNING, NOT FOUND
	BEGIN
		-- Cancela la transacción
		rollback; 
		-- Retorna el estado
		select -1 as state, 'Error en el proceso de actualización.' as message;
	END;

	-- Iniciar Transacción
	start transaction;
	
	-- Tabla Cuenta
	select int_cuencontmov, chr_monecodigo, dec_cuensaldo
	into cont, moneda, saldo
	from cuenta 
	where chr_cuencodigo = p_cuenta;
	
	select dec_costimporte into costoMov
	from costomovimiento
	where chr_monecodigo = moneda;

	if saldo < (p_importe + costoMov) then

		select -1 as state, 'Saldo insuficiente.' as message;

	else

		-- Registrar el deposito
		update cuenta
			set dec_cuensaldo = dec_cuensaldo - p_importe - costoMov,
				int_cuencontmov = int_cuencontmov + 2
			where chr_cuencodigo = p_cuenta;

		-- Registrar el movimiento
		set cont := cont + 1;
		insert into movimiento(chr_cuencodigo,int_movinumero,dtt_movifecha,
			chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia)
			values(p_cuenta,cont,current_date,p_empleado,'004',p_importe,null);

		-- Registrar el costo del movimiento
		set cont := cont + 1;
		insert into movimiento(chr_cuencodigo,int_movinumero,dtt_movifecha,
			chr_emplcodigo,chr_tipocodigo,dec_moviimporte,chr_cuenreferencia)
			values(p_cuenta,cont,current_date,p_empleado,'010',costoMov,null);

		-- Confirma Transacción
		commit;

		-- Retorna el estado
		select 1 as state, 'Proceso ok' as message;

	end if;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `IniciarLogin`(IN `user` VARCHAR(50), IN `clave` VARCHAR(50))
    NO SQL
select * from empleado em where em.vch_emplusuario=user AND
em.vch_emplclave=clave$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ListaMovimientoCuenta`(IN `IdCuenta` INT)
BEGIN
select m.cuenta,m.fecha,m.codtipo,m.tipo,m.accion,m.importe from vmovimientos m where m.cuenta=IdCuenta;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ListaMovimientosEmpleado`(IN `codemple` VARCHAR(50))
    NO SQL
select m.chr_cuencodigo, m.dec_moviimporte, m.dtt_movifecha, m.chr_emplcodigo, tv.chr_tipocodigo,tv.vch_tipodescripcion,tv.vch_tipoaccion from movimiento m inner join tipomovimiento tv on tv.chr_tipocodigo=m.chr_tipocodigo 
where m.chr_emplcodigo=codemple
order by m.dtt_movifecha DESC$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asignado`
--

CREATE TABLE IF NOT EXISTS `asignado` (
  `chr_asigcodigo` char(6) NOT NULL,
  `chr_sucucodigo` char(3) NOT NULL,
  `chr_emplcodigo` char(4) NOT NULL,
  `dtt_asigfechaalta` date NOT NULL,
  `dtt_asigfechabaja` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `asignado`
--

INSERT INTO `asignado` (`chr_asigcodigo`, `chr_sucucodigo`, `chr_emplcodigo`, `dtt_asigfechaalta`, `dtt_asigfechabaja`) VALUES
('000001', '001', '0004', '2007-11-15', NULL),
('000002', '002', '0001', '2007-11-20', NULL),
('000003', '003', '0002', '2007-11-28', NULL),
('000004', '004', '0003', '2007-12-12', '2008-03-25'),
('000005', '005', '0006', '2007-12-20', NULL),
('000006', '006', '0005', '2008-01-05', NULL),
('000007', '004', '0007', '2008-01-07', NULL),
('000008', '005', '0008', '2008-01-07', NULL),
('000009', '001', '0011', '2008-01-08', NULL),
('000010', '002', '0009', '2008-01-08', NULL),
('000011', '006', '0010', '2008-01-08', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargomantenimiento`
--

CREATE TABLE IF NOT EXISTS `cargomantenimiento` (
  `chr_monecodigo` char(2) NOT NULL,
  `dec_cargMontoMaximo` decimal(12,2) NOT NULL,
  `dec_cargImporte` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cargomantenimiento`
--

INSERT INTO `cargomantenimiento` (`chr_monecodigo`, `dec_cargMontoMaximo`, `dec_cargImporte`) VALUES
('01', '3500.00', '7.00'),
('02', '1200.00', '2.50');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE IF NOT EXISTS `cliente` (
  `chr_cliecodigo` char(5) NOT NULL,
  `vch_cliepaterno` varchar(25) NOT NULL,
  `vch_cliematerno` varchar(25) NOT NULL,
  `vch_clienombre` varchar(30) NOT NULL,
  `chr_cliedni` char(8) NOT NULL,
  `vch_clieciudad` varchar(30) NOT NULL,
  `vch_cliedireccion` varchar(50) NOT NULL,
  `vch_clietelefono` varchar(20) DEFAULT NULL,
  `vch_clieemail` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`chr_cliecodigo`, `vch_cliepaterno`, `vch_cliematerno`, `vch_clienombre`, `chr_cliedni`, `vch_clieciudad`, `vch_cliedireccion`, `vch_clietelefono`, `vch_clieemail`) VALUES
('00001', 'CORONEL', 'CASTILLO', 'ERIC GUSTAVO', '06914897', 'LIMA', 'LOS OLIVOS', '9666-4457', 'gcoronel@viabcp.com'),
('00002', 'VALENCIA', 'MORALES', 'PEDRO HUGO', '01576173', 'LIMA', 'MAGDALENA', '924-7834', 'pvalencia@terra.com.pe'),
('00003', 'MARCELO', 'VILLALOBOS', 'RICARDO', '10762367', 'LIMA', 'LINCE', '993-62966', 'ricardomarcelo@hotmail.com'),
('00004', 'ROMERO', 'CASTILLO', 'CARLOS ALBERTO', '06531983', 'LIMA', 'LOS OLIVOS', '865-84762', 'c.romero@hotmail.com'),
('00005', 'ARANDA', 'LUNA', 'ALAN ALBERTO', '10875611', 'LIMA', 'SAN ISIDRO', '834-67125', 'a.aranda@hotmail.com'),
('00006', 'AYALA', 'PAZ', 'JORGE LUIS', '10679245', 'LIMA', 'SAN BORJA', '963-34769', 'j.ayala@yahoo.com'),
('00007', 'CHAVEZ', 'CANALES', 'EDGAR RAFAEL', '10145693', 'LIMA', 'MIRAFLORES', '999-96673', 'e.chavez@gmail.com'),
('00008', 'FLORES', 'CHAFLOQUE', 'ROSA LIZET', '10773456', 'LIMA', 'LA MOLINA', '966-87567', 'r.florez@hotmail.com'),
('00009', 'FLORES', 'CASTILLO', 'CRISTIAN RAFAEL', '10346723', 'LIMA', 'LOS OLIVOS', '978-43768', 'c.flores@hotmail.com'),
('00010', 'GONZALES', 'GARCIA', 'GABRIEL ALEJANDRO', '10192376', 'LIMA', 'SAN MIGUEL', '945-56782', 'g.gonzales@yahoo.es'),
('00011', 'LAY', 'VALLEJOS', 'JUAN CARLOS', '10942287', 'LIMA', 'LINCE', '956-12657', 'j.lay@peru.com'),
('00012', 'MONTALVO', 'SOTO', 'DEYSI LIDIA', '10612376', 'LIMA', 'SURCO', '965-67235', 'd.montalvo@hotmail.com'),
('00013', 'RICALDE', 'RAMIREZ', 'ROSARIO ESMERALDA', '10761324', 'LIMA', 'MIRAFLORES', '991-23546', 'r.ricalde@gmail.com'),
('00014', 'RODRIGUEZ', 'FLORES', 'ENRIQUE MANUEL', '10773345', 'LIMA', 'LINCE', '976-82838', 'e.rodriguez@gmail.com'),
('00015', 'ROJAS', 'OSCANOA', 'FELIX NINO', '10238943', 'LIMA', 'LIMA', '962-32158', 'f.rojas@yahoo.com'),
('00016', 'TEJADA', 'DEL AGUILA', 'TANIA LORENA', '10446791', 'LIMA', 'PUEBLO LIBRE', '966-23854', 't.tejada@hotmail.com'),
('00017', 'VALDEVIESO', 'LEYVA', 'LIDIA ROXANA', '10452682', 'LIMA', 'SURCO', '956-78951', 'r.valdivieso@terra.com.pe'),
('00018', 'VALENTIN', 'COTRINA', 'JUAN DIEGO', '10398247', 'LIMA', 'LA MOLINA', '921-12456', 'j.valentin@terra.com.pe'),
('00019', 'YAURICASA', 'BAUTISTA', 'YESABETH', '10934584', 'LIMA', 'MAGDALENA', '977-75777', 'y.yauricasa@terra.com.pe'),
('00020', 'ZEGARRA', 'GARCIA', 'FERNANDO MOISES', '10772365', 'LIMA', 'SAN ISIDRO', '936-45876', 'f.zegarra@hotmail.com');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contador`
--

CREATE TABLE IF NOT EXISTS `contador` (
  `vch_conttabla` varchar(30) NOT NULL,
  `int_contitem` int(11) NOT NULL,
  `int_contlongitud` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `contador`
--

INSERT INTO `contador` (`vch_conttabla`, `int_contitem`, `int_contlongitud`) VALUES
('Asignado', 11, 6),
('Cliente', 21, 5),
('Empleado', 11, 4),
('Moneda', 2, 2),
('Parametro', 2, 3),
('Sucursal', 7, 3),
('TipoMovimiento', 10, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `costomovimiento`
--

CREATE TABLE IF NOT EXISTS `costomovimiento` (
  `chr_monecodigo` char(2) NOT NULL,
  `dec_costimporte` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `costomovimiento`
--

INSERT INTO `costomovimiento` (`chr_monecodigo`, `dec_costimporte`) VALUES
('01', '2.00'),
('02', '0.60');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cuenta`
--

CREATE TABLE IF NOT EXISTS `cuenta` (
  `chr_cuencodigo` char(8) NOT NULL,
  `chr_monecodigo` char(2) NOT NULL,
  `chr_sucucodigo` char(3) NOT NULL,
  `chr_emplcreacuenta` char(4) NOT NULL,
  `chr_cliecodigo` char(5) NOT NULL,
  `dec_cuensaldo` decimal(12,2) NOT NULL,
  `dtt_cuenfechacreacion` date NOT NULL,
  `vch_cuenestado` varchar(15) NOT NULL DEFAULT 'ACTIVO',
  `int_cuencontmov` int(11) NOT NULL,
  `chr_cuenclave` char(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cuenta`
--

INSERT INTO `cuenta` (`chr_cuencodigo`, `chr_monecodigo`, `chr_sucucodigo`, `chr_emplcreacuenta`, `chr_cliecodigo`, `dec_cuensaldo`, `dtt_cuenfechacreacion`, `vch_cuenestado`, `int_cuencontmov`, `chr_cuenclave`) VALUES
('00100001', '01', '001', '0004', '00005', '8118.00', '2008-01-06', 'ACTIVO', 29, '123456'),
('00100002', '02', '001', '0004', '00005', '4560.00', '2008-01-08', 'ACTIVO', 4, '123456'),
('00200001', '01', '002', '0001', '00008', '7000.00', '2008-01-05', 'ACTIVO', 15, '123456'),
('00200002', '01', '002', '0001', '00001', '6714.00', '2008-01-09', 'ACTIVO', 9, '123456'),
('00200003', '02', '002', '0001', '00007', '6049.40', '2008-01-11', 'ACTIVO', 8, '123456');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE IF NOT EXISTS `empleado` (
  `chr_emplcodigo` char(4) NOT NULL,
  `vch_emplpaterno` varchar(25) NOT NULL,
  `vch_emplmaterno` varchar(25) NOT NULL,
  `vch_emplnombre` varchar(30) NOT NULL,
  `vch_emplciudad` varchar(30) NOT NULL,
  `vch_empldireccion` varchar(50) DEFAULT NULL,
  `vch_emplusuario` varchar(15) NOT NULL,
  `vch_emplclave` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`chr_emplcodigo`, `vch_emplpaterno`, `vch_emplmaterno`, `vch_emplnombre`, `vch_emplciudad`, `vch_empldireccion`, `vch_emplusuario`, `vch_emplclave`) VALUES
('0001', 'Romero', 'Castillo', 'Carlos Alberto', 'Trujillo', 'Call1 1 Nro. 456', 'cromero', 'chicho'),
('0002', 'Castro', 'Vargas', 'Lidia', 'Lima', 'Federico Villarreal 456 - SMP', 'lcastro', 'flaca'),
('0003', 'Reyes', 'Ortiz', 'Claudia', 'Lima', 'Av. Aviación 3456 - San Borja', 'creyes', 'linda'),
('0004', 'Ramos', 'Garibay', 'Angelica', 'Chiclayo', 'Calle Barcelona 345', 'aramos', 'china'),
('0005', 'Ruiz', 'Zabaleta', 'Claudia', 'Cusco', 'Calle Cruz Verde 364', 'cvalencia', 'angel'),
('0006', 'Cruz', 'Tarazona', 'Ricardo', 'Areguipa', 'Calle La Gruta 304', 'rcruz', 'cerebro'),
('0007', 'Diaz', 'Flores', 'Edith', 'Lima', 'Av. Pardo 546', 'ediaz', 'princesa'),
('0008', 'Sarmiento', 'Bellido', 'Claudia Rocio', 'Areguipa', 'Calle Alfonso Ugarte 1567', 'csarmiento', 'chinita'),
('0009', 'Pachas', 'Sifuentes', 'Luis Alberto', 'Trujillo', 'Francisco Pizarro 1263', 'lpachas', 'gato'),
('0010', 'Tello', 'Alarcon', 'Hugo Valentin', 'Cusco', 'Los Angeles 865', 'htello', 'machupichu'),
('0011', 'Carrasco', 'Vargas', 'Pedro Hugo', 'Chiclayo', 'Av. Balta 1265', 'pcarrasco', 'tinajones'),
('9999', 'Internet', 'Internet', 'internet', 'Internet', 'internet', 'internet', 'internet');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `interesmensual`
--

CREATE TABLE IF NOT EXISTS `interesmensual` (
  `chr_monecodigo` char(2) NOT NULL,
  `dec_inteimporte` decimal(12,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `interesmensual`
--

INSERT INTO `interesmensual` (`chr_monecodigo`, `dec_inteimporte`) VALUES
('01', '0.70'),
('02', '0.60');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `moneda`
--

CREATE TABLE IF NOT EXISTS `moneda` (
  `chr_monecodigo` char(2) NOT NULL,
  `vch_monedescripcion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `moneda`
--

INSERT INTO `moneda` (`chr_monecodigo`, `vch_monedescripcion`) VALUES
('01', 'Soles'),
('02', 'Dolares');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimiento`
--

CREATE TABLE IF NOT EXISTS `movimiento` (
  `chr_cuencodigo` char(8) NOT NULL,
  `int_movinumero` int(11) NOT NULL,
  `dtt_movifecha` date NOT NULL,
  `chr_emplcodigo` char(4) NOT NULL,
  `chr_tipocodigo` char(3) NOT NULL,
  `dec_moviimporte` decimal(12,2) NOT NULL,
  `chr_cuenreferencia` char(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `movimiento`
--

INSERT INTO `movimiento` (`chr_cuencodigo`, `int_movinumero`, `dtt_movifecha`, `chr_emplcodigo`, `chr_tipocodigo`, `dec_moviimporte`, `chr_cuenreferencia`) VALUES
('00100001', 1, '2008-01-06', '0004', '001', '2800.00', NULL),
('00100001', 2, '2008-01-15', '0004', '003', '3200.00', NULL),
('00100001', 3, '2008-01-20', '0004', '004', '800.00', NULL),
('00100001', 4, '2008-02-14', '0004', '003', '2000.00', NULL),
('00100001', 5, '2008-02-25', '0004', '004', '500.00', NULL),
('00100001', 6, '2008-03-03', '0004', '004', '800.00', NULL),
('00100001', 7, '2008-03-15', '0004', '003', '1000.00', NULL),
('00100001', 8, '2015-11-11', '0001', '003', '100.00', NULL),
('00100001', 9, '2015-11-11', '0001', '003', '100.00', NULL),
('00100001', 10, '2015-11-11', '0001', '003', '100.00', NULL),
('00100001', 11, '2015-11-11', '0001', '003', '100.00', NULL),
('00100001', 12, '2015-11-11', '0001', '003', '800.00', NULL),
('00100001', 13, '2015-11-11', '0001', '003', '800.00', NULL),
('00100001', 14, '2015-11-11', '0001', '003', '100.00', NULL),
('00100001', 15, '2015-11-11', '0002', '004', '100.00', NULL),
('00100001', 16, '2015-11-11', '0002', '004', '400.00', NULL),
('00100001', 17, '2015-11-11', '0002', '004', '400.00', NULL),
('00100001', 18, '2015-11-11', '0003', '003', '50.00', NULL),
('00100001', 19, '2015-11-11', '0003', '010', '2.00', NULL),
('00100001', 20, '2015-11-11', '0002', '003', '40.00', NULL),
('00100001', 21, '2015-11-11', '0002', '010', '2.00', NULL),
('00100001', 22, '2015-11-11', '0006', '004', '100.00', NULL),
('00100001', 23, '2015-11-11', '0006', '010', '2.00', NULL),
('00100001', 24, '2015-11-11', '0006', '004', '10.00', NULL),
('00100001', 25, '2015-11-11', '0006', '010', '2.00', NULL),
('00100001', 26, '2015-11-12', '0001', '003', '250.00', NULL),
('00100001', 27, '2015-11-12', '0001', '010', '2.00', NULL),
('00100001', 28, '2015-11-12', '0001', '004', '200.00', NULL),
('00100001', 29, '2015-11-12', '0001', '010', '2.00', NULL),
('00100002', 1, '2008-01-08', '0004', '001', '1800.00', NULL),
('00100002', 2, '2008-01-25', '0004', '004', '1000.00', NULL),
('00100002', 3, '2008-02-13', '0004', '003', '2200.00', NULL),
('00100002', 4, '2008-03-08', '0004', '003', '1500.00', NULL),
('00100002', 5, '2015-11-11', '0006', '003', '60.00', NULL),
('00100002', 6, '2015-11-11', '0006', '003', '60.00', NULL),
('00100002', 7, '2015-11-09', '0006', '003', '60.00', NULL),
('00100002', 8, '2015-11-11', '0008', '003', '60.00', NULL),
('00100002', 9, '2015-11-11', '0006', '003', '60.00', NULL),
('00200001', 1, '2008-01-05', '0001', '001', '5000.00', NULL),
('00200001', 2, '2008-01-07', '0001', '003', '4000.00', NULL),
('00200001', 3, '2008-01-09', '0001', '004', '2000.00', NULL),
('00200001', 4, '2008-01-11', '0001', '003', '1000.00', NULL),
('00200001', 5, '2008-01-13', '0001', '003', '2000.00', NULL),
('00200001', 6, '2008-01-15', '0001', '004', '4000.00', NULL),
('00200001', 7, '2008-01-19', '0001', '003', '2000.00', NULL),
('00200001', 8, '2008-01-21', '0001', '004', '3000.00', NULL),
('00200001', 9, '2008-01-23', '0001', '003', '7000.00', NULL),
('00200001', 10, '2008-01-27', '0001', '004', '1000.00', NULL),
('00200001', 11, '2008-01-30', '0001', '004', '3000.00', NULL),
('00200001', 12, '2008-02-04', '0001', '003', '2000.00', NULL),
('00200001', 13, '2008-02-08', '0001', '004', '4000.00', NULL),
('00200001', 14, '2008-02-13', '0001', '003', '2000.00', NULL),
('00200001', 15, '2008-02-19', '0001', '004', '1000.00', NULL),
('00200002', 1, '2008-01-09', '0001', '001', '3800.00', NULL),
('00200002', 2, '2008-01-20', '0001', '003', '4200.00', NULL),
('00200002', 3, '2008-03-06', '0001', '004', '1200.00', NULL),
('00200002', 4, '2015-11-12', '0006', '004', '40.00', NULL),
('00200002', 5, '2015-11-12', '0006', '010', '2.00', NULL),
('00200002', 6, '2015-11-12', '0004', '003', '20.00', NULL),
('00200002', 7, '2015-11-12', '0004', '010', '2.00', NULL),
('00200002', 8, '2015-11-12', '0004', '004', '60.00', NULL),
('00200002', 9, '2015-11-12', '0004', '010', '2.00', NULL),
('00200003', 1, '2008-01-11', '0001', '001', '2500.00', NULL),
('00200003', 2, '2008-01-17', '0001', '003', '1500.00', NULL),
('00200003', 3, '2008-01-20', '0001', '004', '500.00', NULL),
('00200003', 4, '2008-02-09', '0001', '004', '500.00', NULL),
('00200003', 5, '2008-02-25', '0001', '003', '3500.00', NULL),
('00200003', 6, '2008-03-11', '0001', '004', '500.00', NULL),
('00200003', 7, '2015-11-12', '0004', '003', '50.00', NULL),
('00200003', 8, '2015-11-12', '0004', '010', '0.60', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parametro`
--

CREATE TABLE IF NOT EXISTS `parametro` (
  `chr_paracodigo` char(3) NOT NULL,
  `vch_paradescripcion` varchar(50) NOT NULL,
  `vch_paravalor` varchar(70) NOT NULL,
  `vch_paraestado` varchar(15) NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `parametro`
--

INSERT INTO `parametro` (`chr_paracodigo`, `vch_paradescripcion`, `vch_paravalor`, `vch_paraestado`) VALUES
('001', 'ITF - Impuesto a la Transacciones Financieras', '0.08', 'ACTIVO'),
('002', 'Número de Operaciones Sin Costo', '15', 'ACTIVO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE IF NOT EXISTS `sucursal` (
  `chr_sucucodigo` char(3) NOT NULL,
  `vch_sucunombre` varchar(50) NOT NULL,
  `vch_sucuciudad` varchar(30) NOT NULL,
  `vch_sucudireccion` varchar(50) DEFAULT NULL,
  `int_sucucontcuenta` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`chr_sucucodigo`, `vch_sucunombre`, `vch_sucuciudad`, `vch_sucudireccion`, `int_sucucontcuenta`) VALUES
('001', 'Sipan', 'Chiclayo', 'Av. Balta 1456', 2),
('002', 'Chan Chan', 'Trujillo', 'Jr. Independencia 456', 3),
('003', 'Los Olivos', 'Lima', 'Av. Central 1234', 0),
('004', 'Pardo', 'Lima', 'Av. Pardo 345 - Miraflores', 0),
('005', 'Misti', 'Arequipa', 'Bolivar 546', 0),
('006', 'Machupicchu', 'Cusco', 'Calle El Sol 534', 0),
('007', 'Grau', 'Piura', 'Av. Grau 1528', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipomovimiento`
--

CREATE TABLE IF NOT EXISTS `tipomovimiento` (
  `chr_tipocodigo` char(3) NOT NULL,
  `vch_tipodescripcion` varchar(40) NOT NULL,
  `vch_tipoaccion` varchar(10) NOT NULL,
  `vch_tipoestado` varchar(15) NOT NULL DEFAULT 'ACTIVO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipomovimiento`
--

INSERT INTO `tipomovimiento` (`chr_tipocodigo`, `vch_tipodescripcion`, `vch_tipoaccion`, `vch_tipoestado`) VALUES
('001', 'Apertura de Cuenta', 'INGRESO', 'ACTIVO'),
('002', 'Cancelar Cuenta', 'SALIDA', 'ACTIVO'),
('003', 'Deposito', 'INGRESO', 'ACTIVO'),
('004', 'Retiro', 'SALIDA', 'ACTIVO'),
('005', 'Interes', 'INGRESO', 'ACTIVO'),
('006', 'Mantenimiento', 'SALIDA', 'ACTIVO'),
('007', 'ITF', 'SALIDA', 'ACTIVO'),
('008', 'Transferencia', 'INGRESO', 'ACTIVO'),
('009', 'Transferencia', 'SALIDA', 'ACTIVO'),
('010', 'Cargo por Movimiento', 'SALIDA', 'ACTIVO');

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `vmovimientos`
--
CREATE TABLE IF NOT EXISTS `vmovimientos` (
`cuenta` char(8)
,`nromov` int(11)
,`fecha` date
,`codtipo` char(3)
,`tipo` varchar(40)
,`accion` varchar(10)
,`importe` decimal(12,2)
,`referencia` char(8)
);

-- --------------------------------------------------------

--
-- Estructura para la vista `vmovimientos`
--
DROP TABLE IF EXISTS `vmovimientos`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `vmovimientos` AS select `m`.`chr_cuencodigo` AS `cuenta`,`m`.`int_movinumero` AS `nromov`,`m`.`dtt_movifecha` AS `fecha`,`tm`.`chr_tipocodigo` AS `codtipo`,`tm`.`vch_tipodescripcion` AS `tipo`,`tm`.`vch_tipoaccion` AS `accion`,`m`.`dec_moviimporte` AS `importe`,`m`.`chr_cuenreferencia` AS `referencia` from (`tipomovimiento` `tm` join `movimiento` `m` on((`tm`.`chr_tipocodigo` = `m`.`chr_tipocodigo`)));

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asignado`
--
ALTER TABLE `asignado`
  ADD PRIMARY KEY (`chr_asigcodigo`), ADD KEY `idx_asignado01` (`chr_emplcodigo`), ADD KEY `idx_asignado02` (`chr_sucucodigo`);

--
-- Indices de la tabla `cargomantenimiento`
--
ALTER TABLE `cargomantenimiento`
  ADD PRIMARY KEY (`chr_monecodigo`), ADD KEY `idx_cargomantenimiento01` (`chr_monecodigo`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`chr_cliecodigo`);

--
-- Indices de la tabla `contador`
--
ALTER TABLE `contador`
  ADD PRIMARY KEY (`vch_conttabla`);

--
-- Indices de la tabla `costomovimiento`
--
ALTER TABLE `costomovimiento`
  ADD PRIMARY KEY (`chr_monecodigo`), ADD KEY `idx_costomovimiento` (`chr_monecodigo`);

--
-- Indices de la tabla `cuenta`
--
ALTER TABLE `cuenta`
  ADD PRIMARY KEY (`chr_cuencodigo`), ADD KEY `idx_cuenta01` (`chr_cliecodigo`), ADD KEY `idx_cuenta02` (`chr_emplcreacuenta`), ADD KEY `idx_cuenta03` (`chr_sucucodigo`), ADD KEY `idx_cuenta04` (`chr_monecodigo`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`chr_emplcodigo`), ADD UNIQUE KEY `U_Empleado_vch_emplusuario` (`vch_emplusuario`);

--
-- Indices de la tabla `interesmensual`
--
ALTER TABLE `interesmensual`
  ADD PRIMARY KEY (`chr_monecodigo`), ADD KEY `idx_interesmensual01` (`chr_monecodigo`);

--
-- Indices de la tabla `moneda`
--
ALTER TABLE `moneda`
  ADD PRIMARY KEY (`chr_monecodigo`);

--
-- Indices de la tabla `movimiento`
--
ALTER TABLE `movimiento`
  ADD PRIMARY KEY (`chr_cuencodigo`,`int_movinumero`), ADD KEY `idx_movimiento01` (`chr_tipocodigo`), ADD KEY `idx_movimiento02` (`chr_emplcodigo`), ADD KEY `idx_movimiento03` (`chr_cuencodigo`);

--
-- Indices de la tabla `parametro`
--
ALTER TABLE `parametro`
  ADD PRIMARY KEY (`chr_paracodigo`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`chr_sucucodigo`);

--
-- Indices de la tabla `tipomovimiento`
--
ALTER TABLE `tipomovimiento`
  ADD PRIMARY KEY (`chr_tipocodigo`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asignado`
--
ALTER TABLE `asignado`
ADD CONSTRAINT `fk_asignado_empleado` FOREIGN KEY (`chr_emplcodigo`) REFERENCES `empleado` (`chr_emplcodigo`),
ADD CONSTRAINT `fk_asignado_sucursal` FOREIGN KEY (`chr_sucucodigo`) REFERENCES `sucursal` (`chr_sucucodigo`);

--
-- Filtros para la tabla `cargomantenimiento`
--
ALTER TABLE `cargomantenimiento`
ADD CONSTRAINT `fk_cargomantenimiento_moneda` FOREIGN KEY (`chr_monecodigo`) REFERENCES `moneda` (`chr_monecodigo`);

--
-- Filtros para la tabla `costomovimiento`
--
ALTER TABLE `costomovimiento`
ADD CONSTRAINT `fk_costomovimiento_moneda` FOREIGN KEY (`chr_monecodigo`) REFERENCES `moneda` (`chr_monecodigo`);

--
-- Filtros para la tabla `cuenta`
--
ALTER TABLE `cuenta`
ADD CONSTRAINT `fk_cuenta_cliente` FOREIGN KEY (`chr_cliecodigo`) REFERENCES `cliente` (`chr_cliecodigo`),
ADD CONSTRAINT `fk_cuenta_moneda` FOREIGN KEY (`chr_monecodigo`) REFERENCES `moneda` (`chr_monecodigo`),
ADD CONSTRAINT `fk_cuenta_sucursal` FOREIGN KEY (`chr_sucucodigo`) REFERENCES `sucursal` (`chr_sucucodigo`),
ADD CONSTRAINT `fk_cuente_empleado` FOREIGN KEY (`chr_emplcreacuenta`) REFERENCES `empleado` (`chr_emplcodigo`);

--
-- Filtros para la tabla `interesmensual`
--
ALTER TABLE `interesmensual`
ADD CONSTRAINT `fk_interesmensual_moneda` FOREIGN KEY (`chr_monecodigo`) REFERENCES `moneda` (`chr_monecodigo`);

--
-- Filtros para la tabla `movimiento`
--
ALTER TABLE `movimiento`
ADD CONSTRAINT `fk_movimiento_cuenta` FOREIGN KEY (`chr_cuencodigo`) REFERENCES `cuenta` (`chr_cuencodigo`),
ADD CONSTRAINT `fk_movimiento_empleado` FOREIGN KEY (`chr_emplcodigo`) REFERENCES `empleado` (`chr_emplcodigo`),
ADD CONSTRAINT `fk_movimiento_tipomovimiento` FOREIGN KEY (`chr_tipocodigo`) REFERENCES `tipomovimiento` (`chr_tipocodigo`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

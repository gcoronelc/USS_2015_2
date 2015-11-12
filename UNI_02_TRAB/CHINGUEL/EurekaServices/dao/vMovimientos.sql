
CREATE VIEW vMovimientos(
	cuenta, nromov, fecha, codtipo, tipo, accion, importe, referencia 
)
AS
SELECT 
	m.chr_cuencodigo, m.int_movinumero, m.dtt_movifecha, tm.chr_tipocodigo,  
	tm.vch_tipodescripcion, tm.vch_tipoaccion, m.dec_moviimporte, m.chr_cuenreferencia
FROM tipomovimiento tm
JOIN movimiento m
ON tm.chr_tipocodigo = m.chr_tipocodigo;

SELECT * FROM vMovimientos;

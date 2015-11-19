<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>INSERTAR PRODUCTO</title>
</head>
<body>
  <h1>INSERTAR PRODUCTO</h1>
  <a href="<c:url value="/"/>">Retornar</a>
  <p>MENSAJE: ${msg}</p>
  <p>ERROR: ${error}</p>
  <form method="post" action="insProductoGrabar.htm">
    <table>
      <tr>
        <td>NOMBRE</td>
        <td><input type="text" name="nombre"/></td>
      </tr>
      <tr>
        <td>PRECIO</td>
        <td><input type="text" name="precio"/></td>
      </tr>
      <tr>
        <td>STOCK</td>
        <td><input type="text" name="stock"/></td>
      </tr>
    </table>
    <input type="submit" value="Grabar"/>
  </form>
</body>
</html>
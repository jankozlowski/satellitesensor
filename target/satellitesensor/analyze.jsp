<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<style>

body{
	background: transparent url(http://subtlepatterns2015.subtlepatterns.netdna-cdn.com/patterns/hexellence.png) ;
}

table{
text-align:center;
border: 1px solid black;
border-collapse:collapse
}
td{border: 1px solid black;background-color:rgba(172, 249, 148, 0.54)}
tr{border: 1px solid black;background-color:rgba(172, 249, 148, 0.54)}
th {
    background-color: #4CAF50;
    color: white;
    border: 1px solid black;
}

</style>

</head>
<body>
<table>
<tr>
<th>Norad Id</th>
<th>Nazwa</th>
<th>Rodzaj</th>
<th>Użytkownik</th>
<th>Data Startu</th>
<th>Pojazd Startowy</th>
<th>Cel</th>
<th>Masa</th>
<th>Klasa Wysokosci</th>
<th>Orbita</th>
<th>Perygeum</th>
<th>Apogeum</th>
<th>Mimosrod</th>
<th>inklinacja</th>
<th>Czas obiegu (min)</th>
<th>SEM (km)</th>
<th>Sensor</th>
<th>Technika skanowania</th>
<th>Pasmo</th>
<th>Zakres Fal</th>
<th>Centralna długość Fal</th>
<th>Rozdzielcość (m)</th>
</tr>

<c:forEach items="${satelity}" var="element" varStatus="loop"> 

  <tr>
    <td rowspan='${rows[loop.index]+1}'>${element.noradId}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.nazwa}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.rodzaj}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.uzytkownicy}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.dataStartu}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.pojazdStartowy}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.cel}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.masa}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.klasaWysokosci}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.orbita}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.perygeum}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.apogeum}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.mimosrod}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.inklinacja}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.czasObiegu}</td>
    <td rowspan='${rows[loop.index]+1}'>${element.orbita.SEM}</td>
   <c:forEach items="${element.sensory}" var="element2"> 
   <tr>
         <td rowspan='${fn:length(element2.kanaly)+1}'>${element2.nazwa}</td>
		 <td rowspan='${fn:length(element2.kanaly)+1}'>${element2.technikaSkanowania}</td>
		 <td rowspan='${fn:length(element2.kanaly)+1}'>${element2.szerokoscPasma}</td>
   </tr>
   
   <c:forEach items="${element2.kanaly}" var="element3"> 
   <tr>
         <td>${element3.zakresFal}</td>
		 <td>${element3.centralnaDlugosFal}</td>
		 <td>${element3.rozdzielcosc}</td>
   </tr>
   </c:forEach>
   
   </c:forEach>
  </tr>
</c:forEach>

</table>
<p>Found Satelites: ${satelity.size()}</p>
</body>
</html>

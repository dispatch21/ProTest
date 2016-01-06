<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="com.homedepot.mm.mr.probe.ProbeResult"%><HTML>
<jsp:useBean id="probeResult"
	class="com.homedepot.mm.mr.probe.ProbeResult" />
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<TITLE>Application Probe - ProTransactions</TITLE>
</HEAD>
<BODY>
	<TABLE width="90%" border="0" align="center">
		<TR>
			<TD align="right" width="200"><FONT size="+1"
				face="Arial, sans-serif"> Application Status:&nbsp; </FONT></TD>
			<TD align="left"><jsp:getProperty name="probeResult"
					property="result" /></TD>
		</TR>
	</TABLE>
	<BR />
	<BR />
	<TABLE width="90%" border="0" align="left">
		<TR>
			<TD align="right" width="200"><FONT size="+1"
				face="Arial, sans-serif"> Application:&nbsp; </FONT></TD>
			<TD align="left"><FONT size="+1" face="Arial, sans-serif">
					<jsp:getProperty name="probeResult" property="applicationName" />
			</FONT></TD>
		</TR>

		<TR>
			<TD align="right" width="200"><FONT size="+1"
				face="Arial, sans-serif"> Version:&nbsp; </FONT></TD>
			<TD align="left"><FONT size="+1" face="Arial, sans-serif">
					<jsp:getProperty name="probeResult" property="appVersion" />
			</FONT></TD>
		</TR>

		<TR>
			<TD align="right" width="200"><FONT size="+1"
				face="Arial, sans-serif"> System:&nbsp; </FONT></TD>
			<TD align="left"><FONT size="+1" face="Arial, sans-serif">
					<jsp:getProperty name="probeResult" property="system" />
			</FONT></TD>
		</TR>
		<TR>
			<TD align="right" width="200"><FONT size="+1"
				face="Arial, sans-serif"> Sub-system:&nbsp;</FONT></TD>
			<TD align="left"><FONT size="+1" face="Arial, sans-serif">
					<jsp:getProperty name="probeResult" property="subSystem" />
			</FONT></TD>
		</TR>

		<TR>
			<TD align="right" width="200"><FONT size="+1"
				face="Arial, sans-serif"> Last Build Date::&nbsp;</FONT></TD>
			<TD align="left"><FONT size="+1" face="Arial, sans-serif">
					<jsp:getProperty name="probeResult" property="_buildDate" />
			</FONT></TD>
		</TR>

		<TR>
			<%
				java.util.ArrayList<String> messages = probeResult.getMessages();
				if (messages.size() > 0) {
			%>
			<TD align="right" width="200"><FONT size="+1"
				face="Arial, sans-serif"> Errors:&nbsp; </FONT></TD>
			<TD><FONT size="+1" face="Arial, sans-serif"> <%
 	for (String message : messages) {
 			out.write(message + "<BR/>");
 		}
 %>
			</FONT></TD>
			<%
			}
		%>
		</TR>
	</TABLE>
</BODY>
</HTML>
# FTP_Server
-FTP Project using two TCP connections for communication (2 ports).
a. Client sends its username.
i. Server will search in the first file if this username exist
• If it exists, it will request the password.
b. Client sends its password.
i. Server will compare this password with the password that belong to the
username
• If they both match, it will send “Login Successfully”.
• Note: if the username or password is wrong, the server will reply with
“Login Failed and the connection will terminate”.
c. Client will enter the command “show my directories” to list all the available dirs.
i. Server will retrieve list all the available dirs.
d. Client will send the wanted dir. to the server.
i. Server will reply with the available files.
e. Client sends a request which includes the name of the targeted file (download file).
i. Server responses with the file content.
f. Client enters the command “Close” to close the connection that is created on the
second port#.
i. It means that the client can download another file without login again.

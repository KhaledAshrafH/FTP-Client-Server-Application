# FTP Client-Server Application

This is a implementation of a FTP (File Transfer Protocol) client-server application using Java sockets. The client can connect to the server, login with username and password, and download files from the server. The server can handle multiple clients and authenticate them using a file that stores the authorized users and their passwords. The server also has a file that stores the directories and files that are allowed for each client.

## Features

This project has the following features:

- Multiple clients: The server can handle multiple clients that run on different devices and connect to the server using its IP address.
- Authentication: The server can verify the identity of the clients using their username and password that are stored in a text file.
- File transfer: The client can download files from the server using a separate data connection that is created on a different port number.
- Directory listing: The client can see the list of directories and files that are available for them on the server using a simple command.
- Connection closing: The client can close the connection with the server after downloading a file and resume it later without logging in again.

## How to run

To run this project, you need to have Java installed on your machine.

1. Clone this repository or download it as a zip file.
2. Open a terminal or command prompt and navigate to the project folder.
3. Compile the source code using the following command:
    ```bash
    javac FTP_Client.java FTP_Server.java
    ```
4. Run the server using the following command:
    ```bash
    java FTP_Server
    ```
5. Run the client using the following command:
    ```bash
    java FTP_Client
    ```
6. You can run multiple clients on different devices by specifying the IP address of the server as an argument to the client program. For example:
    ```bash
    java FTP_Client 192.168.0.1
    ```

## How to use

To use this application, follow these steps:

1. When you run the client program, you will be prompted to enter your username.
2. The server will check if your username exists in the UserNames Directory. If it does, it will ask you to enter your password.
3. The server will compare your password with the one stored in the username.txt file. If they match, you will be logged in successfully.
4. To see the list of directories that are available for you, enter the command `show my directories`.
5. To see the list of files in a specific directory, enter the command `show <directory name>`. For example, `show Movies`.
6. To download a file from the server, enter the command `<file name>`. For example, `Joker`.
7. To close the connection with the server, enter the command `close`.

## Files

This project contains the following files:

- FTP_Client.java: This is the source code for the client program.
- FTP_Server.java: This is the source code for the server program.
- UsersNames: This is a directory that stores the authorized users and their passwords in the format `username.txt contains password`.
- Directories: This is a directory that stores the directories and files that are allowed for each user.
- Movies, Music, Docs: These are sample directories that contain some sample files for testing purposes.

## Limitations

This project has some limitations that can be improved in future versions:

- The communication between the client and the server is not encrypted or secure.
- The server does not support concurrent file transfers or resume interrupted downloads.
- The client does not have a graphical user interface or any error handling mechanisms.
- The files and directories are hard-coded in the text files and cannot be modified dynamically.

## License

This project is licensed under [MIT License].

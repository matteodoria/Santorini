# Santorini - Java Application

![](src/main/Resources/Graphic/Screen/home.png)

Welcome to **Santorini**, a Java-based application that supports multiple modes of execution: GUI, CLI, and server mode.  
This document outlines the requirements, setup instructions, and additional technical details for running the application.

---

## 🛠 Requirements

- **Java version**: 13.0.2 or greater

---

## 🚀 How to Launch

Open your terminal or command prompt and use the following command:

```bash
java -jar /path/to/santorini.jar [mode]
```

Replace `/path/to/` with the actual path to the `.jar` file.

### Available Modes

| Mode     | Keyword       | Description                           |
|----------|---------------|---------------------------------------|
| GUI      | *(none)*      | Launches the graphical user interface. Also works by double-clicking the `.jar` file. |
| CLI      | `cli`         | Launches the command-line interface.  |
| Server   | `server`      | Starts the server.                    |
| Invalid  | *(other)*     | Any other keyword will terminate the program. |

> *Keywords are case-insensitive.*

---

## 🎨 CLI Color Support on Windows

The CLI uses ANSI escape codes for color output. While this works out-of-the-box on **Linux** and **macOS**, it may not be supported on **Windows 10 (Version 1511 or newer)** by default.

### To enable ANSI colors on Windows:

1. Open Command Prompt or PowerShell.
2. Execute the following command:
```bash
reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f
```
3. Press Enter and restart your shell.

### To disable if needed:
```bash
reg add HKEY_CURRENT_USER\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000000 /f
```

## 🌐 Connection Details

- **Default IP**: `localhost`
- **Default Port**: `9100`
- The server always listens on port **9100**.
- These settings can be modified in the file `connection.json`.
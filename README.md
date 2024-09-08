# Twitter Counter Project

This project is an Android application that provides a comprehensive Twitter-like text counter, adhering to Twitter's character counting rules. It offers additional features such as posting tweets, clearing text, copying text to the clipboard, and displaying live character count feedback to users.

## Table of Contents
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [Usage](#usage)
- [Testing](#testing)

## Features
- **Character Counter**: Counts the number of characters in a tweet following Twitter's rules.
- **Emoji and Language Support**: Counts emojis and special language characters according to Twitter's guidelines.
- **URL Handling**: Counts URLs as fixed-length, regardless of the actual length.
- **Post Tweet**: Posts a tweet and receives success or failure feedback (currently uses static account credentials).
- **Copy to Clipboard**: Copy text to the clipboard with feedback notifications.
- **Clear Text**: Clear the tweet input field.
- **Live Feedback**: Enables or disables the "Post" button based on the number of characters typed.

## Architecture
The project follows the Clean Architecture pattern, separating the application into three main layers:

- **Presentation Layer**: Contains UI-related components (e.g., ViewModels, Activities, Fragments) that interact with the domain layer.
- **Domain Layer**: Contains business logic, use cases, and repository interfaces.
- **Data Layer**: Responsible for handling data operations, such as network requests and data storage.

## Tech Stack
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel) with Clean Architecture
- **Dependency Injection**: Dagger Hilt
- **Coroutines and Flow**: For asynchronous programming
- **Retrofit**: For network requests
- **JUnit and Mockito**: For unit testing

## Setup
To set up and run the project on your local machine, follow these steps:

1. **Clone the repository**:
    ```bash
    git clone https://github.com/E-Khaled/TwitterCounter.git
    cd twitter-counter
    ```

2. **Open the project in Android Studio**:
    - Make sure you have the latest version of Android Studio installed.

3. **Sync Gradle**:
    - Allow Gradle to sync and download the necessary dependencies.

4. **Configure API Keys (if applicable)**:
    - Add any required API keys to your `local.properties` or appropriate configuration files.

5. **Run the project**:
    - Select a device or emulator and click Run to build and launch the app.

## Usage
- **Typing Text**: Start typing your tweet, and the app will automatically display the character count according to Twitter's rules.
- **Post Tweet**: Click the "Post" button to simulate posting a tweet. If the post is successful, a toast message will display a success notification.
- **Copy Text**: Copy text to the clipboard by clicking the "Copy" button.
- **Clear Text**: Click the "Clear" button to clear the input field.

## Testing
The project is well-tested using JUnit and Mockito. Key components such as use cases and repositories have corresponding test cases to ensure functionality.

To run the tests:

1. **Open the Test directory**: In Android Studio, navigate to the test directory.
2. **Run tests**: Right-click on the test directory or an individual test file and select Run 'Tests in...'.

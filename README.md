Magic Fridge
(https://via.placeholder.com/128/FFC107/000000?text=MF)
An Android application designed to help you discover new recipes based on the ingredients you have on hand. Built with a modern, multi-module Clean Architecture approach.
Table of Contents
Features
Tech Stack & Architecture
Tech Stack
Architecture
Project Structure
Setup & Build
License

Features
Magic Fridge provides a simple and intuitive way to find cooking inspiration.
🔍 Multi-Ingredient Search: Find recipes that contain all the ingredients you specify (e.g., "chicken, lemon, rice").
🎲 Random Recipe: Don't know what to cook? Get a random recipe suggestion with a single tap.
📖 Detailed Recipe View: Access complete recipe details, including a list of ingredients with measurements and step-by-step instructions.
❤️ Favorites & Offline Access: Save your favorite recipes for later. All saved recipes are stored locally on your device and are available even without an internet connection.
📱 Modern, Responsive UI: A clean and simple user interface built entirely with Jetpack Compose.


Tech Stack & Architecture
This project is a showcase of modern Android development best practices, emphasizing a clean, scalable, and maintainable architecture.
Tech Stack
UI: Jetpack Compose for building the entire UI declaratively.
Architecture: MVVM (Model-View-ViewModel) layered on top of a multi-module Clean Architecture.
Asynchronous Programming: Kotlin Coroutines & Flow for all background operations and reactive data streams.
Dependency Injection: Koin for managing dependencies across the app.
Networking: Ktor Client for all remote API communication.
Local Storage: Room for persistent, offline storage of favorite recipes.
Navigation: Jetpack Navigation Compose for navigating between screens.
Image Loading: Coil 3 for efficiently loading and caching images from the web.
Architecture
The application is built following Clean Architecture principles, enforcing a strict separation of concerns between different layers of the app. This is achieved through a multi-module Gradle setup.
UI Layer → Presentation Layer → Domain Layer → Data Layer
Domain Layer: The core of the application. It contains the business logic (use cases, though we used repository methods directly for simplicity) and business models (Recipe, Ingredient). It is a pure Kotlin module with no Android dependencies.
Data Layer: Responsible for providing data to the application. It contains the implementation of the repository, a remote data source (Ktor for TheMealDB API), and a local data source (Room database). It knows about the Domain layer but not the UI.
Presentation Layer (ViewModels): Part of the feature modules. It connects the UI to the domain layer, manages UI state, and handles user events.
UI Layer (Composables): The user-facing part of the app. It is a "dumb" layer that simply observes state from the ViewModels and sends user events to them.
Project Structure
The project is divided into several Gradle modules to enforce the architectural separation:
:app - The main application module that the user installs. It ties all the other modules together and handles top-level navigation.
:domain - Contains the core business models (Recipe) and the repository interface (RecipeRepository). Pure Kotlin.
:data - Implements the RecipeRepository. Contains all data-sourcing logic, including Ktor for networking and Room for the database, as well as DTOs and mappers.
:feature_search - A feature module containing all the UI and presentation logic for the online recipe search, home screen, and recipe detail view.
:feature_favorites - A feature module containing the UI and presentation logic for displaying the user's saved, offline recipes.
Setup & Build
To build and run this project, you will need:
Android Studio Iguana | 2023.2.1 or higher.
JDK 17 or higher.
Steps:
Clone this repository:
code
Bash
git clone https://github.com/your-username/magic-fridge.git
Open the project in Android Studio.
Let Gradle sync and download all the required dependencies.
Run the app on an emulator or a physical device.
The project uses TheMealDB API, which does not require an API key.

License
code
Code
MIT License

Copyright (c) 2025 ValenEuterpe

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
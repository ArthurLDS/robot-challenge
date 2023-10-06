# Robot Challenge

[![Kotlin](https://img.shields.io/static/v1?label=kotlin&message=powered&color=green)]()
[![Koin](https://img.shields.io/static/v1?label=koin&message=3.1.5&color=F68212)]()
[![Mockk](https://img.shields.io/static/v1?label=mockk&message=1.10.0&color=9F55FF)]()
[![Compose](https://img.shields.io/static/v1?label=compose&message=1.7.2&color=00AFF0)]()
[![Compose](https://img.shields.io/static/v1?label=compose&message=1.7.2&color=00AFF0)]()
[![Turbine](https://img.shields.io/static/v1?label=turbine&message=0.7.0&color=9F55FF)]()
[![Badge](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)]()

## App Features

The app contains all of the required features as well as some fun bonus features. 
- ✅ At the start of each round, randomly place a prize token somewhere on the game board and start each robot at opposite corners of the board.
- ✅ Robots should take turns making legal game moves at half-second intervals.
- ✅ As each move is made the robot will leave a trail of each space it has moved through during the round — these trail spaces may not be passed through by the other robot during the course of the round.
- ✅ Each robot may move either left, right, up, or down to an unoccupied and unvisited space. Diagonal moves are not allowed.5. If a robot cannot move from its current position, it should hold its position until the end of the round.
- ✅ The first robot to reach the prize wins one point, the board then resets, and the next round starts.This simulation should run continuously, keeping track of the total score for each robot during the game session.
- ✅ Use of coroutines or threads to run each robot concurrently while maintaining turn order.
- 🍒 **BONUS: Dynamic Board Size**: its possible to expand the board size beyond 7x7, just increasing the boardSize in GameRepository.

   - *Note:* Boards larger than 50x50 are pretty hard to see.
- 🍒 **BONUS: Up to 4 Players**: Its possible to increase the number of Players until 4. By default the quantity was set to 2, but you can change it updating the GameRepository.
   

## Technical Features
- UI interface developed using `Jetpack Compose`.
- Implementation of dependency injection using `Koin`.
- Handling of data made by Kotlin `State Flow`.
- Use of Android Architecture Components best practices in general.
- Code written seeking to maintain the best Clean Code practices.
- Unit test coverage in View Models and Repositories, using `Mockk` and `JUnit`.


## Architecture and Design Patterns
- The APP architecture was built based on some **Clean Architecture** concepts, but does not implement all of its details. The innovative architecture consists of 3 layers, data (infrastructure, communication with APIs, etc.), domain (negotiation rules and definition of contacts) and presentation (visualization part of the application).

  <image src="https://user-images.githubusercontent.com/18702590/155230117-69877898-f2e0-4216-a760-c0bafa1f0281.png" width="500"/>

- The application uses **MVVM** as a Pattern for the presentation layer, as recommended by Google itself.

## Technologies

As seguintes bibliotecas e frameworks foram usadas na construção do projeto:
- [Koin](https://github.com/InsertKoinIO/koin)
- [AndroidX](https://developer.android.com/jetpack/androidx?authuser=1)
- [Kotlin Coroutines](https://developer.android.com/courses/pathways/android-coroutines)
- [Mockk](https://github.com/mockk/mockk)
- [Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjw4P6oBhBsEiwAKYVkq2J_xTtgCYPrO6D86heXXvsjgu_6rJF-l0guhHn0cPxSQA22LVzDpBoC50wQAvD_BwE&gclsrc=aw.ds&hl=pt-br)
- [Turbine](https://github.com/cashapp/turbine)

## 📱 Preview

https://user-images.githubusercontent.com/18702590/155229538-ac192fcd-e751-49b3-aa2a-3fc5b10ee817.mp4

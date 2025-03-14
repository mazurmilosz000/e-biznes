# Discord Bot - Product Categories

This is a simple Discord bot built using Kotlin and the Diskord library. The bot provides functionality to list product categories and display products from specific categories.

## Features

- **Ping/Pong Command**: Responds with "pong" when "ping" is sent.
- **List Categories**: Responds with a list of available product categories.
- **List Products by Category**: Responds with a list of products in the specified category.

## Setup

### Prerequisites

- Kotlin 1.5+ (for running the project)
- A Discord bot token
- IntelliJ IDEA or any other IDE that supports Kotlin
- Gradle (for building and running the project)

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://your-repository-url

## How do I use this?

Before this project can be used, a Diskord API bot token will be required.  Check the Discord API
[documentation](https://discord.com/developers/docs/intro) for information on how to acquire this.

Once a bot token has been acquired, create a file in `src/main/resources` called `bot-token.txt` and paste in the bot
token.  This file is explicitly excluded from Git, so it does not accidentally get publicly exposed.

After creating the bot token file, simply run the main function through your IDE or run the Gradle `run` task
(`gradlew run` on Windows CLI, `./gradlew run` on Linux or MacOS).


## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |



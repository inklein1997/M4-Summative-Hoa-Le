# Game Store API

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-2.1-4baaaa.svg)](code_of_conduct.md)

## Table of Contents
- [Description](#Description)
- [Technologies](#Technologies)
- [Contributors](#contributors)
- [Links](#Links)
- [User Instructions](#User-Instructions)
- [License](#License)
- [Contribution](#Contribution)
- [Acknowledgements](#Acknowledgements)
- [Questions](#Questions)

## Description
**Game Store API** is a back-end application that was for store that sells Games, T-Shirts, and Consoles.

## Technologies
- Java 1.8
- Spring Boot

## Contributors
[<img src="https://avatars.githubusercontent.com/u/93157433?v=4" width="75" height="75">](https://github.com/inklein1997)
[<img src="https://avatars.githubusercontent.com/u/47269779?v=4" width="75" height="75">](https://github.com/hoale123)
[<img src="https://avatars.githubusercontent.com/u/87605893?v=4" width="75" height="75">](https://github.com/Honorinenn)

## Links
- ### [URL to Github Repository](https://github.com/inklein1997/M4-Summative-Hoa-Le)

## User Instructions
- Please visit [here](./api-documentation.yaml) for detailed API instructions

| Method | Routes | Description |
| ----------- | ----------- | ----------- |
| CONSOLE ROUTES |  |  |
| GET | https://gamestore-backend.herokuapp.com/consoles | Returns list of all Console |
| GET | https://gamestore-backend.herokuapp.com/consoles/:id | Returns Console information of item matching inputted id |
| GET | https://gamestore-backend.herokuapp.com/consoles?manufacturer={manufacturer_name} | Returns list of consoles that have the specified manufacturer
| POST | https://gamestore-backend.herokuapp.com/consoles | Creates a new Console entry |
| PUT | https://gamestore-backend.herokuapp.com/consoles/:id | Updates product entry of Console matching inputted id |
| DELETE | https://gamestore-backend.herokuapp.com/consoles/:id | Deletes product information of Console matching inputted id |
| GAME ROUTES |  |  |
| GET | https://gamestore-backend.herokuapp.com/games | Returns list of all Games |
| GET | https://gamestore-backend.herokuapp.com/games/:id | Returns Game information of item matching inputted id |
| GET | https://gamestore-backend.herokuapp.com/games/title/:title | Returns Game information of item matching inputted Title |
| GET | https://gamestore-backend.herokuapp.com/games?studio={studio_name}&esrbRating={esrb_rating} | Returns list of Games that have the specified studio name and/or ESRB rating
| POST | https://gamestore-backend.herokuapp.com/games | Creates a new Game entry |
| PUT | https://gamestore-backend.herokuapp.com/games/:id | Updates product entry of Game matching inputted id |
| DELETE | https://gamestore-backend.herokuapp.com/games/:id | Deletes product information of Game matching inputted id |
| TSHIRT ROUTES |  |  |
| GET | https://gamestore-backend.herokuapp.com/tshirts | Returns list of all Tshirts |
| GET | https://gamestore-backend.herokuapp.com/tshirts/:id | Returns Tshirt information of item matching inputted id |
| GET | https://gamestore-backend.herokuapp.com/tshirts?color={color}&size={size} | Returns list of Tshirts that have the specified color and/or size
| POST | https://gamestore-backend.herokuapp.com/tshirts | Creates a new Tshirt entry |
| PUT | https://gamestore-backend.herokuapp.com/tshirts/:id | Updates product entry of Tshirt matching inputted id |
| DELETE | https://gamestore-backend.herokuapp.com/tshirts/:id | Deletes product information of Tshirt matching inputted id |

## License
This project is licensed under the terms of [MIT](https://opensource.org/licenses/MIT).
  
## Contribution
Before contributing to **Game Store API**, please read this [code of conduct](code_of_conduct.md)[^1].<br>
Here's how you can contribute...
1. Add issue or recommendation for improvement to Issues tab on Github.
2. Submit pull request for review.

## Acknowledgements
File structure was modeled by 2U Java Bootcamp curriculum.
CustomErrorResponse and CorsConfig classes was also provided by the 2U Java Bootcamp curriculum

## Questions

If you have any questions, please feel free to reach out to any of us!
| Name | Github | Email |
| ----------- | ----------- | ----------- |
| Michael Klein | [@inklein1997](https://github.com/inklein1997) | michaelklein1997@gmail.com |
| Hoa | [@hoale123](https://github.com/hoale123) | hoa_le9485@yahoo.com |
| Honorine Ndom Ndzah | [@Honorinenn](honorinendzah@gmail.com) | arsementfarms@gmail.com |
[^1]: Code of Conduct provided by [Contributor Covenant](https://www.contributor-covenant.org/)
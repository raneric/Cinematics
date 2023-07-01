package com.example.cinematics.data

import com.example.cinematics.R
import java.time.LocalDate
import java.util.Date

val movieList = listOf(
    MovieModel(
        id = 0,
        title = "Star Wars Rise Of Skywalker",
        year = 2019,
        duration = "02h22",
        genres = listOf("Action", "Si-Fi"),
        ratingNote = 4.0,
        picture = R.drawable.star_wars_rise_of_skywalker,
        stars = 3,
        author = "Cathy Yan"),
    MovieModel(
        id = 1,
        title = "Bird Of Prey",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.birds_of_prey,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 2,
        title = "Avengers End Game",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Si-Fi"),
        ratingNote = 5.0,
        picture = R.drawable.endgame,
        stars = 5,
        author = "Cathy Yan"),
    MovieModel(
        id = 3,
        title = "Grey hound",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.greyhound,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 4,
        title = "Hamilton",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.hamilton,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 5,
        title = "Frozen 2",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.frozen_2,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 6,
        title = "Avengers Infinity War",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.infinity_war,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 7,
        title = "Interstellar",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.interstellar,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 8,
        title = "Onward",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.onward,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 9,
        title = "Spiderman far from home",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.spiderman_far_from_home,
        stars = 4,
        author = "Cathy Yan"),
    MovieModel(
        id = 10,
        title = "Star wars the last jedi",
        year = 2019,
        duration = "01h42",
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = R.drawable.star_wars_last_jedi,
        stars = 4,
        author = "Cathy Yan"))

val userList = listOf(
    User(
        id = 1,
        name = "Kate Lane",
        userName = "kat_m",
        picture = R.drawable.profil1
    ),
    User(
        id = 2,
        name = "Hong Lee",
        userName = "lee_h",
        picture = R.drawable.profil2
    ),
    User(
        id = 3,
        name = "Jack Rose",
        userName = "r_jack",
        picture = R.drawable.profil4
    ),
    User(
        id = 4,
        name = "Catherine Knowles",
        userName = "cath_kon",
        picture = R.drawable.profil3
    ),
    User(
        id = 5,
        name = "Ahmed Marks",
        userName = "ahmed",
        picture = R.drawable.profil5
    ),
    User(
        id = 6,
        name = "Lola Oneal",
        userName = "lola",
        picture = R.drawable.profil6
    ),
    User(
        id = 7,
        name = "Ollie Nelson",
        userName = "ollie",
        picture = R.drawable.profil7
    ),
    User(
        id = 8,
        name = "Bianca Brock",
        userName = "bianca",
        picture = R.drawable.profil8
    ),
    User(
        id = 9,
        name = "Lewis Lowe",
        userName = "lewis",
        picture = R.drawable.profil9
    ),
    User(
        id = 10,
        name = "Asia Bush",
        userName = "asia",
        picture = R.drawable.profil10
    )
)

val userRatingList = listOf(
    UserRating(
        id = 1,
        user = userList[0],
        rating = 3,
        date = LocalDate.of(2023, 6, 11)
    ),
    UserRating(
        id = 2,
        user = userList[2],
        rating = 5,
        date = LocalDate.of(2023, 6, 1)
    ),
    UserRating(
        id = 3,
        user = userList[3],
        rating = 3,
        date = LocalDate.of(2023, 6, 28)
    ),
    UserRating(
        id = 4,
        user = userList[4],
        rating = 4,
        date = LocalDate.of(2023, 7, 1)
    )
)
package com.sgg.cinematics.data

import com.sgg.cinematics.R
import com.sgg.cinematics.data.model.MovieModel
import com.sgg.cinematics.data.model.UserModel
import com.sgg.cinematics.data.model.UserRatingModel
import com.sgg.cinematics.utils.toMillis
import kotlinx.collections.immutable.persistentListOf
import java.time.LocalDate

val fakeMovieList = listOf(
    MovieModel(
        id = 0,
        title = "Star Wars Rise Of Skywalker",
        year = 2019,
        duration = 2.3,
        genres = listOf("Action", "Adventure", "Fantasy"),
        ratingNote = 3.0,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Fstar_wars_rise_of_skywalker.jpg?alt=media&token=1c6b9378-34f7-4162-92da-5abe97b8f895",
        stars = 3,
        author = "J.J. Abrams",
        overview = "The revival of Emperor Palpatine resurrects the battle between the Resistance and the First Order while the Jedi's legendary conflict with the Sith Lord comes to a head." +
                "The revival of Emperor Palpatine resurrects the battle between the"
    ),
    MovieModel(
        id = 1,
        title = "Birds Of Prey",
        year = 2020,
        duration = 1.4,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Fbirds_of_prey.jpg?alt=media&token=4025387f-aeb2-4fe2-bf91-26fcbcd9bf4f",
        stars = 4,
        author = "Christina Hodson",
        overview = "Birds of Prey (and the Fantabulous Emancipation of One Harley Quinn), also known as Harley Quinn: Birds of Prey[6][7] or simply Birds of Prey, is a 2020 American superhero film directed by Cathy Yan and written by Christina Hodson, based on the DC Comics team, the Birds of Prey. Produced by Warner Bros., DC Films, LuckyChap Entertainment, Clubhouse Pictures, and Kroll & Co. Entertainment, it is the eighth installment in the DC Extended Universe (DCEU), and serves as a spin-off and sequel to Suicide Squad (2016). "
    ),
    MovieModel(
        id = 2,
        title = "Avengers End Game",
        year = 2019,
        duration = 3.01,
        genres = listOf("Action", "Si-Fi", "Adventure"),
        ratingNote = 5.0,
        stars = 5,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Favengers_end_game.jpg?alt=media&token=124db381-b61a-422d-806c-4b89a5871772",
        author = " Joe Russo",
        overview = "After Thanos, an intergalactic warlord, disintegrates half of the universe, the Avengers must reunite and assemble again to reinvigorate their trounced allies and restore balance.\n"
    ),
    MovieModel(
        id = 3,
        title = "Grey hound",
        year = 2019,
        duration = 1.6,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Fgrey_hound.jpg?alt=media&token=382406f8-2a52-40f4-88fb-7340817e8f44",
        stars = 4,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    ),
    MovieModel(
        id = 4,
        title = "Hamilton",
        year = 2019,
        duration = 2.0,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 2.0,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Fhamilton.jpg?alt=media&token=b5714807-de10-44f1-8aa3-a444375d4e97",
        stars = 2,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    ),
    MovieModel(
        id = 5,
        title = "Frozen 2",
        year = 2019,
        duration = 1.8,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 1.0,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Ffrozen_2.jpg?alt=media&token=9acfac76-d2f9-4403-a346-41dc6bcabccb",
        stars = 1,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    ),
    MovieModel(
        id = 6,
        title = "Avengers Infinity War",
        year = 2019,
        duration = 2.2,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Favengers_infinity_war.jpg?alt=media&token=5a5cbd5b-fb09-4843-932a-82fff7172399",
        stars = 4,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    ),
    MovieModel(
        id = 7,
        title = "Interstellar",
        year = 2019,
        duration = 2.8,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Finterstellar.jpg?alt=media&token=1a0e4b05-d641-4407-9fec-7bd0004cd73e",
        stars = 4,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    ),
    MovieModel(
        id = 8,
        title = "Onward",
        year = 2019,
        duration = 1.9,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 2.0,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Fonward.jpg?alt=media&token=23e3c6bb-e063-49e4-8c48-8bdca8cb9064",
        stars = 2,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    ),
    MovieModel(
        id = 9,
        title = "Spiderman far from home",
        year = 2019,
        duration = 2.6,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 5.0,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Fspiderman_far_from_home.jpg?alt=media&token=979e97b3-3621-49ad-801c-b3401df0ef5b",
        stars = 5,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    ),
    MovieModel(
        id = 10,
        title = "Star wars the last jedi",
        year = 2019,
        duration = 2.4,
        genres = listOf("Action", "Drama", "Horor"),
        ratingNote = 4.5,
        picture = "https://firebasestorage.googleapis.com/v0/b/travelmantics-5312e.appspot.com/o/movies%2Fstar_wars_the_last_jedi.jpg?alt=media&token=b35faf1b-025d-49ac-8b2a-6511196f26cb",
        stars = 4,
        author = "Cathy Yan",
        overview = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu ipsum nulla. Maecenas aliquam consequat dui non ullamcorper. Curabitur laoreet finibus facilisis. Morbi sit amet sollicitudin odio, at lobortis erat. Nunc eget arcu nunc. Quisque placerat metus tempus, varius lacus at, varius est. Maecenas auctor dui a eros malesuada congue. Donec nec venenatis libero."
    )
)

val userModelLists = persistentListOf(
    UserModel(
        id = "1",
        firstName = "Kate",
        lastName = "Lane",
        email = "kate@gmail.com",
        location = "Antsirabe - Madagascar",
        bio = "Hacktoberfest is a month-long celebration of open source projects, their maintainers, and the entire community of contributors. Each October, open source maintainers give new contributors extra attention as they guide developers through their first pull requests on GitHub.",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "2",
        firstName = "Hong",
        lastName = "Lee",
        email = "hong@gmail.com",
        location = "Majunga - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "3",
        firstName = "Jack",
        lastName = "Rose",
        email = "jack@gmail.com",
        location = "Antananarivo - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "4",
        firstName = "Catherine",
        lastName = "Knowles",
        email = "cath@gmail.com",
        location = "Toliara - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "5",
        firstName = "Ahmed",
        lastName = "Marks",
        email = "marks@gmail.com",
        location = "Fianarantsoa - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "6",
        firstName = "Lola",
        lastName = "Oneal",
        email = "lola@gmail.com",
        location = "Antananarivo - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "7",
        firstName = "Ollie",
        lastName = "Nelson",
        email = "ollie@gmail.com",
        location = "Antananarivo - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "8",
        firstName = "Bianca",
        lastName = "Brock",
        email = "bianca@gmail.com",
        location = "Antananarivo - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "9",
        firstName = "Lewis",
        lastName = "Lowe",
        email = "lew@gmail.com",
        location = "Antananrivo - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    ),
    UserModel(
        id = "10",
        firstName = "Asia",
        lastName = "Bush",
        email = "asia@gmail.com",
        location = "Antananrivo - Madagascar",
        birthDate = LocalDate.of(1989, 8, 23)
            .toMillis()
    )
)

val userRatingModelLists = persistentListOf(
    UserRatingModel(
        id = 1,
        name = "Kitty L.",
        userPicture = R.drawable.profil1,
        rating = 3,
        date = LocalDate.of(2023, 6, 11)
    ),
    UserRatingModel(
        id = 2,
        name = "Donna Brew",
        userPicture = R.drawable.profil2,
        rating = 5,
        date = LocalDate.of(2023, 6, 1)
    ),
    UserRatingModel(
        id = 3,
        name = "Lina Nice",
        userPicture = R.drawable.profil3,
        rating = 3,
        date = LocalDate.of(2023, 6, 28)
    ),
    UserRatingModel(
        id = 4,
        name = "Rayan Bell",
        userPicture = R.drawable.profil4,
        rating = 4,
        date = LocalDate.of(2023, 7, 1)
    )
)
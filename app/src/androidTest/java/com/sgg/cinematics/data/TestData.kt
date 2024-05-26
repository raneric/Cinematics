package com.sgg.cinematics.data

import com.sgg.cinematics.data.model.MovieModel

val testMovieList = listOf(
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
        ))

var testWatchList = mutableListOf<MovieModel>()

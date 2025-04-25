package app.myquizapp.co.uk.presentation

sealed interface LoadEvent {
    data object LoadError: LoadEvent
}
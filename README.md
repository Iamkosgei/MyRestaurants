# My Restaurants

An Android app that uses the Yelp Fusion API to view restaurants in a given zipcode.

## Setup Requirements

This app uses the [Yelp Fusion](https://www.yelp.com/fusion) API so you will need to sign up for a developer's account and generate an access token.
Once you've done that, you'll need to assign the token to a variable called `YelpAccessToken` in the `gradle.properties` file. 

## TODO
- [ ] Refactor Java code to Kotlin
- [ ] Implement AAC
    - [ ] Room
    - [ ] Lifecycle-aware components
    - [ ] ViewModels
    - [ ] Paging
    - [ ] Navigation
- [ ] Replace OKHttp with Retrofit
- [ ] Add Dagger 2 for Dependency injection

## License
[MIT](https://choosealicense.com/licenses/mit/)

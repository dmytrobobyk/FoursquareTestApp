# FoursquareTestApp
Test app with using foursquare api

Haven't done saving to favorites on details page. Architecture doesn't suit good for this task. 
Would be better to use some shared view model here and passing items through it instead of doing it through Parcelable.
Also, it should easily resolve problem with saving favorites from any screen because view model is going to be shared. 
Or even using this architecture it would be much easier to complete the task using some Database and store favorites and items there.

Took a long time to test Fousquare API. Was confused with it's versions in the task description on github and official documentation at foursquare. 

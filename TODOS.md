#Improved Architecture

## Todo's

[] Implement unit tests
[] Implement real database (not in memory)
    [] check for best option (Relational/NonSQL)
    [] run it in Docker
    [] create `docker composer`
[] Improve Error handling
    [] check if throwing error in entity is good
[] Include task property `completed`
    [] create endpoint to set as `completed`
    [] create query param to get list of `completed` or `not completed` tasks
    [] check for best option (completed or status + ENUM)
[] Change task property `priority` for ENUM
[] Implement Auth
    [] Pass Auth as middleware
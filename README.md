# rundeck-webexteams

A notification plugin for Rundeck to send notifications to WebEx Teams.

## Installation instructions

1. Build a snapshot from source using `gradle build`
2. Copy the `build/libs/rundeck-webexteams-1.0.jar` file to your `$RDECK_BASE/libext` folder
3. ???
4. Profit

## Configuration

You'll need a WebEx Teams Auth Token.  Easiest way is to register a bot and use it's token.  Make sure to add the bot to the room you want notifications in.

You'll also need the room UUID that you want to post notifications to.  You can [list rooms](https://developer.webex.com/docs/api/v1/rooms/list-rooms) from the interactive API using the bot Auth Token once you've added the bot to the appropriate room.

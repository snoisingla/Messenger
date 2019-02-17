## Messenger

This is a demo application with whatsapp messenger like functionality. There are two parts for this application.

1. RESTful web service: Built using spring boot, this service offers REST APIs. Data exchange happens in JSON format.

2. Front-end: Build using React, this application uses the REST APIs offered by the backend.

### This application provides following features:
* User can Register and Login using mobile number
* User can upload a profile picture
* User can see list of conversations with most recently active at the top. User can see list of messages in each conversation
* User can send messages to any other other and chat/message screen will update accordingly
* User can see 'last seen at' for other users
* User can see other user's profile


## Backend - Spring Boot Messaging Service.

This is the backend application for the Messenger project. This application is built using Spring boot.

React based [MessengerUI](https://github.com/snoisingla/MessengerUI) is the front-end for this Messenger project.

### Security
* Auth Token implementation for securing each API call
* One Time Password (OTP) implementation

### REST APIs
User:
* POST users
* POST users/verify
* GET users/:contactNumber
* GET users

Messages:
* POST messages
* GET messages
* GET allmessages
* GET messages/:id
* DELETE message/:id
* EDIT message/:id

OTP:
* POST otps/request
* GET otps/:contactnumber
* POST otps/verify
* GET otps

### Database
PostgreSQL is being used for persistent storage.

### How to run backend service

#### Using Eclipse

1. Download and install `Eclipse Java EE IDE for Web Developers`.  
2. Clone this project
3. Run Eclipse. Go to File -> Import -> Maven -> Existing Maven Projects and select the cloned folder
4. Use Eclipse -> Run to start the server


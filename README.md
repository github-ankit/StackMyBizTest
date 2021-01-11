# StackMyBizTest

This application provides User login/register via google's account. Here we have two screens : 
1. Login Screen -  It has two fields UserName and Password with a login button and a Forgot Password link.
2. User's Profile screen - It has a ImaveView that displays user's image and couple of TextBox to display user's name, email etc.




Login Screen
<img src="https://github.com/github-ankit/StackMyBizTest/blob/main/app/src/main/res/drawable/screenshot_aa.jpg" width="200" height="400">

Profile Screen
<img src="https://github.com/github-ankit/StackMyBizTest/blob/main/app/src/main/res/drawable/screenshot_bb.jpg" width="200" height="400">

This application also stores users's login and logout events into Firebase database.

Here, Google Sigin and adding the login event to firebase database also have been added.

After succesfull login, login event is saved in firebase database, Here we are storing users location when user logs in and updating the location every 5 sec till user is logs out :

```json
{
  "LocationUpdates" : {
    "1610354492" : {
      "email" : "ankit.4uv@gmail.com",
      "name" : "Ankit Singh",
      "userLat" : "23.6894532",
      "userLong" : "85.3137833"
    },
    "1610354494" : {
      "email" : "ankit.4uv@gmail.com",
      "name" : "Ankit Singh",
      "userLat" : "23.686355",
      "userLong" : "85.3181917"
    },
    "1610354500" : {
      "email" : "ankit.4uv@gmail.com",
      "name" : "Ankit Singh",
      "userLat" : "23.6861747",
      "userLong" : "85.3180557"
    },
    "1610354523" : {
      "email" : "mailmeankit26@gmail.com",
      "name" : "ankit kumar",
      "userLat" : "23.6861333",
      "userLong" : "85.31802"
    },
    "1610354528" : {
      "email" : "mailmeankit26@gmail.com",
      "name" : "ankit kumar",
      "userLat" : "23.686115",
      "userLong" : "85.3179918"
    },
    "1610354533" : {
      "email" : "mailmeankit26@gmail.com",
      "name" : "ankit kumar",
      "userLat" : "23.6861108",
      "userLong" : "85.3179877"
    }
  },
  "LoginEvents" : {
    "1610354488" : {
      "date" : "Mon,11 Jan, 2021 @ 14:11 PM",
      "email" : "Ankit Singh",
      "name" : "ankit.4uv@gmail.com",
      "status" : "Online"
    },
    "1610354504" : {
      "date" : "Mon,11 Jan, 2021 @ 14:11 PM",
      "email" : "Ankit Singh",
      "name" : "ankit.4uv@gmail.com",
      "status" : "Offline"
    },
    "1610354522" : {
      "date" : "Mon,11 Jan, 2021 @ 14:12 PM",
      "email" : "ankit kumar",
      "name" : "mailmeankit26@gmail.com",
      "status" : "Online"
    },
    "1610354535" : {
      "date" : "Mon,11 Jan, 2021 @ 14:12 PM",
      "email" : "ankit kumar",
      "name" : "mailmeankit26@gmail.com",
      "status" : "Offline"
    }
  }
}
```

We can also fetch the stored data when required.


## Requiremenets 

1. We need Android studio to run this project.
2. A Physical Device to run the application
3. For google login we an email id

## Features

1. It provides user login using google account
2. Data of the user saved in database 

## Flow

This app uses a google account to login user, 

1. Click on SignIn with Google Button and you will be able to signin by choosing your account.
2. There are two feilds username(emailid) and password , both the feilds are validated on clicking login button.
3. Then we a Profile section where name, email ID and picture of the user is displayed.
4. On Profile screen we have a logout buttons and clicking on it will be loggedOut and returned to the login screen.


###

Answer to the question : If you were given more time to improve your solution what would you improve and why?

- Since app UI is the first thing everyone notices, I would have used motion Layout for animating the textfields of the login page so that app doesn't look simple.

- I would also love to write the code in Kotlin, since it new to me and others are adopting it i also like to code in kotlin to get more command over that language.
